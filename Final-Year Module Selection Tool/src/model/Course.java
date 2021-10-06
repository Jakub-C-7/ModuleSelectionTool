package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that stores methods for creating, getting and setting a course.
 * Includes methods for module access and addition e.g. addModule or getAllModulesOnCourse or getModuleByCode
 * 
 * @author Jakub Chamera
 *
 */
public class Course implements Serializable{
	
	//default serial id
	private static final long serialVersionUID = 1L;
	
	private String courseName;
	private Map<String, Module> modules;
	
	public Course(String courseName) {
		this.courseName = courseName;
		modules = new HashMap<String, Module>();
	}
	
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String name) {
		courseName = name;
	}
	
	public void addModule(Module m) {
		modules.put(m.getModuleCode(), m);
	}
	
	public Module getModuleByCode(String code) {
		return modules.get(code);
	}
	
	public Collection<Module> getAllModulesOnCourse() {
		return modules.values();
	}
	
	@Override
	public String toString() {
		//a non-standard toString that simply returns the course name,
		//so as to assist in displaying courses correctly in a ComboBox<Course>
		return courseName;
	}
	
	public String actualToString() {
		return "Course:[courseName=" + courseName + ", modules=" + modules + "]";
	}
	
}
