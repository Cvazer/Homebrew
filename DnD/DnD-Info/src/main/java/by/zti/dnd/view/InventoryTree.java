package by.zti.dnd.view;

import java.io.IOException;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Item;
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

public class InventoryTree {
	private BorderPane pane;
	private static boolean done = false;
	private static TreeItem<Item> root;
	private static TreeItem<Item> concumable;
	private static TreeItem<Item> weapon_mele;
	private static TreeItem<Item> armor;
	private static TreeItem<Item> ammo;
	private static TreeItem<Item> junk;
	private static TreeItem<Item> valuable;
	private static TreeItem<Item> gem;
	private static TreeItem<Item> ring;
	private static TreeItem<Item> weapon_range;
	private static TreeItem<Item> food;
	
	public static ObservableList<Item> items;
	
	@FXML
	private TreeView<Item> tree_items;
	
	public void init() {
		try {
			items = FXCollections.observableArrayList();
			setPane((BorderPane) FXMLLoader.load(InventoryTree.class.getResource("InventoryPanel.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		loadItems();
		tree_items.setCellFactory(new Callback<TreeView<Item>, TreeCell<Item>>() {
			@Override
			public TreeCell<Item> call(TreeView<Item> param) {
				TreeCell<Item> cell = new TreeCell<Item>();

				ContextMenu menu = new ContextMenu();

				MenuItem delete = new MenuItem("Delete");

				delete.setOnAction(e -> {
					MainWindow.current_sheet.get().inv.getItems().remove(cell.getItem());
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
						cell.setText(cell.getItem().toString());
						if(cell.getItem().getType()!=null){
							cell.setContextMenu(menu);
						}
					}
				});
				return cell;
			}
		});
		tree_items.setRoot(root);
		tree_items.getSelectionModel().select(root);
		items.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if (done) {
					tree_items.setRoot(root);
					tree_items.getSelectionModel().select(root);
					done = false;
				}
			}
		});
	}

	private void loadItems() {
		items.setAll(MainWindow.current_sheet.get().inv.getItems());
		createTabelModel();
		sortItems();
		Item nulifier = new Item(Item.Type.AMMO, "nulifier");
		items.add(nulifier);
		done = true;
		items.remove(nulifier);
	}

	private void sortItems() {
		for (Item item : items) {
			if (item.getType() != null) {
				TreeItem<Item> node = new TreeItem<Item>(item);
				if (item.getType() == Item.Type.CONSUMABLE) {
					concumable.getChildren().add(node);
				} else if (item.getType() == Item.Type.WEAPON_MELE) {
					weapon_mele.getChildren().add(node);
				} else if (item.getType() == Item.Type.ARMOR) {
					armor.getChildren().add(node);
				} else if (item.getType() == Item.Type.AMMO) {
					ammo.getChildren().add(node);
				} else if (item.getType() == Item.Type.JUNK) {
					junk.getChildren().add(node);
				} else if (item.getType() == Item.Type.VALUABLE) {
					valuable.getChildren().add(node);
				} else if (item.getType() == Item.Type.GEM) {
					gem.getChildren().add(node);
				} else if (item.getType() == Item.Type.RING) {
					ring.getChildren().add(node);
				} else if (item.getType() == Item.Type.WEAPON_RANGE) {
					weapon_range.getChildren().add(node);
				} else if (item.getType() == Item.Type.FOOD) {
					food.getChildren().add(node);
				}
			}
		}
	}

	private void createTabelModel() {
		root = new TreeItem<Item>(new Item(null, "Предметы"));
		concumable = new TreeItem<Item>(new Item(null, "Расходники"));
		weapon_mele = new TreeItem<Item>(new Item(null, "Оружие ближнего боя"));
		weapon_range = new TreeItem<Item>(new Item(null, "Оружие дальнего боя"));
		armor = new TreeItem<Item>(new Item(null, "Доспехи"));
		junk = new TreeItem<Item>(new Item(null, "Хлам"));
		valuable = new TreeItem<Item>(new Item(null, "Ценности"));
		gem = new TreeItem<Item>(new Item(null, "Драгоценные камни"));
		ring = new TreeItem<Item>(new Item(null, "Бижа"));
		food = new TreeItem<Item>(new Item(null, "Припасы"));
		ammo = new TreeItem<Item>(new Item(null, "Боезапас"));
		root.setExpanded(true);
		concumable.setExpanded(true);
		weapon_mele.setExpanded(true);
		weapon_range.setExpanded(true);
		armor.setExpanded(true);
		junk.setExpanded(true);
		valuable.setExpanded(true);
		gem.setExpanded(true);
		ring.setExpanded(true);
		food.setExpanded(true);
		ammo.setExpanded(true);
		root.getChildren().add(concumable);
		root.getChildren().add(weapon_mele);
		root.getChildren().add(weapon_range);
		root.getChildren().add(ammo);
		root.getChildren().add(armor);
		root.getChildren().add(junk);
		root.getChildren().add(valuable);
		root.getChildren().add(gem);
		root.getChildren().add(ring);
		root.getChildren().add(food);
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
