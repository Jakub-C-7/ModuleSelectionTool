 
package main;

import controller.ModuleSelectionController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleSelectionRootPane;

/**
 * The Application loader starts the program
 * 
 * @author Jakub Chamera
 *
 */
public class ApplicationLoader extends Application {

	private ModuleSelectionRootPane view;
	
	@Override
	public void init() {
		//create view and model and pass their references to the controller
		//creates view
		view = new ModuleSelectionRootPane();
		//creates model
		StudentProfile model = new StudentProfile();
		
		//references being passes to the controller
		new ModuleSelectionController(model, view);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//sets min width and height for the stage window
		stage.setMinWidth(530); 
		stage.setMinHeight(500);
		
		//set title for the program
		stage.setTitle("Final Year Module Selection Tool");
		//set the scene of the stage to our view class (ModuleSelectionRootPane)
		stage.setScene(new Scene(view));
		//show the contents of the stage
		stage.show();
	}
	
	//launches application
	public static void main(String[] args) {
		launch(args);
	}

}
