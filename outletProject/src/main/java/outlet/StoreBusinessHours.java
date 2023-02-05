package outlet;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;

public class StoreBusinessHours {

    private int id;
    private Time openingTime;
    private Time closingTime;
    private String isRoundTheClockWork;
    private Calendar open;

    public StoreBusinessHours(int id, Time openingTime, Time closingTime, String isRoundTheClockWork) {
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.isRoundTheClockWork = isRoundTheClockWork;
        this.open = Calendar.getInstance();
        this.open.setTime(openingTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public String getIsRoundTheClockWork() {
        return isRoundTheClockWork;
    }

    public void setIsRoundTheClockWork(String isRoundTheClockWork) {
        this.isRoundTheClockWork = isRoundTheClockWork;
    }

    public boolean isRoundTheClock() {
        return "yes".equalsIgnoreCase(isRoundTheClockWork);
    }

    public long getWorkingTime(Timestamp start, Timestamp end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.set(Calendar.YEAR, open.get(Calendar.YEAR));
        startCalendar.set(Calendar.MONTH, open.get(Calendar.MONTH));
        startCalendar.set(Calendar.DATE, open.get(Calendar.DATE));
        if(startCalendar.before(open)) {
            startCalendar.setTime(start);
            startCalendar.set(Calendar.HOUR, open.get(Calendar.HOUR));
            startCalendar.set(Calendar.MINUTE, open.get(Calendar.MINUTE));
            startCalendar.set(Calendar.SECOND, open.get(Calendar.SECOND));
        } else startCalendar.setTime(start);
        //System.out.printf("start:%s end:%s%n", startCalendar.getTime(), end);

        return (end.getTime() - startCalendar.getTimeInMillis())/60_000;
    }
}

//Код предназначен для класса StoreBusinessHours в пакете Outlet.
// Этот класс используется для хранения информации о часах работы магазина, включая время открытия магазина,
//время закрытия и то, работает ли магазин круглосуточно и без выходных.
// Класс включает геттеры и сеттеры для каждого из этих полей, а также два дополнительных метода:

//isDateInRange(): этот метод принимает дату в качестве входных данных и возвращает true,
//если дата приходится на рабочий день, когда магазин открыт, и false в противном случае.
//В настоящее время реализация всегда возвращает true,
//поскольку оператор switch охватывает только 7 дней недели.