import java.util.Comparator;

public class NameComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1 != null && w2 != null) {
            return w1.getFullName().compareTo(w2.getFullName());
        } else if (w1 != null && w2 == null) {
            return 1;
        } else {
            return -1;
        }

    }
}