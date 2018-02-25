import java.io.Serializable;
import java.util.Date;

public class Employee extends Worker implements Serializable {

    public Employee(String fullName, Date birthDate, Date enteringDate){
        super(fullName, birthDate, enteringDate);
    }

}