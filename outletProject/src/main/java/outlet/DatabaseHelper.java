package outlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private final String DB_URL;
    private final String USER;
    private final String PASS;

    public DatabaseHelper(String DB_URL, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    public void initDatabase() {
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            String storeBusinessHoursTable = "CREATE TABLE IF NOT EXISTS store_business_hours (\n"
                    + "id INT AUTO_INCREMENT PRIMARY KEY,\n"
                    + "outlet_id INT NOT NULL,\n"
                    + "opening_time TIME NOT NULL,\n"
                    + "closing_time TIME NOT NULL,\n"
                    + "is_round_the_clock_work ENUM('yes', 'no') NOT NULL\n"
                    + ");";

            String calculatedShowcaseTable = "CREATE TABLE IF NOT EXISTS calculated_showcase (\n"
                    + "interval_id INT AUTO_INCREMENT PRIMARY KEY,\n"
                    + "outlet_id INT NOT NULL,\n"
                    + "employee_id INT NOT NULL,\n"
                    + "date DATE NOT NULL,\n"
                    + "number_of_working_minutes INT NOT NULL\n"
                    + ");";

            String biometryTable = "CREATE TABLE IF NOT EXISTS biometry (\n"
                    + "ID int NOT NULL,\n"
                    + "EmployeeID int NOT NULL,\n"
                    + "Date datetime NOT NULL,\n"
                    + "PresenceType varchar(3) NOT NULL,\n"
                    + "PRIMARY KEY (ID, EmployeeID, Date)\n"
                    + ");";

            stmt.executeUpdate(storeBusinessHoursTable);
            stmt.executeUpdate(calculatedShowcaseTable);
            stmt.executeUpdate(biometryTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void populateDatabase() {
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate("DELETE FROM store_business_hours");
            stmt.executeUpdate("DELETE FROM biometry");
            stmt.executeUpdate("DELETE FROM calculated_showcase");
            stmt.executeUpdate("INSERT INTO store_business_hours (outlet_id, opening_time, closing_time, is_round_the_clock_work) VALUES (1, '09:00:00', '21:00:00', 'no');");
            stmt.executeUpdate("INSERT INTO store_business_hours (outlet_id, opening_time, closing_time, is_round_the_clock_work) VALUES (2, '10:00:00', '9:59:00', 'yes');");
            stmt.executeUpdate("INSERT INTO biometry (ID, EmployeeID, Date, PresenceType) VALUES (1, 123, '2019-06-19 10:00:00', 'ON');");
            stmt.executeUpdate("INSERT INTO biometry (ID, EmployeeID, Date, PresenceType) VALUES (1, 123, '2019-06-19 12:00:00', 'ON');");
            stmt.executeUpdate("INSERT INTO biometry (ID, EmployeeID, Date, PresenceType) VALUES (1, 123, '2019-06-19 14:00:00', 'OFF');");
            stmt.executeUpdate("INSERT INTO biometry (ID, EmployeeID, Date, PresenceType) VALUES (1, 123, '2019-06-19 15:00:00', 'ON');");
            stmt.executeUpdate("INSERT INTO biometry (ID, EmployeeID, Date, PresenceType) VALUES (1, 123, '2019-06-19 17:55:00', 'OFF');");
            stmt.executeUpdate("INSERT INTO biometry (ID, EmployeeID, Date, PresenceType) VALUES (1, 123, '2019-06-19 19:00:00', 'ON');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    