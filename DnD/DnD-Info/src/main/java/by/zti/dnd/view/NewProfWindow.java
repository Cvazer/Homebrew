package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.Proficiency;
import by.zti.dnd.utils.Serealizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewProfWindow {
	private AnchorPane pane;
	public static Proficiency prof;
	
	@FXML
	private TextField name;
	@FXML
	private ComboBox<Proficiency.Type> type;
	@FXML
	private TextArea desc;

	public void init() {
		Stage stage = new Stage();
		stage.setTitle("New Proficiency");
		try {
			FXMLLoader loader = new FXMLLoader(NewSkillWindow.class.getResource("NewProf.fxml"));
			pane = (AnchorPane) loader.load();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void initialize() {
		name.setText(prof.getName());
		ObservableList<Proficiency.Type> types = FXCollections.observableArrayList();
		types.addAll(Proficiency.Type.ARMOR, Proficiency.Type.LANGUAGE, Proficiency.Type.TOOL, Proficiency.Type.WEAPON);
		type.setItems(types);
		type.getSelectionModel().select(prof.getType());
		desc.setText(prof.getDescription());
	}
	
	@FXML
	private void confirm() {
		File prev = new File("res/profs/" + prof.getName() + ".prf");
		if (prev.exists()) {
			prev.delete();
		}
		prof.setName(name.getText());
		prof.setType(type.getValue());
		prof.setDescription(desc.getText());
		Serealizator.serealize(prof, "res/profs/" + prof.getName() + ".prf");
		ProfsListView.loadProfs();
		prof = null;
		cancel();
	}
	
	@FXML
	private void cancel() {
		Stage stage = (Stage) name.getScene().getWindow();
		stage.close();
	}
}
