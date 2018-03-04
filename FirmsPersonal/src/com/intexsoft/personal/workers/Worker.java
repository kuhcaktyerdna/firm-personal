package com.intexsoft.personal.workers;

import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

public class Worker {

	private String fullName;
	private Date birthDate;
	private Date enteringDate;
	private int id;
	private int subId = 0;
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("BY"));


    public Worker(){}

	public Worker(String fullName, Date birthDate, Date enteringDate){
		setFullName(fullName);
		setBirthDate(birthDate);
		setEnteringDate(enteringDate);

	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getEnteringDate() {
		return enteringDate;
	}

	public void setEnteringDate(Date enteringDate) {
		this.enteringDate = enteringDate;
	}

	public int getId(){
	    return id;
    }

    public void setId(int id){
	    this.id = id;
    }

    public int getSubId(){
        return subId;
    }

    public void setSubId(int subId){
        this.subId = subId;
    }

    public String toString(){
    	return "name:" + getFullName() + "\nbirth date: " +
                dateFormat.format(getBirthDate()) +
                "\nentering date: " + dateFormat.format(getEnteringDate());
    }

}