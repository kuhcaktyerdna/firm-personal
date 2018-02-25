import java.util.ArrayList;
import java.util.Date;

public class Manager extends Worker {

    private final ArrayList<Worker> subordinates = new ArrayList<>();
    private int subId = 0;

    public void addSubordinate(Worker subordinate){
        subordinate.setSubId(subId);
        subId++;
        subordinates.add(subordinate);
    }

    public Manager(String fullName, Date birthDate, Date enteringDate){
        super(fullName, birthDate, enteringDate);
    }
}