package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.Trait;
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

public class NewTraitWindow {
	private AnchorPane root;
	private Stage stage;
	public static Trait trait;

	@FXML
	private TextField name;
	@FXML
	private ComboBox<Trait.Type> types;
	@FXML
	private TextArea desc;

	public void init() {
		stage = new Stage();
		stage.setTitle("New Trait");
		try {
			FXMLLoader loader = new FXMLLoader(NewTraitWindow.class.getResource("NewTrait.fxml"));
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {
		name.setText(trait.getName());
		desc.setText(trait.getDescription());
		initTypes(trait);
	}

	@FXML
	private void confirm() {
		File prev = new File("res/traits/" + trait.getName() + ".trt");
		if (prev.exists()) {
			prev.delete();
		}
		trait.setName(name.getText());
		trait.setDescription(desc.getText());
		trait.setType(types.getValue());
		Serealizator.serealize(trait, "res/traits/" + trait.getName() + ".trt");
		TraitsListView.loadTraits();
		Stage stage = (Stage) types.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) name.getScene().getWindow();
		stage.close();
	}

	private void initTypes(Trait trait) {
		ObservableList<Trait.Type> list = FXCollections.observableArrayList();
		list.addAll(Trait.Type.CLASS, Trait.Type.FEAT, Trait.Type.RACE);
		types.setItems(list);
		types.getSelectionModel().select(trait.getType());
	}

	public AnchorPane getPane() {
		return root;
	}

	public void setPane(AnchorPane root) {
		this.root = root;
	}
}
