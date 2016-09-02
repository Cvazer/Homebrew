package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Skill;
import by.zti.dnd.model.Stats;
import by.zti.dnd.utils.Serealizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class SkillsListView {
	private BorderPane pane;

	private static ObservableList<Skill> skills;

	@FXML
	private ListView<Skill> list_skills;

	public void init() {
		try {
			skills = FXCollections.observableArrayList();
			pane = (BorderPane) FXMLLoader.load(SkillsListView.class.getResource("SkillsListPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		list_skills.setCellFactory(new Callback<ListView<Skill>, ListCell<Skill>>() {
			@Override
			public ListCell<Skill> call(ListView<Skill> param) {
				ListCell<Skill> cell = new ListCell<Skill>();

				ContextMenu menu = new ContextMenu();

				MenuItem delete = new MenuItem("Delete");
				MenuItem edit = new MenuItem("Edit");

				delete.setOnAction(e -> {
					new File("res/skills/" + cell.getItem().getName() + ".skl").delete();
					loadSkills();
				});

				edit.setOnAction(e -> {
					NewSkillWindow.skill = cell.getItem();
					NewSkillWindow window = new NewSkillWindow();
					window.init();
				});

				menu.getItems().addAll(delete, edit);

				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setText("");
						cell.setContextMenu(null);
					} else {
						cell.setText(cell.getItem().getName());
						cell.setContextMenu(menu);
					}
				});

				return cell;
			}
		});
		list_skills.setItems(skills);
		list_skills.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2 && click.getButton() == MouseButton.PRIMARY && MainWindow.current_sheet.getValue()!=null) {
				MainWindow.current_sheet.get().skills.add(list_skills.getSelectionModel().getSelectedItem());
				update();
			}
		});
	}

	public static void loadSkills() {
		Skill nulifier = new Skill("nulifier", Stats.Stat.STR);
		skills.add(nulifier);
		skills.remove(nulifier);
		skills.clear();
		File folder = new File("res/skills");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.getName().contains("skl")) {
				skills.add((Skill) Serealizator.deSerealize(file));
			}
		}
	}
	
	private void update() {
		CharSheet sheet = MainWindow.current_sheet.get();
		Serealizator.serealize(sheet, "res/sheets/" + sheet.getToken() + ".chr");
		MainWindow.current_sheet.set(null);
		MainWindow.current_sheet.set(sheet);
	}

	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}

}
