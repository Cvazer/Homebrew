package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Trait;
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

public class TraitsListView {
	private BorderPane pane;

	private static ObservableList<Trait> traits;

	@FXML
	private ListView<Trait> list_traits;

	public void init() {
		try {
			traits = FXCollections.observableArrayList();
			pane = (BorderPane) FXMLLoader.load(TraitsListView.class.getResource("TraitsListPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		loadTraits();
		list_traits.setItems(traits);
		list_traits.setCellFactory(new Callback<ListView<Trait>, ListCell<Trait>>() {
			@Override
			public ListCell<Trait> call(ListView<Trait> param) {
				ListCell<Trait> cell = new ListCell<Trait>();

				ContextMenu menu = new ContextMenu();

				MenuItem delete = new MenuItem("Delete");
				MenuItem edit = new MenuItem("Edit");

				delete.setOnAction(e -> {
					new File("res/traits/" + cell.getItem().getName() + ".trt").delete();
					loadTraits();
				});
				edit.setOnAction(e -> {
					NewTraitWindow.trait = cell.getItem();
					NewTraitWindow window = new NewTraitWindow();
					window.init();
				});

				menu.getItems().addAll(edit, delete);

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
		list_traits.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2 && click.getButton() == MouseButton.PRIMARY
					&& MainWindow.current_sheet.getValue() != null) {
				MainWindow.current_sheet.get().traits.add(list_traits.getSelectionModel().getSelectedItem());
				update();
			}
		});
	}

	public static void loadTraits() {
		Trait nulifier = new Trait(Trait.Type.CLASS, "null");
		traits.add(nulifier);
		traits.remove(nulifier);
		traits.clear();
		File folder = new File("res/traits");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.getName().contains("trt")) {
				traits.add((Trait) Serealizator.deSerealize(file));
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
}
