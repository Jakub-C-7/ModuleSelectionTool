package view;

import java.time.LocalDate;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Course;
import model.Name;

/**
 * The CreateProfilePane is the first tab that the user is faced with.
 * 
 * This pane displays a combo-box, pre-populated with the two courses.
 * Displays five text fields for inputting a student P-Number, first and last name, email and submission date
 * There is also a create profile button.
 * 
 * @author Jakub Chamera
 *
 */

public class CreateProfilePane extends GridPane {

	private ComboBox<Course> cboCourse;
	private TextField txtSurname, txtFirstName, txtPnumber, txtEmail;
	private DatePicker inputDate;
	private Button btnCreate;

	public CreateProfilePane() {
		//styling
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.RIGHT);

		this.getColumnConstraints().addAll(column0);

		//create labels
		Label lblTitle = new Label("Select course: ");
		Label lblPnumber = new Label("Input P number: ");
		Label lblFirstName = new Label("Input first name: ");
		Label lblSurname = new Label("Input surname: ");
		Label lblEmail = new Label("Input email: ");
		Label lblDate = new Label("Input date: ");

		//setup combobox
		cboCourse = new ComboBox<Course>(); //will be populated via method towards end of class

		//setup text fields
		txtFirstName = new TextField();
		txtSurname = new TextField();
		txtPnumber = new TextField();
		txtEmail = new TextField();

		inputDate = new DatePicker();

		//initialise create button
		btnCreate = new Button("Create Profile");

		//add controls and labels to container
		this.add(lblTitle, 0, 0);
		this.add(cboCourse, 1, 0);

		this.add(lblPnumber, 0, 1);
		this.add(txtPnumber, 1, 1);

		this.add(lblFirstName, 0, 2);
		this.add(txtFirstName, 1, 2);

		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);

		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);

		this.add(lblDate, 0, 5);
		this.add(inputDate, 1, 5);

		this.add(new HBox(), 0, 6);
		this.add(btnCreate, 1, 6);
	}

	//method to allow the controller to populate the course combobox
	public void populateCourseComboBox(List<Course> courses) {
		cboCourse.getItems().addAll(courses);
		cboCourse.getSelectionModel().select(0); //select first course by default
	}


	//methods to retrieve the form selection/input
	public Course getSelectedCourse() {
		return cboCourse.getSelectionModel().getSelectedItem();
	}

	public String getPnumberInput() {
		return txtPnumber.getText();
	}

	public Name getName() {
		return new Name(txtFirstName.getText(), txtSurname.getText());
	}

	public String getEmail() {
		return txtEmail.getText();
	}

	public LocalDate getDate() {
		return inputDate.getValue();
	}

	//set methods
	public void setSelectedCourse(Course c) {
		cboCourse.getSelectionModel().select(c);
	}

	public void setPnumberInput(String s) {
		txtPnumber.setText(s);
	}

	public void setName(Name n) {
		txtFirstName.setText(n.getFirstName());
		txtSurname.setText(n.getFamilyName());

	}

	public void setEmail(String s) {
		txtEmail.setText(s);
	}

	public void setDate(LocalDate d) {
		inputDate.setValue(d);
	}


	//method to attach the create profile button event handler
	public void addCreateProfileHandler(EventHandler<ActionEvent> handler) {
		btnCreate.setOnAction(handler);
	}

}
