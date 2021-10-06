package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * This class simply provides an overview of personal details, selected modules and reserved modules.
 * 
 * @author Jakub Chamera
 *
 */
public class OverviewSelectionPane extends GridPane{

	//Fields 
	// -- top box
	TextArea txtUserDetails;
	// --  bottom box
	TextArea txtSelected, txtReserved;
	// -- bottom button
	Button btnSave;

	public OverviewSelectionPane() {
		//set styling for this pane
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setHgap(10);
		this.setVgap(10);
		
		//initialise fields
		txtUserDetails = new TextArea();
		txtUserDetails.setMinSize(100, 100);
		txtUserDetails.setPrefSize(300, 100);
		txtUserDetails.setEditable(false);
		txtUserDetails.setFont(Font.font("Calibri", 22));
		
		txtSelected = new TextArea();
		txtSelected.setMinSize(100, 100);
		txtSelected.setPrefSize(300, 100);
		txtSelected.setEditable(false);
		txtSelected.setFont(Font.font("Calibri", 14));
		
		txtReserved = new TextArea();
		txtReserved.setMinSize(100, 100);
		txtReserved.setPrefSize(300, 100);
		txtReserved.setEditable(false);
		txtReserved.setFont(Font.font("Calibri", 14));
		
		btnSave = new Button("Save Overview");
		btnSave.setPrefSize(100, 20);
		btnSave.setAlignment(Pos.CENTER);
		
		
		//Sub-containera
		HBox top = new HBox();
		top.getChildren().addAll(txtUserDetails);
		top.setSpacing(10);
		top.setAlignment(Pos.CENTER);
		HBox.setHgrow(txtUserDetails, Priority.ALWAYS);
		
		HBox bottom = new HBox();
		bottom.getChildren().addAll(txtSelected, txtReserved);
		bottom.setSpacing(10);
		bottom.setAlignment(Pos.CENTER);
		HBox.setHgrow(txtSelected, Priority.ALWAYS);
		HBox.setHgrow(txtReserved, Priority.ALWAYS);
		
		//main container
		VBox main = new VBox();
		main.getChildren().addAll(top, bottom);
		main.setSpacing(10);
		main.setAlignment(Pos.CENTER);
		VBox.setVgrow(top, Priority.ALWAYS);
		VBox.setVgrow(bottom, Priority.ALWAYS);
		
		//bottom button box
		HBox bottomButton = new HBox();
		bottomButton.getChildren().add(btnSave);
		bottomButton.setSpacing(10);
		bottomButton.setAlignment(Pos.CENTER);
		
		//adding all main containers to this gridpane and allowing gridpane to grow with these containers
		this.add(main, 0, 0);
		this.add(bottomButton, 0, 1);
		
		//setting grow for main container
		GridPane.setVgrow(main, Priority.ALWAYS);
		GridPane.setHgrow(main, Priority.ALWAYS);
	}
	
	//methods for attatching event handlers
	public void addSaveOverviewButtonHandler(EventHandler<ActionEvent> handler) {
		btnSave.setOnAction(handler);
	}
	
	//get and set methods
	public void setUserDetails(String t){
		txtUserDetails.setText(t);
	}
	
	public void setSelected(String t){
		txtSelected.setText(t);
	}
	
	public void setReserved(String t){
		txtReserved.setText(t);
	}
	
	public TextArea getUserDetails(){
		return txtUserDetails;
	}
	
	public TextArea getSelected(){
		return txtSelected;
	}
	
	public TextArea getReserved(){
		return txtReserved;
	}

}
