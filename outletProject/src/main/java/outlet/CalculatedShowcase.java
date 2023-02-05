package outlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CalculatedShowcase {
    private int employerId;
    private int outletId;
    private Timestamp date;
    private long workingTime;

    public CalculatedShowcase(int outletId, int employerId, Timestamp date, long workingTime) {
        this.outletId = outletId;
        this.date = date;
        this.workingTime = workingTime;
        this.employerId = employerId;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outlet_id) {
        this.outletId = outlet_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public long getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(long workingTime) {
        this.workingTime = workingTime;
    }

    public void insertIntoDatabase(Connection connection) throws SQLException {
        String sql = "INSERT INTO calculated_showcase (outlet_id, employee_id, date, number_of_working_minutes) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, outletId);
            preparedStatement.setInt(2, employerId);
            preparedStatement.setTimestamp(3, date);
            preparedStatement.setLong(4, workingTime);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public String toString() {
        return "CalculatedShowcase{" +
                "employerId=" + employerId +
                ", outletId=" + outletId +
                ", date=" + date +
                ", workingTime=" + workingTime +
                '}';
    }
}
