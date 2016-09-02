package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.Skill;
import by.zti.dnd.model.Stats;
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

public class NewSkillWindow {
	private AnchorPane pane;
	public static Skill skill;

	@FXML
	private TextField name;
	@FXML
	private ComboBox<Stats.Stat> scaling;
	@FXML
	private TextArea desc;

	public void init() {
		Stage stage = new Stage();
		stage.setTitle("New Skill");
		try {
			FXMLLoader loader = new FXMLLoader(NewSkillWindow.class.getResource("NewSkill.fxml"));
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
		name.setText(skill.getName());
		ObservableList<Stats.Stat> stats = FXCollections.observableArrayList();
		stats.addAll(Stats.Stat.STR, Stats.Stat.DEX, Stats.Stat.CON, Stats.Stat.INTEL, Stats.Stat.WIS,
				Stats.Stat.CHARM);
		scaling.setItems(stats);
		scaling.getSelectionModel().select(skill.getScaling());
		desc.setText(skill.getDescription());
	}

	@FXML
	private void confirm() {
		File prev = new File("res/skills/" + skill.getName() + ".skl");
		if (prev.exists()) {
			prev.delete();
		}
		skill.setName(name.getText());
		skill.setScaling(scaling.getValue());
		skill.setDescription(desc.getText());
		Serealizator.serealize(skill, "res/skills/" + skill.getName() + ".skl");
		SkillsListView.loadSkills();
		skill = null;
		cancel();
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) name.getScene().getWindow();
		stage.close();
	}
}
