package outlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculateWorkingTime {
    private final List<Biometry> biometryList;
    private final Map<Integer, StoreBusinessHours> storeBusinessHoursMap;

    public CalculateWorkingTime(List<Biometry> biometryList, Map<Integer, StoreBusinessHours> storeBusinessHoursMap) {
        this.biometryList = biometryList;
        this.storeBusinessHoursMap = storeBusinessHoursMap;
    }

    public List<CalculatedShowcase> calculate() {
        List<CalculatedShowcase> list = new ArrayList<>(biometryList.size());
        Biometry f = null;
        for (Biometry b : biometryList) {
            f = check(f, b, list);
        }
        return list;
    }

    private Biometry check(Biometry f, Biometry b, List<CalculatedShowcase> list) {
        if(
                f == null
                || b.getId() != f.getId()
                || b.getEmployeeId() != f.getEmployeeId()
                || f.getPresenceType().equalsIgnoreCase("off")
        ) return b;
        StoreBusinessHours store = storeBusinessHoursMap.get(b.getId());
        if(store == null) return b;
        if(b.getPresenceType().equalsIgnoreCase("on")) {
            System.out.printf("ТТ %s Сотрудник %s на рабочем месте%n", store.getId(), b.getEmployeeId());
            return f;
        }
        long workingTime = store.getWorkingTime(f.getDate(), b.getDate());
        System.out.printf("ТТ %s Сотрудник %s не на рабочем месте, рабочее время: %s мин.%n", store.getId(), b.getEmployeeId(), workingTime);
        CalculatedShowcase c = new CalculatedShowcase(
                store.getId(),
                b.getEmployeeId(),
                b.getDate(),
                workingTime
        );
        list.add(c);
        System.out.println("RESULT: " + c);
        return b;
    }
}
