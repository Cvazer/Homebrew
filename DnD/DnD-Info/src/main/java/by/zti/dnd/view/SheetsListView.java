package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.utils.Serealizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class SheetsListView {
	private BorderPane pane;

	private static ObservableList<CharSheet> sheets;
	
	@FXML
	private ListView<CharSheet> list_sheets;
	
	public void init() {
		try {
			sheets = FXCollections.observableArrayList();
			pane = (BorderPane) FXMLLoader.load(SheetsListView.class.getResource("SheetsListPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		list_sheets.setItems(sheets);
		list_sheets.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2 && click.getButton() == MouseButton.PRIMARY) {
				MainWindow.current_sheet.setValue(list_sheets.getSelectionModel().getSelectedItem());
			}
		});
		list_sheets.setCellFactory(new Callback<ListView<CharSheet>, ListCell<CharSheet>>() {
			@Override
			public ListCell<CharSheet> call(ListView<CharSheet> list) {

				ListCell<CharSheet> cell = new ListCell<CharSheet>();

				ContextMenu menu = new ContextMenu();
				MenuItem delete = new MenuItem("Delete");
				delete.setOnAction(e -> {
					CharSheet item = cell.getItem();
					File file = new File("res/sheets/" + item.getToken() + ".chr");
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Warning!");
					alert.setHeaderText(null);
					alert.setContentText("This action will delete this charsheet PERMENANTLY! "
							+ "are you shure you want to procede?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						file.delete();
						loadSheets();
					}
				});
				menu.getItems().addAll(delete);

				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setContextMenu(null);
						cell.setText("");
					} else {
						cell.setContextMenu(menu);
						cell.setText(cell.getItem().toString());
					}
				});
				return cell;
			}
		});
		loadSheets();
	}

	public static void loadSheets() {
		CharSheet nulifier = new CharSheet("null");
		sheets.add(nulifier);
		sheets.remove(nulifier);
		sheets.clear();
		File folder = new File("res/sheets");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.getName().contains("chr")) {
				sheets.add((CharSheet) Serealizator.deSerealize(file));
			}
		}
	}

	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}
}
