package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Proficiency;
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

public class ProfsListView {
	private BorderPane pane;
	
	private static ObservableList<Proficiency> profs;
	
	@FXML
	private ListView<Proficiency> list_profs;
	
	public void init() {
		try {
			profs = FXCollections.observableArrayList();
			pane = (BorderPane) FXMLLoader.load(ProfsListView.class.getResource("ProfsListPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		list_profs.setCellFactory(new Callback<ListView<Proficiency>, ListCell<Proficiency>>() {		
			@Override
			public ListCell<Proficiency> call(ListView<Proficiency> param) {
				ListCell<Proficiency> cell = new ListCell<Proficiency>();
				
				ContextMenu menu = new ContextMenu();

				MenuItem delete = new MenuItem("Delete");
				MenuItem edit = new MenuItem("Edit");
				
				delete.setOnAction(e -> {
					new File("res/profs/" + cell.getItem().getName() + ".prf").delete();
					loadProfs();
				});
				
				edit.setOnAction(e -> {
					NewProfWindow.prof = cell.getItem();
					NewProfWindow window = new NewProfWindow();
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
		list_profs.setItems(profs);
		list_profs.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2 && click.getButton() == MouseButton.PRIMARY && MainWindow.current_sheet.getValue()!=null) {
				MainWindow.current_sheet.get().profs.add(list_profs.getSelectionModel().getSelectedItem());
				update();
			}
		});
	}
	
	public static void loadProfs() {
		Proficiency nulifier = new Proficiency(Proficiency.Type.WEAPON, "null");
		profs.add(nulifier);
		profs.remove(nulifier);
		profs.clear();
		File folder = new File("res/profs");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.getName().contains("prf")) {
				profs.add((Proficiency) Serealizator.deSerealize(file));
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
