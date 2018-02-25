import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Controller {

	private WorkersRepository workersRepository = WorkersRepository.getInstance();
    public static final Controller instance = new Controller();

	public static Controller getInstance(){
	    return instance;
    }


    public Worker switchPosition(Worker worker, String position){
	    switch (position){
	        case "Manager":
				worker = new Manager(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
                break;

            case "Employee":
				worker = new Employee(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
                break;

            case "Head":
				worker = new Head(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
				break;

            case "Secretary":
				worker = new Secretary(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
                break;
			default: worker = new Worker(worker.getFullName(),
					worker.getBirthDate(), worker.getEnteringDate());
			break;
        }
        return worker;
    }

    //writing to *.xml
	public void writeList(){
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT,
				new Locale("BY"));
    	try {
			Document document = DocumentBuilderFactory.newInstance().
					newDocumentBuilder().newDocument();
			Element root = document.createElement("root");
			document.appendChild(root);
			int i = 1;
			for(Worker w : WorkersRepository.instance.getRepository()) {
				Element worker = document.createElement("worker"+i);
				i++;
				root.appendChild(worker);
				worker.setAttribute("FullName", w.getFullName());
				worker.setAttribute("BirthDate",
						dateFormat.format(w.getBirthDate()).toString());
				worker.setAttribute("EnteringDate",
						dateFormat.format(w.getEnteringDate()).toString());
				Transformer transformer =
						TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.transform(new DOMSource(document),
						new StreamResult(new FileOutputStream("workers.xml")));
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

    public void readList(File file) {
		ArrayList<Worker> readedWorkers = new ArrayList<>();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy",
					new Locale("BY"));
			List<String> lines = Files.readAllLines(Paths.get(file.getName()),
					StandardCharsets.UTF_8);
			for (int i = 0; i < lines.size(); i=+3){
				Worker worker = new Worker();
				worker.setFullName(lines.get(i));
				worker.setBirthDate(
						sdf.parse(lines.get(i+1)));
				worker.setEnteringDate(
						sdf.parse(lines.get(i+2)));
				readedWorkers.add(worker);
			}
		} catch (ParseException pe){
			System.err.println(pe);
		} catch (FileNotFoundException fe) {
			System.err.println(fe);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		WorkersRepository.instance.addWorkers(readedWorkers);
	}

}