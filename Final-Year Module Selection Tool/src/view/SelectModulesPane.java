package view;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

/**
 * This class allows the Selection of modules based on the course selected in the CreateProfilePane
 * 
 * @author Jakub Chamera
 *
 */
public class SelectModulesPane extends GridPane{

	//Fields

	//LEFT SIDE ----------
	private Label lblUnT1, lblUnT1Buttons, lblUnT2, lblUnT2Buttons;;
	private ListView<Module> txtUnT1, txtUnT2;
	private Button btnUnT1Add, btnUnT1Remove, btnUnT2Add, btnUnT2Remove;

	//RIGHT SIDE -----------
	private Label lblSelYLong, lblSelT1, lblSelT2;
	private ListView <Module> txtSelYLong, txtSelT1, txtSelT2;

	//Total for TERM 1 and total for TERM 2
	private Label lblT1Total, lblT2Total;
	private TextField txtT1Total, txtT2Total;

	//Bottom buttons
	private Button btnReset, btnSubmit;


	public SelectModulesPane() {
		//set styling for this pane
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setHgap(10);
		this.setVgap(10);

		//Initialising fields and buttons ---------

		//LEFT SIDE --- top ---
		//unselected modules term 1 box
		lblUnT1 = new Label("Unselected Term 1 modules");
		txtUnT1 = new ListView<Module>();
		txtUnT1.setEditable(false);
		txtUnT1.setMinSize(100, 100);
		txtUnT1.setPrefSize(300, 100);
		//unselected modules term 1 add and remove buttons + label
		lblUnT1Buttons = new Label("Term 1");
		btnUnT1Add = new Button("Add");
		btnUnT1Remove = new Button("Remove");
		btnUnT1Add.setPrefSize(70, 20);
		btnUnT1Remove.setPrefSize(70, 20);

		//LEFT SIDE --- bottom ---
		//unselected modules term 2 box
		lblUnT2 = new Label("Unselected Term 2 modules");
		txtUnT2 = new ListView<Module>();
		txtUnT2.setEditable(false);
		txtUnT2.setMinSize(100, 100);
		txtUnT2.setPrefSize(300, 100);
		//unselected modules term 2 add and remove buttons + label
		lblUnT2Buttons = new Label("Term 2");
		btnUnT2Add = new Button("Add");
		btnUnT2Remove = new Button("Remove");
		btnUnT2Add.setPrefSize(70, 20);
		btnUnT2Remove.setPrefSize(70, 20);

		//RIGHT SIDE -----
		//year long modules list
		lblSelYLong = new Label("Selected Year Long modules");
		txtSelYLong = new ListView<Module>();
		txtSelYLong.setEditable(false);
		txtSelYLong.setPrefSize(100, 40);

		//List for selected term 1 modules
		lblSelT1 = new Label("Selected Term 1 modules");
		txtSelT1 = new ListView<Module>();
		txtSelT1.setEditable(false);
		txtSelT1.setMinSize(100, 100);
		txtSelT1.setPrefSize(300, 100);

		//List for selected term 2 modules
		lblSelT2 = new Label("Selected Term 2 modules");
		txtSelT2 = new ListView<Module>();
		txtSelT2.setEditable(false);
		txtSelT2.setMinSize(100, 100);
		txtSelT2.setPrefSize(300, 100);

		//Term 1 total credits -- will be automatically updated and cannot be editable
		lblT1Total = new Label("Current term 1 credits:");
		txtT1Total = new TextField("0");
		txtT1Total.setEditable(false);
		txtT1Total.setPrefSize(50, 30);
		//Term 2 total credits -- will be automatically updated and cannot be editable
		lblT2Total = new Label("Current term 2 credits:");
		txtT2Total = new TextField("0");
		txtT2Total.setEditable(false);
		txtT2Total.setPrefSize(50, 30);

		//bottom button fields for resetting all fields and submitting choices
		btnReset = new Button("Reset");
		btnSubmit = new Button("Submit");
		//btnReset.setPrefSize(70, 20);
		//btnSubmit.setPrefSize(70, 20);

		//----- Creating Boxes to contain all of my elements in a neat layout --------------

		//LEFT side boxes

		//Term 1 button box
		HBox t1ButtonsBox = new HBox();
		t1ButtonsBox.getChildren().addAll(lblUnT1Buttons, btnUnT1Add, btnUnT1Remove);
		t1ButtonsBox.setSpacing(10);
		t1ButtonsBox.setAlignment(Pos.CENTER);

		//LEFT TOP BOX
		VBox leftTopBox = new VBox();
		leftTopBox.getChildren().addAll(lblUnT1, txtUnT1, t1ButtonsBox);
		leftTopBox.setSpacing(10);

		// --


		//Term 1 total box sub-container
		HBox term1TotalBox = new HBox();
		term1TotalBox.getChildren().addAll(lblT1Total, txtT1Total);
		term1TotalBox.setSpacing(10);
		term1TotalBox.setAlignment(Pos.CENTER_RIGHT);

		//Term 2 total box sub-container
		HBox term2TotalBox = new HBox();
		term2TotalBox.getChildren().addAll(lblT2Total, txtT2Total);
		term2TotalBox.setSpacing(10);
		term2TotalBox.setAlignment(Pos.CENTER_RIGHT);

		// --

		//Term 2 button box
		HBox t2ButtonsBox = new HBox();
		t2ButtonsBox.getChildren().addAll(lblUnT2Buttons, btnUnT2Add, btnUnT2Remove);
		t2ButtonsBox.setSpacing(10);
		t2ButtonsBox.setAlignment(Pos.CENTER);

		//LEFT bottom box sub-container
		VBox leftBottomBox = new VBox();
		leftBottomBox.getChildren().addAll(lblUnT2, txtUnT2, t2ButtonsBox, term1TotalBox);
		leftBottomBox.setSpacing(10);

		//Entire LEFT side container that contains two sub-containers (leftTop and leftBottom)
		//Left side split up to allow for a neater layout
		VBox leftMainContainer = new VBox();
		leftMainContainer.getChildren().addAll(leftTopBox, leftBottomBox);
		leftMainContainer.setSpacing(10);
		leftMainContainer.setPadding(new Insets(15, 20, 15, 20));
		VBox.setVgrow(leftTopBox, Priority.ALWAYS);
		VBox.setVgrow(leftBottomBox, Priority.ALWAYS);
		VBox.setVgrow(txtUnT1, Priority.ALWAYS);
		VBox.setVgrow(txtUnT2, Priority.ALWAYS);


		//Entire RIGHT side container
		VBox rightMainContainer = new VBox();
		rightMainContainer.getChildren().addAll(lblSelYLong, txtSelYLong, lblSelT1, txtSelT1, lblSelT2, txtSelT2, term2TotalBox);
		rightMainContainer.setSpacing(10);
		rightMainContainer.setPadding(new Insets(15, 20, 15, 20));
		VBox.setVgrow(txtSelYLong, Priority.ALWAYS);
		VBox.setVgrow(txtSelT1, Priority.ALWAYS);
		VBox.setVgrow(txtSelT2, Priority.ALWAYS);


		//BOTTOM box containing the Reset and Submit buttons
		HBox bottomButtonContainer = new HBox();
		bottomButtonContainer.getChildren().addAll(btnReset, btnSubmit);
		bottomButtonContainer.setSpacing(10);
		bottomButtonContainer.setAlignment(Pos.CENTER);

		//MAIN container with the left and right boxes
		HBox mainContainer = new HBox();
		mainContainer.getChildren().addAll(leftMainContainer, rightMainContainer);
		mainContainer.setSpacing(10);
		mainContainer.setAlignment(Pos.CENTER);
		HBox.setHgrow(leftMainContainer, Priority.ALWAYS);
		HBox.setHgrow(rightMainContainer, Priority.ALWAYS);



		//add main containers and set rows and columns for them
		this.add(mainContainer, 0, 0);
		this.add(bottomButtonContainer, 0, 1);

		//allow components to grow vertically and horizontally within the main containers
		GridPane.setHgrow(mainContainer, Priority.ALWAYS);
		GridPane.setVgrow(mainContainer, Priority.ALWAYS);

	}

