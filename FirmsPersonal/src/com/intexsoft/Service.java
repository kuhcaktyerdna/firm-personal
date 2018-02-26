package com.intexsoft;

import com.intexsoft.workers.*;
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

public class Service {

	private WorkersRepository workersRepository = WorkersRepository.getInstance();
    public static final Service INSTANCE = new Service();

	private Service(){}

    public static Service getInstance(){
	    return INSTANCE;
    }

	public  ArrayList<Worker> getRepository(){
    	return workersRepository.getRepository();
	}

	public void addWorker(Worker newWorker){
		workersRepository.addWorker(newWorker);
	}

	public void addWorkers(Worker[] newWorkers){
		workersRepository.addWorkers(newWorkers);
	}

	public void addWorkers(ArrayList<Worker> newWorkers){
		workersRepository.addWorkers(newWorkers);
	}

	public void removeWorker(int id){
		workersRepository.removeWorker(id);
	}

	public Worker getWorkerById(int id){
		return workersRepository.getWorkerById(id);
	}

	public void removeWorkers(int[] ids){
		workersRepository.removeWorkers(ids);
	}

	public void sortByName(){
		workersRepository.sortByName();
	}

	public void sortByBirthDate(){
		workersRepository.sortByBirthDate();
	}

	public void sortByEnteringDate(){
		workersRepository.sortByEnteringDate();
	}

    public Worker switchPosition(Worker worker, String position){
	    switch (position){
	        case "com.intexsoft.workers.Manager":
				worker = new Manager(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
                break;

            case "com.intexsoft.workers.Employee":
				worker = new Employee(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
                break;

            case "com.intexsoft.workers.Head":
				worker = new Head(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
				break;

            case "com.intexsoft.workers.Secretary":
				worker = new Secretary(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
                break;
			default: worker = new Worker(worker.getFullName(),
					worker.getBirthDate(), worker.getEnteringDate());
			break;
        }
        return worker;
    }

	public void writeList(){
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT,
				new Locale("BY"));
    	try {
			Document document = DocumentBuilderFactory.newInstance().
					newDocumentBuilder().newDocument();
			Element root = document.createElement("root");
			document.appendChild(root);
			int i = 1;
			for(Worker w : WorkersRepository.INSTANCE.getRepository()) {
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
		if(!file.exists()){
			System.out.println("File not found.");
			System.exit(0);
		}
		if(file != null){
			ArrayList<Worker> readedWorkers = new ArrayList<>();
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy",
						new Locale("BY"));
				List<String> lines = Files.readAllLines(Paths.get(file.getName()),
						StandardCharsets.UTF_8);
				System.out.println(lines.size());
				for (int i = 0; i < lines.size(); i++){
					Worker worker = new Worker();
					worker.setFullName(lines.get(i));
					worker.setBirthDate(
							sdf.parse(lines.get(i+1)));
					worker.setEnteringDate(
							sdf.parse(lines.get(i+2)));
					i += 2;
					System.out.println(worker);
					readedWorkers.add(worker);
				}
			} catch (ParseException pe){
				System.err.println(pe);
			} catch (FileNotFoundException fe) {
				System.err.println(fe);
			} catch (IOException ioe) {
				System.err.println(ioe);
			}
			WorkersRepository.INSTANCE.addWorkers(readedWorkers);
		}
		else {
			System.out.println("File is empty.");
			System.exit(0);
		}

	}
}