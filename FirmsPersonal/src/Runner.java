import java.io.*;
import java.util.Date;

public class Runner {

    public static void main(String args[]){
        WorkersRepository rep = WorkersRepository.getInstance();
        Worker worker = new Employee("Petrov Ivan", new Date(),
                new Date());
        File file = new File("file.txt");
        Controller controller = Controller.getInstance();
        rep.addWorker(worker);
        controller.readList(file);
        controller.writeList();
        System.out.println(rep.getRepository().size());
        worker = controller.switchPosition(worker, "Manager");

    }

}
