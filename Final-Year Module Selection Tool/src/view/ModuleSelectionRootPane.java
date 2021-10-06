package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

/**
 * This is the root pane for the entire application. 
 * 
 * This class can be used to create new tabs and assign them to the appropriate sub-containers 
 * 
 * @author Jakub Chamera
 *
 */
public class ModuleSelectionRootPane extends BorderPane {

	private CreateProfilePane cpp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	private ModuleSelectionMenuBar msmb;
	private TabPane tp;
	private Tab t1,t2,t3,t4;

	public ModuleSelectionRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);


		//create panes
		cpp = new CreateProfilePane();
		smp = new SelectModulesPane();
		rmp = new ReserveModulesPane();
		osp = new OverviewSelectionPane();

		//create tabs with panes added
		t1 = new Tab("Create Profile", cpp);
		t2 = new Tab("Select Modules", smp);
		t3 = new Tab("Reserve Modules", rmp);
		t4 = new Tab("Overview Selection", osp);
		//disables tabs so that they cannot be opened
		t2.setDisable(true);
		t3.setDisable(true);
		t4.setDisable(true);

		//add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3, t4);

		//create menu bar
		msmb = new ModuleSelectionMenuBar();

		//add menu bar and tab pane to this root pane
		this.setTop(msmb);
		this.setCenter(tp);

	}

	//methods allowing sub-containers to be accessed by the controller.
	public CreateProfilePane getCreateProfilePane() {
		return cpp;
	}

	public SelectModulesPane getSelectModulesPane() {
		return smp;
	}

	public ReserveModulesPane getReserveModulesPane() {
		return rmp;
	}

	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}

	public ModuleSelectionMenuBar getModuleSelectionMenuBar() {
		return msmb;
	}
	
	//methods for allowing the retrieval of tabs through the controller
	public Tab getTab1() {
		return t1;
	}

	public Tab getTab2() {
		return t2;
	}
	
	public Tab getTab3() {
		return t3;
	}
	
	public Tab getTab4() {
		return t4;
	}
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
}
