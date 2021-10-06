package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

/**
 * The Reserve module pane has a tab for term 1 and term 2.
 * Each of these tabs lists the unselected modules for that term.
 * This tabs offers the functionality of selecting a back-up set of modules 
 * 
 * @author Jakub Chamera
 *
 */
public class ReserveModulesPane extends Accordion{

	//declaring fields to be used throughout the class
	private Label lblUnselectedT1, lblUnselectedT2, lblReservedT1, lblReservedT2;
	private ListView<Module> txtUnselectedT1, txtUnselectedT2, txtReservedT1, txtReservedT2;
	//pane 1 and 2 of accordion
	private TitledPane t1, t2;
	//button boxes
	private Label lblButtonBox1, lblButtonBox2;
	private Button btnAdd1, btnRemove1, btnConfirm1, btnAdd2, btnRemove2, btnConfirm2;

	public ReserveModulesPane() {
		//set styling
		this.setPadding(new Insets(20, 20, 20, 20));


		//Fields
		lblUnselectedT1 = new Label("Unselected Term 1 modules");
		lblUnselectedT2 = new Label("Unselected Term 2 modules");
		lblReservedT1 = new Label("Reserved Term 1 modules");
		lblReservedT2 = new Label("Reserved Term 2 modules");
		txtUnselectedT1 = new ListView<Module>();
		txtUnselectedT2 = new ListView<Module>();
		txtReservedT1 = new ListView<Module>();
		txtReservedT2 = new ListView<Module>();

		txtUnselectedT1.setPrefSize(300, 200);
		txtUnselectedT2.setPrefSize(300, 200);
		txtReservedT1.setPrefSize(300, 200);
		txtReservedT1.setEditable(false);
		txtReservedT2.setPrefSize(300, 200);
		txtReservedT2.setEditable(false);


		//button box fields
		lblButtonBox1 = new Label("Reserve 30 credits worth of term 1 modules");
		lblButtonBox2 = new Label("Reserve 30 credits worth of term 2 modules");
		btnAdd1 = new Button("Add");
		btnAdd2 = new Button("Add");
		btnRemove1 = new Button("Remove");
		btnRemove2 = new Button("Remove");
		btnConfirm1 = new Button("Confirm");
		btnConfirm2 = new Button("Confirm");
		btnAdd1.setPrefSize(70, 20);
		btnAdd2.setPrefSize(70, 20);
		btnRemove1.setPrefSize(70, 20);
		btnRemove2.setPrefSize(70, 20);
		btnConfirm1.setPrefSize(70, 20);
		btnConfirm2.setPrefSize(70, 20);

		//Pane 1 sub-containers for left and right sides
		VBox left1 = new VBox();
		left1.getChildren().addAll(lblUnselectedT1, txtUnselectedT1);
		left1.setSpacing(10);
		left1.setPadding(new Insets(15, 20, 15, 20));
		VBox.setVgrow(txtUnselectedT1, Priority.ALWAYS);
		VBox right1 = new VBox();
		right1.getChildren().addAll(lblReservedT1, txtReservedT1);
		right1.setSpacing(10);
		right1.setPadding(new Insets(15, 20, 15, 20));
		VBox.setVgrow(txtReservedT1, Priority.ALWAYS);

		//Pane 2 sub-containers for left and right sides
		VBox left2 = new VBox();
		left2.getChildren().addAll(lblUnselectedT2, txtUnselectedT2);
		left2.setSpacing(10);
		left2.setPadding(new Insets(15, 20, 15, 20));
		VBox.setVgrow(txtUnselectedT2, Priority.ALWAYS);
		VBox right2 = new VBox();
		right2.getChildren().addAll(lblReservedT2, txtReservedT2);
		right2.setSpacing(10);
		right2.setPadding(new Insets(15, 20, 15, 20));
		VBox.setVgrow(txtReservedT2, Priority.ALWAYS);

		//1st pane container that encapsulates left and right sub-containers
		HBox container1 = new HBox();
		container1.getChildren().addAll(left1, right1);
		container1.setSpacing(10);
		container1.setPadding(new Insets(15, 20, 15, 20));
		HBox.setHgrow(left1, Priority.ALWAYS);
		HBox.setHgrow(right1, Priority.ALWAYS);
		//2nd pane container that encapsulates left and right sub-containers
		HBox container2 = new HBox();
		container2.getChildren().addAll(left2, right2);
		container2.setSpacing(10);
		container2.setPadding(new Insets(15, 20, 15, 20));
		HBox.setHgrow(left2, Priority.ALWAYS);
		HBox.setHgrow(right2, Priority.ALWAYS);

		//Button Box sub-containers
		HBox buttonBox1 = new HBox();
		buttonBox1.getChildren().addAll(lblButtonBox1, btnAdd1, btnRemove1, btnConfirm1);
		buttonBox1.setSpacing(10);
		buttonBox1.setPadding(new Insets(15, 20, 15, 20));
		buttonBox1.setAlignment(Pos.CENTER);
		HBox buttonBox2 = new HBox();
		buttonBox2.getChildren().addAll(lblButtonBox2, btnAdd2, btnRemove2, btnConfirm2);
		buttonBox2.setSpacing(10);
		buttonBox2.setPadding(new Insets(15, 20, 15, 20));
		buttonBox2.setAlignment(Pos.CENTER);


		//Main containers that each hold a container and a buttonBox
		GridPane main1 = new GridPane();
		main1.add(container1, 0, 0);
		main1.add(buttonBox1, 0, 1);
		main1.setVgap(10);
		main1.setHgap(10);
		GridPane.setVgrow(container1, Priority.ALWAYS);
		GridPane.setHgrow(container1, Priority.ALWAYS);
		GridPane main2 = new GridPane();
		main2.add(container2, 0, 0);
		main2.add(buttonBox2, 0, 1);
		main2.setVgap(10);
		main2.setHgap(10);
		GridPane.setVgrow(container2, Priority.ALWAYS);
		GridPane.setHgrow(container2, Priority.ALWAYS);


		//Pane 1 and 2 of accordion
		t1 = new TitledPane("Term 1 modules", main1);
		t2 = new TitledPane("Term 2 modules", main2);

		//add all
		this.getPanes().addAll(t1, t2);
		this.setExpandedPane(t1);
		t2.setDisable(true);
	}

