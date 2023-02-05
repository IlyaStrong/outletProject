package outlet;

import java.sql.Timestamp;
import java.util.Objects;

public class Biometry {

    private int id;
    private int employeeId;
    private Timestamp date;
    private String presenceType;

    public Biometry(int id, int employeeId, Timestamp date, String presenceType) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.presenceType = presenceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getPresenceType() {
        return presenceType;
    }

    public void setPresenceType(String presenceType) {
        this.presenceType = presenceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometry biometry = (Biometry) o;
        return id == biometry.id &&
                employeeId == biometry.employeeId &&
                date.equals(biometry.date) &&
                presenceType.equals(biometry.presenceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, date, presenceType);
    }
}
