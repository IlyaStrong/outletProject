package outlet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class DataDisplay {
    private Connection conn = null;

    public DataDisplay(Connection conn) {
        this.conn = conn;
    }

    public void displayData() {
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM calculated_showcase");

            System.out.println("Data in the calculated showcase table:");
            System.out.println("ID\tWorking Time\tDate");
            while (rs.next()) {
                int id = rs.getInt("id");
                Time workingTime = rs.getTime("working_time");
                Date date = rs.getDate("date");

                System.out.println(id + "\t" + workingTime + "\t" + date);
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while displaying data from the calculated showcase table: " + ex.getMessage());
        }
    }
}

 