	//methods to attach button handlers
	public void addTerm1AddButtonHandler(EventHandler<ActionEvent> handler) {
		btnAdd1.setOnAction(handler);
	}

	public void addTerm2AddButtonHandler(EventHandler<ActionEvent> handler) {
		btnAdd2.setOnAction(handler);
	}

	public void addTerm1ConfirmButtonHandler(EventHandler<ActionEvent> handler) {
		btnConfirm1.setOnAction(handler);
	}

	public void addTerm2ConfirmButtonHandler(EventHandler<ActionEvent> handler) {
		btnConfirm2.setOnAction(handler);
	}

	public void addTerm1RemoveButtonHandler(EventHandler<ActionEvent> handler) {
		btnRemove1.setOnAction(handler);
	}

	public void addTerm2RemoveButtonHandler(EventHandler<ActionEvent> handler) {
		btnRemove2.setOnAction(handler);
	}

	//other methods

	//adding a module to a given list
	public void addSelectedModule(Module m, ListView<Module> list) {
		list.getItems().addAll(m);
	}

	//removing a module from a given list
	public void removeSelectedModule(Module m, ListView<Module> list) {
		list.getItems().remove(m);
	}

	public ListView<Module> getUnselectedModulesT1(){
		return txtUnselectedT1;
	}

	public ListView<Module> getUnselectedModulesT2(){
		return txtUnselectedT2;
	}

	public ListView<Module> getReservedModulesT1(){
		return txtReservedT1;
	}

	public ListView<Module> getReservedModulesT2(){
		return txtReservedT2;
	}
	
	//get methods for titledPanes
	public TitledPane getTitledPane1() {
		return t1;
	}
	
	public TitledPane getTitledPane2() {
		return t2;
	}

}
