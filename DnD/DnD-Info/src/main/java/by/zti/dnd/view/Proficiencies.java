package by.zti.dnd.view;

import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Proficiency;
import by.zti.dnd.utils.Serealizator;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class Proficiencies {
	private BorderPane pane;
	private static boolean done = false;
	private static TreeItem<Proficiency> root;
	private static TreeItem<Proficiency> language;
	private static TreeItem<Proficiency> weapon;
	private static TreeItem<Proficiency> armor;
	private static TreeItem<Proficiency> tool;
	
	public static ObservableList<Proficiency> profs;
	
	@FXML
	private TreeView<Proficiency> tree_profs;
	
	public void init() {
		try {
			profs = FXCollections.observableArrayList();
			setPane((BorderPane) FXMLLoader.load(Proficiencies.class.getResource("ProfsPanel.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		loadItems();
		tree_profs.setCellFactory(new Callback<TreeView<Proficiency>, TreeCell<Proficiency>>() {
			@Override
			public TreeCell<Proficiency> call(TreeView<Proficiency> param) {
				TreeCell<Proficiency> cell = new TreeCell<Proficiency>();
				
				ContextMenu menu = new ContextMenu();

				MenuItem delete = new MenuItem("Delete");
				
				delete.setOnAction(e -> {
					MainWindow.current_sheet.get().profs.remove(cell.getItem());
					MainWindow.saveCurrentSheet();
					update();
					loadItems();
				});
				
				menu.getItems().addAll(delete);
				
				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setText("");
						cell.setContextMenu(null);
					} else {
						cell.setText(cell.getItem().getName());
						if (cell.getItem().getType() != null) {
							cell.setContextMenu(menu);
						}
					}
				});
				return cell;
			}
		});
		tree_profs.setRoot(root);
		tree_profs.getSelectionModel().select(root);
		profs.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if (done) {
					tree_profs.setRoot(root);
					tree_profs.getSelectionModel().select(root);
					done = false;
				}
			}
		});
	}

	protected void update() {
		CharSheet sheet = MainWindow.current_sheet.get();
		Serealizator.serealize(sheet, "res/sheets/" + sheet.getToken() + ".chr");
		MainWindow.current_sheet.set(null);
		MainWindow.current_sheet.set(sheet);
	}

	private void loadItems() {
		profs.setAll(MainWindow.current_sheet.get().profs);
		createTabelModel();
		sortProfs();
		Proficiency nulifier = new Proficiency(null, "nulifier");
		profs.add(nulifier);
		done = true;
		profs.remove(nulifier);
	}

	private void sortProfs() {
		for(Proficiency prof: profs){
			TreeItem<Proficiency> node = new TreeItem<Proficiency>(prof);
			if (prof.getType() != null){
				if(prof.getType() == Proficiency.Type.LANGUAGE){
					language.getChildren().add(node);
				} else if(prof.getType() == Proficiency.Type.WEAPON){
					weapon.getChildren().add(node);
				} else if(prof.getType() == Proficiency.Type.ARMOR){
					armor.getChildren().add(node);
				} else if(prof.getType() == Proficiency.Type.TOOL){
					tool.getChildren().add(node);
				}
			}
		}
	}

	private void createTabelModel() {
		root = new TreeItem<Proficiency>(new Proficiency(null, "Предметы"));
		language = new TreeItem<Proficiency>(new Proficiency(null, "Языки"));
		weapon = new TreeItem<Proficiency>(new Proficiency(null, "Оружие"));
		armor = new TreeItem<Proficiency>(new Proficiency(null, "Броня"));
		tool = new TreeItem<Proficiency>(new Proficiency(null, "Инструменты"));
		root.setExpanded(true);
		language.setExpanded(true);
		weapon.setExpanded(true);
		armor.setExpanded(true);
		tool.setExpanded(true);
		root.getChildren().add(language);
		root.getChildren().add(weapon);
		root.getChildren().add(armor);
		root.getChildren().add(tool);
	}

	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}

}
