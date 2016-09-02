package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Item;
import by.zti.dnd.model.Proficiency;
import by.zti.dnd.model.Skill;
import by.zti.dnd.model.Stats;
import by.zti.dnd.model.Trait;
import by.zti.dnd.network.Connector;
import by.zti.dnd.utils.Serealizator;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
	private Stage primaryStage;
	private BorderPane root;
	public static SimpleObjectProperty<CharSheet> current_sheet;

	@FXML
	private ListView<CharSheet> sheets_list;
	@FXML
	private TabPane tabPane;
	@FXML
	private TabPane tabPaneDM;

	@FXML
	private Tab overview;
	@FXML
	private Tab inventory;
	@FXML
	private Tab traitsDM;
	@FXML
	private Tab profsDM;

	@FXML
	private Tab traits;
	@FXML
	private Tab items;
	@FXML
	private Tab skills;
	@FXML
	private Tab profs;
	@FXML
	private Tab sheets;

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		primaryStage.setTitle("DnD-Info");
		FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
		root = (BorderPane) loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}

	@FXML
	private void initialize() {
		try{
		current_sheet = new SimpleObjectProperty<CharSheet>();
		current_sheet.addListener((obs, oldV, newV) -> {
			if (newV != null) {
				Overview pane = new Overview();
				pane.init();
				overview.setContent(pane.getPane());
				SheetsListView.loadSheets();

				InventoryTree inv = new InventoryTree();
				inv.init();
				inventory.setContent(inv.getPane());

				Proficiencies profs = new Proficiencies();
				profs.init();
				profsDM.setContent(profs.getPane());

				Traits trs = new Traits();
				trs.init();
				traitsDM.setContent(trs.getPane());
			}
		});

		traits.setOnSelectionChanged(e -> {
			if (traits.isSelected()) {
				TraitsListView pane = new TraitsListView();
				pane.init();
				traits.setContent(pane.getPane());
				TraitsListView.loadTraits();
			}
		});

		items.setOnSelectionChanged(e -> {
			if (items.isSelected()) {
				ItemsTreeView pane = new ItemsTreeView();
				pane.init();
				items.setContent(pane.getPane());
				ItemsTreeView.loadItems();
			}
		});

		skills.setOnSelectionChanged(e -> {
			if (skills.isSelected()) {
				SkillsListView pane = new SkillsListView();
				pane.init();
				skills.setContent(pane.getPane());
				SkillsListView.loadSkills();
			}
		});

		profs.setOnSelectionChanged(e -> {
			if (profs.isSelected()) {
				ProfsListView pane = new ProfsListView();
				pane.init();
				profs.setContent(pane.getPane());
				ProfsListView.loadProfs();
			}
		});

		sheets.setOnSelectionChanged(e -> {
			if (sheets.isSelected()) {
				SheetsListView pane = new SheetsListView();
				pane.init();
				sheets.setContent(pane.getPane());
				SheetsListView.loadSheets();
			}
		});

		for (Tab tab : tabPaneDM.getTabs()) {
			tabPaneDM.getSelectionModel().select(tab);
		}
		tabPaneDM.getSelectionModel().select(0);
		} catch (Exception e){}
	}

	@FXML
	public void newSheet() {
		NewSheetWindow window = new NewSheetWindow();
		window.init();
	}

	@FXML
	public void newTrait() {
		NewTraitWindow.trait = new Trait(Trait.Type.CLASS, "null");
		NewTraitWindow window = new NewTraitWindow();
		window.init();
	}

	@FXML
	public void newItem() {
		NewItemWindow.item = new Item(Item.Type.WEAPON_MELE, "null");
		NewItemWindow window = new NewItemWindow();
		window.init();
	}

	@FXML
	public void newSkill() {
		NewSkillWindow.skill = new Skill("null", Stats.Stat.STR);
		NewSkillWindow window = new NewSkillWindow();
		window.init();
	}

	@FXML
	public void newProf() {
		NewProfWindow.prof = new Proficiency(Proficiency.Type.WEAPON, "null");
		NewProfWindow window = new NewProfWindow();
		window.init();
	}
	
	@FXML
	public void mapEdit(){
		
	}

	public static void saveCurrentSheet() {
		Serealizator.serealize(current_sheet.get(), "res/sheets/" + current_sheet.get().getToken() + ".chr");
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Connector.itit();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		checkFolders();
		launch(args);
	}

	private static void checkFolders() {
		File res_folder = new File("res");
		if(!res_folder.exists()){
			res_folder.mkdirs();
			new File("res/sheets").mkdirs();
			new File("res/skills").mkdirs();
			new File("res/profs").mkdirs();
			new File("res/traits").mkdirs();
			new File("res/items").mkdirs();
		}
	}

}