	//methods to attach the create event handlers
	public void addResetButtonHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}

	public void addSubmitButtonHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}

	public void addUnselectedTerm1AddButtonHandler(EventHandler<ActionEvent> handler) {
		btnUnT1Add.setOnAction(handler);
	}

	public void addUnselectedTerm2AddButtonHandler(EventHandler<ActionEvent> handler) {
		btnUnT2Add.setOnAction(handler);
	}

	public void addTerm1RemoveButtonHandler(EventHandler<ActionEvent> handler) {
		btnUnT1Remove.setOnAction(handler);
	}

	public void addTerm2RemoveButtonHandler(EventHandler<ActionEvent> handler) {
		btnUnT2Remove.setOnAction(handler);
	}

	//adding a module to a given list
	public void addSelectedModule(Module m, ListView<Module> list) {
		list.getItems().addAll(m);
	}
	
	//removing a module from a given list
	public void removeSelectedModule(Module m, ListView<Module> list) {
		list.getItems().remove(m);
	}

	//get method for total credits
	public int getTotalCredits(TextField field) {
		return Integer.parseInt(field.getText());
	}

	//adds a certain number of credits to the current total
	public void addCredits(int i, TextField field) {
		int credits = Integer.parseInt(field.getText());
		credits = credits + i;
		field.setText(Integer.toString(credits));  
	}
	
	//subtracts a certain number of credits from the current total
	public void removeCredits(int i, TextField field) {
		int credits = Integer.parseInt(field.getText());
		credits = credits - i;
		field.setText(Integer.toString(credits));  
	}


	public ListView<Module> getUnselectedModulesT1(){
		return txtUnT1;
	}

	public ListView<Module> getUnselectedModulesT2(){
		return txtUnT2;
	}

	public ListView<Module> getSelectedModulesT1(){
		return txtSelT1;
	}

	public ListView<Module> getSelectedModulesT2(){
		return txtSelT2;
	}


	public ListView<Module> getYearLongModules(){
		return txtSelYLong;
	}
	
	public TextField getTotalT1Field(){
		return txtT1Total;
	}
	
	public TextField getTotalT2Field(){
		return txtT2Total;
	}



}
