package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.Course;
import model.Delivery;
import model.Module;
import model.StudentProfile;
import view.ModuleSelectionRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateProfilePane;
import view.ModuleSelectionMenuBar;

/**
 * The ModuleSelectionController class contains event handlers and most general behaviour of the
 * Module Selection Program. 
 * 
 * This class initialises the model, view and subcontainers
 * 
 * @author Jakub Chamera
 *
 */

public class ModuleSelectionController {

	//fields to be used throughout class
	private StudentProfile model;
	private ModuleSelectionRootPane view;

	private CreateProfilePane cpp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	private ModuleSelectionMenuBar msmb;


	//default constructor 
	public ModuleSelectionController(StudentProfile model, ModuleSelectionRootPane view) {
		//initialise model and view fields
		this.model = model;
		this.view = view;

		//initialise view subcontainer fields
		cpp = view.getCreateProfilePane();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();
		msmb = view.getModuleSelectionMenuBar();

		//populate combobox in create profile pane with courses using the setupAndGetCourses method below
		cpp.populateCourseComboBox(setupAndGetCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}


	//a helper method used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create profile pane
		cpp.addCreateProfileHandler(new CreateProfileHandler());

		// -- Select Module Pane --
		//attach event handlers for selecting modules
		smp.addUnselectedTerm1AddButtonHandler(new Term1AddModuleButtonHandler());
		smp.addUnselectedTerm2AddButtonHandler(new Term2AddModuleButtonHandler());
		//attach event handlers for removing selected modules
		smp.addTerm1RemoveButtonHandler(new Term1RemoveModuleButtonHandler());
		smp.addTerm2RemoveButtonHandler(new Term2RemoveModuleButtonHandler());
		//attach event handlers for submit and reset buttons for selectModulePane
		smp.addResetButtonHandler(new ResetButtonHandler());
		smp.addSubmitButtonHandler(new SubmitButtonHandler());
		// -- Reserve Module Pane --
		rmp.addTerm1AddButtonHandler(new ReserveTerm1AddButtonHandler());
		rmp.addTerm2AddButtonHandler(new ReserveTerm2AddButtonHandler());
		rmp.addTerm1RemoveButtonHandler(new ReserveTerm1RemoveButtonHandler());
		rmp.addTerm2RemoveButtonHandler(new ReserveTerm2RemoveButtonHandler());
		rmp.addTerm1ConfirmButtonHandler(new ReserveConfirmTerm1ButtonHandler());
		rmp.addTerm2ConfirmButtonHandler(new ReserveConfirmTerm2ButtonHandler());
		// -- Overview Selection Pane --
		osp.addSaveOverviewButtonHandler(new SaveOverviewSelection());

		//attach an event handler to the menu bar that closes the application
		msmb.addExitHandler(e -> System.exit(0));
		//attach load handler
		msmb.addLoadHandler(new MenuLoadHandler());
		//attach save handler
		msmb.addSaveHandler(new MenuSaveHandler());
		//attach about handler
		msmb.addAboutHandler(e -> this.alertDialogBuilder(AlertType.INFORMATION, "About", "Module selection application v1.0", "This application allows you to choose third year modules for a selection of courses"));
	}

