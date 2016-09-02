package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.utils.Serealizator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static by.zti.dnd.model.CharSheet.Param.*;

public class NewSheetWindow {
	private Stage stage;
	private AnchorPane root;

	@FXML
	private TextField token_tf;
	@FXML
	private TextField name_tf;
	@FXML
	private TextField level_tf;
	@FXML
	private TextField str_tf;
	@FXML
	private TextField dex_tf;
	@FXML
	private TextField con_tf;
	@FXML
	private TextField intel_tf;
	@FXML
	private TextField wis_tf;
	@FXML
	private TextField charm_tf;
	@FXML
	private TextField speed_tf;
	@FXML
	private TextField hetDice_tf;

	public void init() {
		stage = new Stage();
		stage.setTitle("New CharSheet");
		try {
			root = (AnchorPane) FXMLLoader.load(NewSheetWindow.class.getResource("NewSheet.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void commit() {
		String token = token_tf.getText();
		File file = new File("res/sheets/" + token + ".chr");
		if (file.exists()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText(null);
			alert.setContentText("Charsheet with '" + token + "' already exists. Override?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				createNewCharSheet(file);
			}
		} else {
			createNewCharSheet(file);
		}
	}

	private void createNewCharSheet(File file) {
		try {
			CharSheet sheet = new CharSheet(token_tf.getText());
			sheet.setName(name_tf.getText());
			sheet.setParam(LEVEL, Integer.parseInt(level_tf.getText()));
			sheet.stats.setAllStats(Integer.parseInt(str_tf.getText()), Integer.parseInt(dex_tf.getText()),
					Integer.parseInt(con_tf.getText()), Integer.parseInt(intel_tf.getText()),
					Integer.parseInt(wis_tf.getText()), Integer.parseInt(charm_tf.getText()));
			sheet.setParam(SPEED, Integer.parseInt(speed_tf.getText()));
			sheet.setParam(HIT_DICE, Integer.parseInt(hetDice_tf.getText()));
			Serealizator.serealize(sheet, file);
			stage = (Stage) token_tf.getScene().getWindow();
			SheetsListView.loadSheets();
			stage.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText(null);
			alert.setContentText("Incorrect data \n" + e.getMessage());
			alert.show();
			e.printStackTrace();
		}
	}

	@FXML
	private void cancel() {
		stage = (Stage) token_tf.getScene().getWindow();
		stage.close();
	}

}
