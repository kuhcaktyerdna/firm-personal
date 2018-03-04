package com.intexsoft.personal.services;

import com.intexsoft.personal.repositories.WorkersRepository;
import com.intexsoft.personal.workers.*;
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

public class Service implements IService {

	private WorkersRepository workersRepository = WorkersRepository.getInstance();
    public static final Service INSTANCE = new Service();

	private Service(){}

    public static Service getInstance(){
	    return INSTANCE;
    }

	public  ArrayList<Worker> getRepository(){
    	return workersRepository.getRepository();
	}

	/**
	 * Editing methods for the personal list.
	 */

	@Override
	public void addWorker(Worker newWorker){
		workersRepository.addWorker(newWorker);
		System.out.println("Worker was added to repository successfully.");
	}

	@Override
	public void addWorkers(Worker[] newWorkers){
		workersRepository.addWorkers(newWorkers);
		System.out.println("Workers were added to repository successfully.");
	}

	@Override
	public void addWorkers(ArrayList<Worker> newWorkers){
		workersRepository.addWorkers(newWorkers);
		System.out.println("Workers were added to repository successfully.");
	}

	@Override
	public void removeWorker(int id){
		workersRepository.removeWorker(id);
		System.out.println("Worker was removed from repository successfully.");
	}

	@Override
	public Worker getWorkerById(int id){
		return workersRepository.getWorkerById(id);
	}

	@Override
	public void removeWorkers(int[] ids){
		workersRepository.removeWorkers(ids);
		System.out.println("Workers were removed from repository successfully.");
	}

	public Worker switchPosition(Worker worker, String position){
		switch (position){
			case "Manager":
				worker = new Manager(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
				System.out.println("Worker's position was switched to manager successfully.");
				break;

			case "Employee":
				worker = new Employee(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
				System.out.println("Worker's position was switched to employee successfully.");
				break;

			case "Head":
				worker = new Head(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
				System.out.println("Worker's position was switched to head successfully.");
				break;

			case "Secretary":
				worker = new Secretary(worker.getFullName(),
						worker.getBirthDate(), worker.getEnteringDate());
				System.out.println("Worker's position was switched to secretary successfully.");
				break;
			default:
				System.out.println("Error finding position.");
				break;
		}
		return worker;
	}


	/**
	 * Sorting main list with all personal.
	 */

	public void sortByName(){
		workersRepository.sortByName();
		System.out.println("Repository was sorted by workers's names successfully.");
	}

	public void sortByBirthDate(){
		workersRepository.sortByBirthDate();
		System.out.println("Repository was sorted by workers's birth dates successfully.");
	}

	public void sortByEnteringDate(){
		workersRepository.sortByEnteringDate();
		System.out.println("Repository was sorted by workers's entering dates successfully.");
	}

    /**
     * Read/write methods for files
     */

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
		System.out.println("Workers's list has been written to .xml file successfully.");
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
				for (int i = 0; i < lines.size(); i++){
					Worker worker = new Worker();
					worker.setFullName(lines.get(i));
					worker.setBirthDate(
							sdf.parse(lines.get(i+1)));
					worker.setEnteringDate(
							sdf.parse(lines.get(i+2)));
					i += 2;
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
		System.out.println("Workers's list has been read from .txt file successfully.");
	}

	/**
	 * Service for manager's subordinates list.
	 * @param manager the manager with the list of whom the work is going to be done.
	 * @param subordinate the person with whom the work will be done.
	 */

	public void addSubordinateToManager(Manager manager, Worker subordinate){
		manager.addSubordinate(subordinate);
	}

	public void removeSubordinateFromManager(Manager manager, int subId){
		manager.removeSubordinate(subId);
	}

	public Worker getManagersSubordinateById(Manager manager, int subId){
		return manager.getSubordinateById(subId);
	}
}