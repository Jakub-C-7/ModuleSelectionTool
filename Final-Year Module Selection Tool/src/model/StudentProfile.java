package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;


/**
 * This class allows the creation of a StudentProfile with appropriate information
 * 
 * @author Jakub Chamera
 *
 */
public class StudentProfile implements Serializable {
	
	//default serial id
	private static final long serialVersionUID = 1L;
	//create fields
	private String pNumber;
	private Name studentName;
	private String email;
	private LocalDate date;
	private Course course;
	private Set<Module> selectedModules;
	private Set<Module> reservedModules;
	
	//constructor for empty student profile
	public StudentProfile() {
		pNumber = "";
		studentName = new Name();
		email = "";
		date = null;
		course = null;
		selectedModules = new TreeSet<Module>();
		reservedModules = new TreeSet<Module>();
	}
	
	
	//get and set methods
	public String getpNumber() {
		return pNumber;
	}
	
	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}
	
	public Name getStudentName() {
		return studentName;
	}
	
	public void setStudentName(Name studentName) {
		this.studentName = studentName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getSubmissionDate() {
		return date;
	}
	
	public void setSubmissionDate(LocalDate date) {
		this.date = date;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	//adds a selected module
	public boolean addSelectedModule(Module m) {
		return selectedModules.add(m);
	}
	
	//gets all modules that have been selected
	public Set<Module> getAllSelectedModules() {
		return selectedModules;
	}
	
	//clears selections
	public void clearSelectedModules() {
		selectedModules.clear();
	}
	
	//method for reserving a module
	public boolean addReservedModule(Module m) {
		return reservedModules.add(m);
	}
	
	//gets all reserved modules
	public Set<Module> getAllReservedModules() {
		return reservedModules;
	}
	
	//clears reserved modules
	//MODIFIED THIS TO ACTUALLY CLEAR RESERVED MODULES
	public void clearReservedModules() {
		//selectedModules.clear();
		reservedModules.clear();
	}
	
	//to string method that returns all of a student profile's details
	@Override
	public String toString() {
		return "StudentProfile:[pNumber=" + pNumber + ", studentName="
				+ studentName + ", email=" + email + ", date="
				+ date + ", course=" + course.actualToString() 
				+ ", selectedModules=" + selectedModules
				+ ", reservedModules=" + reservedModules + "]";
	}
	
	public String actualToString() {
		return "Student name: " + studentName.getFullName() + "\nP number: " + pNumber + "\nEmail: " + email +
				"\nInput date: " + date + "\nCourse: " + course.getCourseName();
	}
	
}
