package by.zti.dnd.view;

import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Trait;
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

public class Traits {
	private BorderPane pane;
	private static boolean done = false;
	private static TreeItem<Trait> root;
	private static TreeItem<Trait> gameClass;
	private static TreeItem<Trait> race;
	private static TreeItem<Trait> feat;

	public static ObservableList<Trait> traits;

	@FXML
	private TreeView<Trait> tree_traits;

	public void init() {
		try {
			traits = FXCollections.observableArrayList();
			setPane((BorderPane) FXMLLoader.load(Traits.class.getResource("TraitsPanel.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		loadItems();
		tree_traits.setCellFactory(new Callback<TreeView<Trait>, TreeCell<Trait>>() {
			@Override
			public TreeCell<Trait> call(TreeView<Trait> param) {
				TreeCell<Trait> cell = new TreeCell<Trait>();

				ContextMenu menu = new ContextMenu();

				MenuItem delete = new MenuItem("Delete");

				delete.setOnAction(e -> {
					MainWindow.current_sheet.get().traits.remove(cell.getItem());
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
		tree_traits.setRoot(root);
		tree_traits.getSelectionModel().select(root);
		traits.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if (done) {
					tree_traits.setRoot(root);
					tree_traits.getSelectionModel().select(root);
					done = false;
				}
			}
		});
	}

	private void sortProfs() {
		for (Trait trait : traits) {
			TreeItem<Trait> node = new TreeItem<Trait>(trait);
			if (trait.getType() != null) {
				if (trait.getType() == Trait.Type.CLASS) {
					gameClass.getChildren().add(node);
				} else if (trait.getType() == Trait.Type.RACE) {
					race.getChildren().add(node);
				} else if (trait.getType() == Trait.Type.FEAT) {
					feat.getChildren().add(node);
				}
			}
		}
	}

	private void createTabelModel() {
		root = new TreeItem<Trait>(new Trait(null, "Предметы"));
		gameClass = new TreeItem<Trait>(new Trait(null, "Класс"));
		race = new TreeItem<Trait>(new Trait(null, "Расса"));
		feat = new TreeItem<Trait>(new Trait(null, "Фиты"));
		root.setExpanded(true);
		gameClass.setExpanded(true);
		race.setExpanded(true);
		feat.setExpanded(true);
		root.getChildren().add(gameClass);
		root.getChildren().add(race);
		root.getChildren().add(feat);
	}

	private void loadItems() {
		traits.setAll(MainWindow.current_sheet.get().traits);
		createTabelModel();
		sortProfs();
		Trait nulifier = new Trait(null, "nulifier");
		traits.add(nulifier);
		done = true;
		traits.remove(nulifier);
	}

	protected void update() {
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
