package model;

import java.io.Serializable;

/**
 * The name of a student consists of a first name string and a last name string
 * 
 * @author Jakub Chamera
 *
 */
public class Name implements Serializable{

	//default serial id
	private static final long serialVersionUID = 1L;
	//create fields
	private String firstName;
	private String familyName;

	
	//Constructor for empty name
	public Name() {
		firstName = "";
		familyName = "";
	}
	
	//Default constructor for name
	public Name(String firstName, String familyName) {
		this.firstName = firstName;
		this.familyName = familyName;
	}

	//get and set methods
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getFullName() {
		if (firstName.equals("") && familyName.equals("")) {
			return "";
		} else {
			return firstName + " " + familyName;
		}
	}

	//to string method returning first and last name
	@Override
	public String toString() {
		return "Name:[firstName=" + firstName + ", familyName=" + familyName + "]";
	}
	
}
