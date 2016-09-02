package by.zti.dnd.view;

import java.io.File;
import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Item;
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

public class NewItemWindow {
	public static Item item;
	private AnchorPane pane;

	@FXML
	private TextField name;
	@FXML
	private TextField weight;
	@FXML
	private TextField count;
	@FXML
	private TextField cp;
	@FXML
	private TextField sp;
	@FXML
	private TextField gp;
	@FXML
	private TextArea desc;
	@FXML
	private ComboBox<Item.Type> type;

	public void init() {
		Stage stage = new Stage();
		stage.setTitle("New Item");
		try {
			FXMLLoader loader = new FXMLLoader(NewTraitWindow.class.getResource("NewItem.fxml"));
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
	private void confirm() {
		File prev = new File("res/items/" + item.getName() + ".itm");
		if (prev.exists()) {
			prev.delete();
		}
		item.setName(name.getText());
		item.setItemWeight(Integer.parseInt(weight.getText()));
		item.setCount(Integer.parseInt(count.getText()));
		item.setPrice(Integer.parseInt(cp.getText()), Integer.parseInt(sp.getText()), Integer.parseInt(gp.getText()));
		item.setDescription(desc.getText());
		item.setType(type.getValue());
		Serealizator.serealize(item, "res/items/" + item.getName() + ".itm");
		ItemsTreeView.loadItems();
		item = null;
		cancel();
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) name.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void initialize() {
		name.setText(item.getName());
		weight.setText(item.getItemWeight() + "");
		count.setText(item.getCount() + "");
		cp.setText(item.getCurrency(CharSheet.Money.COPPER) + "");
		sp.setText(item.getCurrency(CharSheet.Money.SILVER) + "");
		gp.setText(item.getCurrency(CharSheet.Money.GOLD) + "");
		desc.setText(item.getDescription() + "");

		ObservableList<Item.Type> types = FXCollections.observableArrayList();
		types.addAll(Item.Type.AMMO, Item.Type.CONSUMABLE, Item.Type.WEAPON_MELE, Item.Type.ARMOR, Item.Type.JUNK,
				Item.Type.VALUABLE, Item.Type.GEM, Item.Type.RING, Item.Type.WEAPON_RANGE, Item.Type.FOOD);
		type.setItems(types);
		type.getSelectionModel().select(item.getType());
	}

}