	// Top Menu Bar handlers
	private class MenuLoadHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e) {
			//load in the data model
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("studentProfile.dat"))){

				model = (StudentProfile) ois.readObject(); //reads the object in from a file

				//pop up confirmation
				alertDialogBuilder(AlertType.INFORMATION, "Profile Load", "Load Successful", "Profile loaded from: " + "studentProfile.dat");

			} catch (IOException ioExcept) {
				System.out.println("Error Loading");
			}
			catch (ClassNotFoundException c) {
				System.out.println("Class not found");
			}
			//re-populate the student profile fields 
			cpp.setSelectedCourse(model.getCourse());
			cpp.setPnumberInput(model.getpNumber());
			cpp.setName(model.getStudentName());
			cpp.setEmail(model.getEmail());
			cpp.setDate(model.getSubmissionDate());
		}
	}

	//saves the model into a file using an object output stream
	private class MenuSaveHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e) {
			//save the data model
			try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("studentProfile.dat"))){

				//writes the model to a file
				oos.writeObject(model);
				oos.flush();
				//pop up confirmation
				alertDialogBuilder(AlertType.INFORMATION, "Profile Save", "Save Successful", "Student Profile saved to: " + "studentProfile.dat");

			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Error Saving");

			}
		}
	}

	// Tab 1 -- Profile Creation handlers ------------//////////////------------------------

	//This event handler creates a profile and based on course selection, populates the select module pane ListViews
	private class CreateProfileHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			//string to store all error messages
			String error = new String();
			//PNumber validation
			if (!cpp.getPnumberInput().startsWith("P") ) {
				error = error + "The P number needs to start with a capital 'P'\n";
			}
			if (cpp.getPnumberInput().length() != 8) {
				error = error + "The P number needs to be at least 8 characters\n";
			}
			//Name validation
			if (cpp.getName().getFirstName().length() < 1) {
				error = error + "The first name cannot be empty\n";
			}
			if (cpp.getName().getFamilyName().length() < 1) {
				error = error + "The last name cannot be empty\n";
			}
			//Email validation
			if (cpp.getEmail().length() < 3) {
				error = error + "The email cannot be less than 3 characters\n";
			}
			if (!cpp.getEmail().contains("@")) {
				error = error + "The email needs to contain the '@' symbol\n";
			}
			if (cpp.getEmail().startsWith("@")) {
				error = error + "The email cannot start with the '@' symbol\n";
			}
			if (cpp.getEmail().endsWith("@")) {
				error = error + "The email cannot end with the '@' symbol\n";
			}
			//Date validation
			if (!cpp.getDate().equals(LocalDate.now())) {
				error = error + "The input date has to be today's date\n";
			}
			//create a new student and assign him to this data model if all fields have data
			if (error.isEmpty()) {
				//capture data from fields and set to model
				model.setpNumber(cpp.getPnumberInput());
				model.setStudentName(cpp.getName());
				model.setCourse(cpp.getSelectedCourse());
				model.setEmail(cpp.getEmail());
				model.setSubmissionDate(cpp.getDate());

				smp.getUnselectedModulesT1().getItems().clear();
				smp.getUnselectedModulesT2().getItems().clear();
				smp.getSelectedModulesT1().getItems().clear();
				smp.getSelectedModulesT2().getItems().clear();
				smp.getYearLongModules().getItems().clear();

				smp.getTotalT1Field().setText("0");
				smp.getTotalT2Field().setText("0");

				//Sorts modules into appropriate boxes
				for (Module m: model.getCourse().getAllModulesOnCourse()) {
					//Checks for mandatory year long modules
					if (m.isMandatory() == true && m.getRunPlan() == Delivery.YEAR_LONG) {
						smp.addSelectedModule(m, smp.getYearLongModules());
						//add half of the year long credits to term 1 total and the other half to term 2 total
						smp.addCredits(m.getCredits() / 2, smp.getTotalT1Field());
						smp.addCredits(m.getCredits() / 2, smp.getTotalT2Field());

						//checks for non mandatory term 1 modules
					}else if (m.isMandatory() == false && m.getRunPlan() == Delivery.TERM_1 ) { //checks for term 1 modules
						smp.addSelectedModule(m, smp.getUnselectedModulesT1());

						//checks for mandatory term 1 modules
					}else if (m.isMandatory() == true && m.getRunPlan() == Delivery.TERM_1) {
						smp.addSelectedModule(m, smp.getSelectedModulesT1());
						//add credit for mandatory module
						smp.addCredits(m.getCredits(), smp.getTotalT1Field());

						//checks for non mandatory term 2 modules
					}else if (m.isMandatory() == false && m.getRunPlan() == Delivery.TERM_2) {
						smp.addSelectedModule(m, smp.getUnselectedModulesT2());

						//check for term 2 mandatory modules
					}else if (m.isMandatory() == true && m.getRunPlan() == Delivery.TERM_2) {
						smp.addSelectedModule(m, smp.getSelectedModulesT2());
						//add credits for mandatory module
						smp.addCredits(m.getCredits(), smp.getTotalT2Field());
					}

				}

				//enables tab 2 
				view.getTab2().setDisable(false);
				//changes to next tab
				view.changeTab(1);
			}else {
				alertDialogBuilder(AlertType.ERROR, "Invalid Data" , null, error.toString());
			}
		}
	}


	// Tab 2 -- Module Selection handlers ------------//////////////------------------------

	//handlers for selecting term 1 modules
	public class Term1AddModuleButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the unselected term 1 modules list
			Module selection = smp.getUnselectedModulesT1().getSelectionModel().getSelectedItem();

			//if there is an item selected
			if (!smp.getUnselectedModulesT1().getSelectionModel().isEmpty()) {

				if (smp.getTotalCredits(smp.getTotalT1Field()) <= 45) {

					//add selected module from the unselected term 1 list to the selected term 1 list
					smp.addSelectedModule(selection, smp.getSelectedModulesT1());
					//remove the module thats just been selected from the unselected term 1 module list
					smp.removeSelectedModule(selection, smp.getUnselectedModulesT1());	
					//add credits for that module
					smp.addCredits(selection.getCredits(), smp.getTotalT1Field());
				}
			}
		}
	}

	//handler for selecting term 2 module, updating the credit count and the selectedTerm2 box
	public class Term2AddModuleButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the unselected term 1 modules list
			Module selection = smp.getUnselectedModulesT2().getSelectionModel().getSelectedItem();

			//if there is an item selected
			if (!smp.getUnselectedModulesT2().getSelectionModel().isEmpty()) {

				if (smp.getTotalCredits(smp.getTotalT2Field()) <= 45) {

					//add selected module from the unselected term 2 modules list
					smp.addSelectedModule(selection, smp.getSelectedModulesT2());
					//remove the module thats just been selected from the unselected term 2 module list
					smp.removeSelectedModule(selection, smp.getUnselectedModulesT2());	
					//add credits for that module
					smp.addCredits(selection.getCredits(), smp.getTotalT2Field());
				}
			}
		}
	}

	//handler for removing selected term 1 module, updating the credit count and the unselectedTerm1 listView
	public class Term1RemoveModuleButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the selected term 1 modules list
			Module selection = smp.getSelectedModulesT1().getSelectionModel().getSelectedItem();

			//if there is a module selected an if the module isn't mandatory
			if (!smp.getSelectedModulesT1().getSelectionModel().isEmpty() && smp.getSelectedModulesT1().getSelectionModel().getSelectedItem().isMandatory() == false) {

				//add the selected module from selected term 1 module list back to the selected term 1 list
				smp.addSelectedModule(selection, smp.getUnselectedModulesT1());
				//remove the previously selected module from the selected list an
				smp.removeSelectedModule(selection, smp.getSelectedModulesT1());
				//remove credits for that module
				smp.removeCredits(selection.getCredits(), smp.getTotalT1Field());
			}
		}
	}

	//handler for removing selected term 2 module, updating the credit count and the unselectedTerm 2 listView
	public class Term2RemoveModuleButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the selected term 2 modules list
			Module selection = smp.getSelectedModulesT2().getSelectionModel().getSelectedItem();

			//if there is a module selected and if the module isn't mandatory
			if (!smp.getSelectedModulesT2().getSelectionModel().isEmpty() && smp.getSelectedModulesT2().getSelectionModel().getSelectedItem().isMandatory() == false) {

				//add the selected module from selected term 2 module list back to the selected term 2 list
				smp.addSelectedModule(selection, smp.getUnselectedModulesT2());
				//remove the previously selected module from the selected list an
				smp.removeSelectedModule(selection, smp.getSelectedModulesT2());
				//remove credits for that module
				smp.removeCredits(selection.getCredits(), smp.getTotalT2Field());
			}
		}
	}

	// The reset button handler resets the selectModulePane to the state it was right after the course was selected
	//It clears all selected modules and returns the mandatory ones into their fields
	public class ResetButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {

			//clears all fields and sets credit total to 0
			smp.getTotalT1Field().setText("0");
			smp.getTotalT2Field().setText("0");
			smp.getUnselectedModulesT1().getItems().clear();
			smp.getUnselectedModulesT2().getItems().clear();
			smp.getSelectedModulesT1().getItems().clear();
			smp.getSelectedModulesT2().getItems().clear();
			smp.getYearLongModules().getItems().clear();

			// -- Reads course data from model and populates fields back to default --
			//Sorts modules into appropriate boxes
			for (Module m: model.getCourse().getAllModulesOnCourse()) {
				//Checks for mandatory year long modules
				if (m.isMandatory() == true && m.getRunPlan() == Delivery.YEAR_LONG) {
					smp.addSelectedModule(m, smp.getYearLongModules());
					//add half of the year long credits to term 1 total and the other half to term 2 total
					smp.addCredits(m.getCredits() / 2, smp.getTotalT1Field());
					smp.addCredits(m.getCredits() / 2, smp.getTotalT2Field());

					//checks for non mandatory term 1 modules
				}else if (m.isMandatory() == false && m.getRunPlan() == Delivery.TERM_1 ) { //checks for term 1 modules
					smp.addSelectedModule(m, smp.getUnselectedModulesT1());

					//checks for mandatory term 1 modules
				}else if (m.isMandatory() == true && m.getRunPlan() == Delivery.TERM_1) {
					smp.addSelectedModule(m, smp.getSelectedModulesT1());
					//add credit for mandatory module
					smp.addCredits(m.getCredits(), smp.getTotalT1Field());

					//checks for non mandatory term 2 modules
				}else if (m.isMandatory() == false && m.getRunPlan() == Delivery.TERM_2) {
					smp.addSelectedModule(m, smp.getUnselectedModulesT2());

					//check for term 2 mandatory modules
				}else if (m.isMandatory() == true && m.getRunPlan() == Delivery.TERM_2) {
					smp.addSelectedModule(m, smp.getSelectedModulesT2());
					//add credits for mandatory module
					smp.addCredits(m.getCredits(), smp.getTotalT2Field());
				}
			}

		}
	}

	public class SubmitButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {

			if (smp.getTotalCredits(smp.getTotalT1Field()) == 60 && smp.getTotalCredits(smp.getTotalT2Field()) == 60) {
				//clear both unselected and reserve module fields before populating them
				rmp.getUnselectedModulesT1().getItems().clear();
				rmp.getUnselectedModulesT2().getItems().clear();
				rmp.getReservedModulesT1().getItems().clear();
				rmp.getReservedModulesT2().getItems().clear();

				//clear the model from any possible selected modules before adding 
				model.getAllSelectedModules().clear();

				//update the model with the selected modules
				for(Module m: smp.getSelectedModulesT1().getItems()){
					model.addSelectedModule(m);
				}
				for(Module m: smp.getSelectedModulesT2().getItems()){
					model.addSelectedModule(m);
				}

				//capture the modules that haven't yet been selected for term 1 and 2 and add these modules to the reserveModulePane
				for (Module m: smp.getUnselectedModulesT1().getItems()) {
					rmp.addSelectedModule(m, rmp.getUnselectedModulesT1());
				}
				for (Module m: smp.getUnselectedModulesT2().getItems()) {
					rmp.addSelectedModule(m, rmp.getUnselectedModulesT2());
				}

				//enable tab 3 (reserving modules)
				view.getTab3().setDisable(false);
				//change to next tab (reserving modules)
				view.changeTab(2);
			}else {
				alertDialogBuilder(AlertType.ERROR, "Invalid Selection", null, "Select 60 credits worth of Term 1 modules and 60 credits worth of Term 2 modules in order to continue.");
			}
		}
	}

	// Tab 3 -- Reserve Additional Modules handlers ------------//////////////------------------------

	//handlers for reserving term 1 modules
	public class ReserveTerm1AddButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the unselected term 1 modules list
			Module selection = rmp.getUnselectedModulesT1().getSelectionModel().getSelectedItem();

			//if there is an item selected
			if (!rmp.getUnselectedModulesT1().getSelectionModel().isEmpty()) {

				//only allows the addition of modules if there are less than 2 modules selected
				if (rmp.getReservedModulesT1().getItems().size() < 2) {

					//add selected module from the unselected term 1 list to the reserved term 1 list
					rmp.addSelectedModule(selection, rmp.getReservedModulesT1());
					//add reservation to model
					model.addReservedModule(selection);
					//remove the module thats just been selected from the unselected term 1 module list
					rmp.removeSelectedModule(selection, rmp.getUnselectedModulesT1());	
				}
			}
		}
	}

	//handlers for reserving term 2 modules
	public class ReserveTerm2AddButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the unselected term 2 modules list
			Module selection = rmp.getUnselectedModulesT2().getSelectionModel().getSelectedItem();

			//if there is an item selected
			if (!rmp.getUnselectedModulesT2().getSelectionModel().isEmpty()) {

				//only allows the addition of modules if there are less than 2 modules selected
				if (rmp.getReservedModulesT2().getItems().size() < 2) {

					//add selected module from the unselected term 2 list to the reserved term 2 list
					rmp.addSelectedModule(selection, rmp.getReservedModulesT2());
					//remove the module thats just been selected from the unselected term 2 module list
					rmp.removeSelectedModule(selection, rmp.getUnselectedModulesT2());	
				}
			}
		}
	}

	//handler for removing a reserved term 1 module and updating the unselectedTerm1 listView
	public class ReserveTerm1RemoveButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the reserved term 1 modules list
			Module selection = rmp.getReservedModulesT1().getSelectionModel().getSelectedItem();

			//if there is a module selected and if the module isn't mandatory
			if (!rmp.getReservedModulesT1().getSelectionModel().isEmpty() && rmp.getReservedModulesT1().getSelectionModel().getSelectedItem().isMandatory() == false) {

				//add the selected module from reserved term 1 module list back to the unselected term 1 list
				rmp.addSelectedModule(selection, rmp.getUnselectedModulesT1());
				//remove the previously selected module from the reserved list
				rmp.removeSelectedModule(selection, rmp.getReservedModulesT1());
			}
		}
	}

	//handler for removing a reserved term 2 module and updating the unselectedTerm2 listView
	public class ReserveTerm2RemoveButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			//A variable assigned to the selected item in the reserved term 2 modules list
			Module selection = rmp.getReservedModulesT2().getSelectionModel().getSelectedItem();

			//if there is a module selected and if the module isn't mandatory
			if (!rmp.getReservedModulesT2().getSelectionModel().isEmpty() && rmp.getReservedModulesT2().getSelectionModel().getSelectedItem().isMandatory() == false) {

				//add the selected module from reserved term 2 module list back to the unselected term 2 list
				rmp.addSelectedModule(selection, rmp.getUnselectedModulesT2());
				//remove the previously selected module from the reserved list
				rmp.removeSelectedModule(selection, rmp.getReservedModulesT2());
			}
		}
	}


	public class ReserveConfirmTerm1ButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {

			//if there are exactly 2 reserved modules
			if (rmp.getReservedModulesT1().getItems().size() == 2) {
				//clear before adding
				model.getAllReservedModules().clear();

				//add all reserved modules to model
				for(Module m: rmp.getReservedModulesT1().getItems()) {
					model.addReservedModule(m);
				}

				//opens the second pane and enables it
				rmp.setExpandedPane(rmp.getTitledPane2());
				rmp.getTitledPane2().setDisable(false);

			}else {
				alertDialogBuilder(AlertType.ERROR, "Invalid Selection", null, "Reserve 30 credits worth of Term 1 modules in order to continue.");
			}
		}
	}

	public class ReserveConfirmTerm2ButtonHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {

			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("Submission Confirmation");
			confirmation.setHeaderText("Are you sure that you want to submit?");
			confirmation.setContentText("You will not be able to go back and change your choices");


			//validation for T2 reserve modules
			if (rmp.getReservedModulesT2().getItems().size() == 2) {
				//display confirmation for the user to submit
				Optional<ButtonType> result = confirmation.showAndWait();
				if(result.get() == ButtonType.OK) { //user clicks ok

					//add all reserved modules to model
					for(Module m: rmp.getReservedModulesT2().getItems()) {
						model.addReservedModule(m);
					}

					//populates the user details textArea
					osp.setUserDetails(model.actualToString());

					//populating SelectedModules box
					String yearlongModules = new String();
					String term1SelectedModules = new String();
					String term2SelectedModules = new String();
					for(Module m: model.getCourse().getAllModulesOnCourse()) {
						if (m.getRunPlan().equals(Delivery.YEAR_LONG) && m.isMandatory()) {
							yearlongModules = yearlongModules + m.actualToString() + "\n";
						}
					}
					for(Module m: model.getAllSelectedModules()) {
						if (m.getRunPlan().equals(Delivery.TERM_1)) {
							term1SelectedModules = term1SelectedModules + m.actualToString() + "\n";
						}
					}
					for(Module m: model.getAllSelectedModules()) {
						if (m.getRunPlan().equals(Delivery.TERM_2)) {
							term2SelectedModules = term2SelectedModules + m.actualToString() + "\n";
						}
					}

					osp.setSelected("Selected Modules\n===================================\n" + "--Year Long Modules--\n" + 
							yearlongModules + "--Term 1 Modules--\n" + term1SelectedModules + "--Term 2 Modules--\n" + term2SelectedModules);

					//populating ReservedModules box
					String term1ReserveModules = new String();
					String term2ReserveModules = new String();
					for(Module m: model.getAllReservedModules()) {
						if (m.getRunPlan().equals(Delivery.TERM_1)) {
							term1ReserveModules = term1ReserveModules + m.actualToString() + "\n";
						}
					}
					for(Module m: model.getAllReservedModules()) {
						if (m.getRunPlan().equals(Delivery.TERM_2)) {
							term2ReserveModules = term2ReserveModules + m.actualToString() + "\n";
						}
					}
					osp.setReserved("Reserved Modules\n===================================\n" + "--Term 1 Modules--\n" +
							term1ReserveModules + "--Term 2 Modules--\n" + term2ReserveModules);

					//opens and enables the fourth tab
					view.changeTab(3);
					view.getTab4().setDisable(false);
					//disable all other tabs
					view.getTab1().setDisable(true);
					view.getTab2().setDisable(true);
					view.getTab3().setDisable(true);
				}
			} else {
				alertDialogBuilder(AlertType.ERROR, "Invalid Selection", null, "Reserve 30 credits worth of Term 2 modules in order to continue.");
			}
		}
	}

	// Tab 4 -- Overview Selection Pane handlers ------------//////////////------------------------

	public class SaveOverviewSelection implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			FileChooser chooser = new FileChooser();
			File file = chooser.showSaveDialog(null);

			//the below code saves it to a specific file path
			//File file = new File("/Users/kuba/Desktop/test.txt");

			//set the printwriter to write to the file chosen by the chooser
			try (PrintWriter writer = new PrintWriter(file)) {
				//write all data from OverviewSelectionPane
				writer.write(osp.getUserDetails().getText() + "\n");
				writer.write(osp.getSelected().getText() + "\n");
				writer.write(osp.getReserved().getText() + "\n");

				//confirmation of progressing onto the overview pane
				alertDialogBuilder(AlertType.INFORMATION, "Overview Save", "Save Successful" ,"Your overview '" + file.getName() + "' has been saved to: " + file.toString());

			} catch (FileNotFoundException e) {
				System.out.println("Error Saving: File not found");
			}
		}
	}

	/**
	 * Generates all module and course data and returns courses within an ArrayList. This method is a lot
	 * more re-usable as it reads all the information in from a text file. The text file can be modified 
	 * in the future to add/remove courses or modules.
	 * 
	 * @return
	 */
	private List<Course> setupAndGetCourses() {
		//creating empty arrays to store modules and courses
		List<Module> modulesList = new ArrayList<Module>();
		List<Course> coursesList = new ArrayList<Course>();

		//creating a scanner to read the text data file
		Scanner sc = null;
		try {
			sc = new Scanner(new File("courseDetails.txt")); //set the scanner to read from our file
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		//set the scanner's delimiters to angle brackets <> and new lines
		sc.useDelimiter("[<>\r\n]+");

		String currentItem = sc.next();           //get first element

		int courseIndex = -1; //index starts at -1. This will be incremented later so that first course added will be index 0		
		String courseName = null;
		Course course = null;

		//while not end
		while (!currentItem.equals("end")) {
			//while not new course
			if (!currentItem.equals("new course")) {
				String moduleName = sc.next();          //get next element
				int credits = sc.nextInt();				//get the credits as an int
				boolean mandatory = sc.nextBoolean();   //get mandatory as a boolean
				String runPlanString = sc.next();		//read in the runPlan as a string

				Delivery runPlan = null;				//initialise empty variable of type Delivery

				//convert string input from scanner to the enum type, Delivery
				if (runPlanString.equals("TERM_1")) {		
					runPlan = Delivery.TERM_1;
				}
				else if (runPlanString.equals("TERM_2")) {
					runPlan = Delivery.TERM_2;
				}
				else if (runPlanString.equals("YEAR_LONG")) {
					runPlan = Delivery.YEAR_LONG;
				}

				//create and add the module to the list
				modulesList.add(new Module(currentItem, moduleName, credits, mandatory, runPlan));
			}
			//if item is equal to "new course"
			else {
				for(Module m : modulesList) {
					course.addModule(m);
				}
				modulesList.clear();
				courseIndex = courseIndex + 1;
				courseName = sc.next();	//courseName is the string after "new course"
				course = new Course(courseName); //create new course with that name
				coursesList.add(course); //add the course to the list
			}
			currentItem = sc.next();              //read the next element

			//if we have reached "end"
			if (currentItem.equals("end")) {
				for(Module m : modulesList) {
					coursesList.get(courseIndex).addModule(m);
				}
				modulesList.clear();
			}
		}
		//close scanner
		sc.close();

		//create array list to store courses
		List<Course> courses = new ArrayList<Course>();
		for(Course c : coursesList) {
			courses.add(c);
		}
		return courses;
	}

	//helper method for building dialogs - will come handy throughout validation and with aspects of the menu bar
	public void alertDialogBuilder(AlertType type, String title, String header, String contents) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contents);
		alert.showAndWait();
	}

	/**
	 * old method for reading in course and module data
	 * generates all module and course data and returns courses within the array 'Course'
	 * @param type
	 * @param title
	 * @param header
	 * @param contents
	 */
	/*private Course[] setupAndGetCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Delivery.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Delivery.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Delivery.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Delivery.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Delivery.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModule(imat3423);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3902_CompSci);
		compSci.addModule(ctec3110);
		compSci.addModule(ctec3605);
		compSci.addModule(ctec3606);
		compSci.addModule(ctec3410);
		compSci.addModule(ctec3904);
		compSci.addModule(ctec3905);
		compSci.addModule(ctec3906);
		compSci.addModule(imat3410);
		compSci.addModule(imat3406);
		compSci.addModule(imat3611);
		compSci.addModule(imat3613);
		compSci.addModule(imat3614);
		compSci.addModule(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(imat3423);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3902_SoftEng);
		softEng.addModule(ctec3110);
		softEng.addModule(ctec3605);
		softEng.addModule(ctec3606);
		softEng.addModule(ctec3410);
		softEng.addModule(ctec3904);
		softEng.addModule(ctec3905);
		softEng.addModule(ctec3906);
		softEng.addModule(imat3410);
		softEng.addModule(imat3406);
		softEng.addModule(imat3611);
		softEng.addModule(imat3613);
		softEng.addModule(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
	 */
}
