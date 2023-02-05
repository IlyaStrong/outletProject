package outlet;

import java.sql.*;
import java.util.*;

public class RetrieveData {
    public static void main(String[] args) {
        List<Biometry> biometryList = new ArrayList<>();
        Map<Integer, StoreBusinessHours> storeBusinessHoursMap = new HashMap<>();
        DatabaseHelper db = new DatabaseHelper("jdbc:mysql://localhost:3306/outlet", "root", "55895589Bb&");
        db.initDatabase();
       // if(Boolean.parseBoolean(args[3])) db.populateDatabase();
        try (
                Connection connection = db.getConnection();
                ResultSet biometryResultSet = connection.createStatement().executeQuery("SELECT * FROM outlet.biometry order by ID, EmployeeID, Date;");
                ResultSet storeBusinessHoursResultSet = connection.createStatement().executeQuery("SELECT * FROM store_business_hours")
        ) {
            while (biometryResultSet.next()) {
                int id = biometryResultSet.getInt("ID");
                int employeeId = biometryResultSet.getInt("EmployeeID");
                Timestamp date = biometryResultSet.getTimestamp("Date");
                String presenceType = biometryResultSet.getString("PresenceType");
                biometryList.add(new Biometry(id, employeeId, date, presenceType));
            }

            while (storeBusinessHoursResultSet.next()) {
                int id = storeBusinessHoursResultSet.getInt("outlet_id");
                Time openingTime = storeBusinessHoursResultSet.getTime("opening_time");
                Time closingTime = storeBusinessHoursResultSet.getTime("closing_time");
                String isRoundTheClockWork = storeBusinessHoursResultSet.getString("is_round_the_clock_work");
                storeBusinessHoursMap.put(id, new StoreBusinessHours(id, openingTime, closingTime, isRoundTheClockWork));
            }

            CalculateWorkingTime calculateWorkingTime = new CalculateWorkingTime(biometryList, storeBusinessHoursMap);
            List<CalculatedShowcase> calculatedShowcases = calculateWorkingTime.calculate();
            for (CalculatedShowcase calculatedShowcase : calculatedShowcases) {
                calculatedShowcase.insertIntoDatabase(connection);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data from the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
