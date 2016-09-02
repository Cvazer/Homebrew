package by.zti.dnd.view;

import java.io.IOException;
import java.util.Optional;

import by.zti.dnd.model.CharSheet;
import by.zti.dnd.model.Skill;
import by.zti.dnd.model.Stats;
import by.zti.dnd.utils.SaveThrow;
import by.zti.dnd.utils.Serealizator;
import by.zti.dnd.utils.Stat;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class Overview {
	private GridPane pane;

	private ObservableList<Skill> skills_list;

	@FXML
	private TableView<SaveThrow> table;
	@FXML
	private TableView<Stat> statsTable;
	@FXML
	private ListView<Skill> skills;
	@FXML
	private TableColumn<SaveThrow, String> save_name;
	@FXML
	private TableColumn<SaveThrow, Integer> save_mod;
	@FXML
	private TableColumn<SaveThrow, String> save_prof;
	@FXML
	private TableColumn<Stat, String> stat_name;
	@FXML
	private TableColumn<Stat, Integer> stat_mod;
	@FXML
	private TableColumn<Stat, Integer> stat_value;

	@FXML
	private ProgressBar hp_bar;
	@FXML
	private CheckBox inspiration;
	@FXML
	private Label class_and_level;
	@FXML
	private Label race;
	@FXML
	private Label background;
	@FXML
	private Label expirience;
	@FXML
	private Label ac;
	@FXML
	private Label speed;
	@FXML
	private Label name;
	@FXML
	private Label hitDice;
	@FXML
	private Label hitDiceTotal;
	@FXML
	private Label hitDiceCurrent;
	@FXML
	private Label hpMax;
	@FXML
	private Label hpCurrent;
	@FXML
	private Label hpRolled;
	@FXML
	private Label copper;
	@FXML
	private Label silver;
	@FXML
	private Label gold;
	@FXML
	private Label weight;
	@FXML
	private Label prof_score;

	public void init() {
		try {
			setPane((GridPane) FXMLLoader.load(Overview.class.getResource("OverviewPanel.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {
		if (MainWindow.current_sheet.get() != null) {
			name.setText(MainWindow.current_sheet.get().getName());
			initHpBar();
			initLabels();
			initSavesTable();
			initStatsTable();
			initInsperation();
			initSkills();
		}
	}

	private void initSkills() {
		skills_list = FXCollections.observableArrayList(MainWindow.current_sheet.get().skills);
		skills.setItems(skills_list);
		skills.setCellFactory(new Callback<ListView<Skill>, ListCell<Skill>>() {
			@Override
			public ListCell<Skill> call(ListView<Skill> param) {
				ListCell<Skill> cell = new ListCell<Skill>();

				ContextMenu menu = new ContextMenu();

				MenuItem delete = new MenuItem("Delete");

				delete.setOnAction(e -> {
					MainWindow.current_sheet.get().skills.remove(cell.getItem());
					MainWindow.saveCurrentSheet();
					update();
				});

				menu.getItems().addAll(delete);

				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setText("");
						cell.setContextMenu(null);
					} else {
						if (cell.getItem().isProficient()) {
							int mod = cell.getItem().getMod(MainWindow.current_sheet.get());
							cell.setText(cell.getItem().getName() + " (" + mod + ")");
						} else {
							cell.setText(cell.getItem().getName() + " ("
									+ cell.getItem().getMod(MainWindow.current_sheet.get()) + ")");
						}
						cell.setContextMenu(menu);
					}
				});
				cell.setOnMouseClicked(click -> {
					if (click.getClickCount() == 2 && click.getButton() == MouseButton.PRIMARY
							&& MainWindow.current_sheet.getValue() != null) {
						cell.getItem().setProficient(!cell.getItem().isProficient());
						update();
					}
				});
				return cell;
			}

		});
	}

	private void initInsperation() {
		inspiration.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				MainWindow.current_sheet.get().setInspiration(observable.getValue());
				update();
			}
		});
	}

	private void initStatsTable() {
		ObservableList<Stat> stats = FXCollections.observableArrayList();
		stats.add(new Stat("Strength", MainWindow.current_sheet.get().stats.getStat(Stats.Stat.STR),
				MainWindow.current_sheet.get().stats.getMod(Stats.Stat.STR), Stats.Stat.STR));
		stats.add(new Stat("Dexterity", MainWindow.current_sheet.get().stats.getStat(Stats.Stat.DEX),
				MainWindow.current_sheet.get().stats.getMod(Stats.Stat.DEX), Stats.Stat.DEX));
		stats.add(new Stat("Constitution", MainWindow.current_sheet.get().stats.getStat(Stats.Stat.CON),
				MainWindow.current_sheet.get().stats.getMod(Stats.Stat.CON), Stats.Stat.CON));
		stats.add(new Stat("Inteligence", MainWindow.current_sheet.get().stats.getStat(Stats.Stat.INTEL),
				MainWindow.current_sheet.get().stats.getMod(Stats.Stat.INTEL), Stats.Stat.INTEL));
		stats.add(new Stat("Wisdom", MainWindow.current_sheet.get().stats.getStat(Stats.Stat.WIS),
				MainWindow.current_sheet.get().stats.getMod(Stats.Stat.WIS), Stats.Stat.WIS));
		stats.add(new Stat("Charisma", MainWindow.current_sheet.get().stats.getStat(Stats.Stat.CHARM),
				MainWindow.current_sheet.get().stats.getMod(Stats.Stat.CHARM), Stats.Stat.CHARM));
		statsTable.setItems(stats);

		stat_name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stat, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Stat, String> param) {
						return new SimpleStringProperty(param.getValue().getName());
					}
				});

		stat_mod.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stat, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Stat, Integer> param) {
						return new SimpleIntegerProperty(param.getValue().getMod()).asObject();
					}
				});

		stat_value.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stat, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Stat, Integer> param) {
						return new SimpleIntegerProperty(param.getValue().getValue()).asObject();
					}
				});
		statsTable.setRowFactory(new Callback<TableView<Stat>, TableRow<Stat>>() {
			@Override
			public TableRow<Stat> call(TableView<Stat> param) {
				TableRow<Stat> row = new TableRow<Stat>();
				row.setOnMouseClicked(e -> {
					if (e.getClickCount() == 2 && (!row.isEmpty())) {
						showInputDialog("Change stat", "Enter new stat value", "" + row.getItem().getValue())
								.ifPresent(value -> {
									MainWindow.current_sheet.get().stats.setStat(row.getItem().getStat(),
											Integer.parseInt(value));
									update();
								});
					}
				});
				return row;
			}
		});
	}

	private void initHpBar() {
		hpCurrent.textProperty().addListener((obs, oldV, newV) -> {
			double hp = Double.parseDouble(obs.getValue());
			double progress = ((hp * 100) / (double) MainWindow.current_sheet.get().getParam(CharSheet.Param.HP_MAX))
					/ 100.0;
			hp_bar.setProgress(progress);
		});
	}

	private void initSavesTable() {
		ObservableList<SaveThrow> saves = FXCollections.observableArrayList();
		saves.add(new SaveThrow("Strength", MainWindow.current_sheet.get().stats.getMod(Stats.Stat.STR),
				MainWindow.current_sheet.get().getSave(Stats.Stat.STR), Stats.Stat.STR));
		saves.add(new SaveThrow("Dexterity", MainWindow.current_sheet.get().stats.getMod(Stats.Stat.DEX),
				MainWindow.current_sheet.get().getSave(Stats.Stat.DEX), Stats.Stat.DEX));
		saves.add(new SaveThrow("Constitution", MainWindow.current_sheet.get().stats.getMod(Stats.Stat.CON),
				MainWindow.current_sheet.get().getSave(Stats.Stat.CON), Stats.Stat.CON));
		saves.add(new SaveThrow("Inteligence", MainWindow.current_sheet.get().stats.getMod(Stats.Stat.INTEL),
				MainWindow.current_sheet.get().getSave(Stats.Stat.INTEL), Stats.Stat.INTEL));
		saves.add(new SaveThrow("Wisdom", MainWindow.current_sheet.get().stats.getMod(Stats.Stat.WIS),
				MainWindow.current_sheet.get().getSave(Stats.Stat.WIS), Stats.Stat.WIS));
		saves.add(new SaveThrow("Charisma", MainWindow.current_sheet.get().stats.getMod(Stats.Stat.CHARM),
				MainWindow.current_sheet.get().getSave(Stats.Stat.CHARM), Stats.Stat.CHARM));
		table.setItems(saves);
		save_name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SaveThrow, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<SaveThrow, String> param) {
						return new SimpleStringProperty(param.getValue().getName());
					}
				});
		save_mod.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SaveThrow, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<SaveThrow, Integer> param) {
						int mod = param.getValue().getMod();
						if (param.getValue().isProf()) {
							mod += MainWindow.current_sheet.get().getParam(CharSheet.Param.PROFICIENCY);
						}
						return new SimpleIntegerProperty(mod).asObject();
					}
				});
		save_prof.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SaveThrow, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<SaveThrow, String> param) {
						if (param.getValue().isProf()) {
							return new SimpleStringProperty("Yes");
						} else {
							return new SimpleStringProperty("No");
						}
					}
				});
		table.setRowFactory(new Callback<TableView<SaveThrow>, TableRow<SaveThrow>>() {
			@Override
			public TableRow<SaveThrow> call(TableView<SaveThrow> param) {
				TableRow<SaveThrow> row = new TableRow<SaveThrow>();
				row.setOnMouseClicked(e -> {
					if (e.getClickCount() == 2 && (!row.isEmpty())) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Switch proficiency");
						alert.setHeaderText(null);
						alert.setContentText("Switch proficiency for that save throw?");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							SaveThrow save = row.getItem();
							MainWindow.current_sheet.get().saves.put(save.getStat(), !save.isProf());
							update();
						}
					}
				});
				return row;
			}
		});
	}

	private void initLabels() {
		inspiration.setSelected(MainWindow.current_sheet.get().isInspiration());
		class_and_level.setText(MainWindow.current_sheet.get().getGameClass() + " "
				+ MainWindow.current_sheet.get().getParam(CharSheet.Param.LEVEL));
		race.setText(MainWindow.current_sheet.get().getRace());
		background.setText(MainWindow.current_sheet.get().getBackground());
		expirience.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.XP));
		ac.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.AC));
		speed.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.SPEED));
		hitDice.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.HIT_DICE));
		hitDiceTotal.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.HIT_DICE_TOTAL));
		hitDiceCurrent.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.HIT_DICE_CURRENT));
		hpMax.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.HP_MAX));
		hpCurrent.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.HP_CURRENT));
		hpRolled.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.HP_ROLLED));
		gold.setText("" + MainWindow.current_sheet.get().walet.get(CharSheet.Money.GOLD));
		silver.setText("" + MainWindow.current_sheet.get().walet.get(CharSheet.Money.SILVER));
		copper.setText("" + MainWindow.current_sheet.get().walet.get(CharSheet.Money.COPPER));
		weight.setText("" + MainWindow.current_sheet.get().inv.updateWeight());
		prof_score.setText("" + MainWindow.current_sheet.get().getParam(CharSheet.Param.PROFICIENCY));

		name.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("Change name", "Enter new name", name.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setName(value);
					update();
				});
			}
		});
		ac.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New AC", "Enter new AC", ac.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.AC, Integer.parseInt(value));
					update();
				});
			}
		});
		hpCurrent.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New HP", "Enter new HP", hpCurrent.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.HP_CURRENT, Integer.parseInt(value));
					update();
				});
			}
		});
		expirience.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New XP", "Enter new XP", expirience.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.XP, Integer.parseInt(value));
					update();
				});
			}
		});
		speed.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New speed", "Enter new speed", speed.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.SPEED, Integer.parseInt(value));
					update();
				});
			}
		});
		hitDice.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New hit dice", "Enter new hit dice", hitDice.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.HIT_DICE, Integer.parseInt(value));
					update();
				});
			}
		});
		hitDiceTotal.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New total hit dice", "Enter new total hit dice", hitDiceTotal.getText())
						.ifPresent(value -> {
							MainWindow.current_sheet.get().setParam(CharSheet.Param.HIT_DICE_TOTAL,
									Integer.parseInt(value));
							update();
						});
			}
		});
		hitDiceCurrent.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New current hit dice", "Enter new current hit dice", hitDiceCurrent.getText())
						.ifPresent(value -> {
							MainWindow.current_sheet.get().setParam(CharSheet.Param.HIT_DICE_CURRENT,
									Integer.parseInt(value));
							update();
						});
			}
		});
		hpMax.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New max hp", "Enter new max hp", hpMax.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.HP_MAX, Integer.parseInt(value));
					update();
				});
			}
		});
		hpRolled.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New rolled hp", "Enter new rolled hp", hpMax.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.HP_ROLLED, Integer.parseInt(value));
					update();
				});
			}
		});
		gold.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New gold value", "Enter new gold value", gold.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().walet.put(CharSheet.Money.GOLD, Integer.parseInt(value));
					update();
				});
			}
		});
		silver.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New silver value", "Enter new silver value", silver.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().walet.put(CharSheet.Money.SILVER, Integer.parseInt(value));
					update();
				});
			}
		});
		copper.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New copper value", "Enter new copper value", copper.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().walet.put(CharSheet.Money.COPPER, Integer.parseInt(value));
					update();
				});
			}
		});

		race.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New race", "Enter new race value", race.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setRace(value);
					update();
				});
			}
		});
		weight.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				update();
			}
		});
		background.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New background", "Enter new background value", background.getText())
						.ifPresent(value -> {
							MainWindow.current_sheet.get().setBackground(value);
							update();
						});
			}
		});
		prof_score.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {
				showInputDialog("New score", "Enter new score value", prof_score.getText()).ifPresent(value -> {
					MainWindow.current_sheet.get().setParam(CharSheet.Param.PROFICIENCY, Integer.parseInt(value));
					update();
				});
			}
		});
		class_and_level.setOnMouseClicked(click -> {
			if (click.getClickCount() == 2) {

				showInputDialog("New calss", "Enter new calss", "" + MainWindow.current_sheet.get().getGameClass())
						.ifPresent(value -> {
							MainWindow.current_sheet.get().setGameClass(value);
							update();
						});
				class_and_level.setText(MainWindow.current_sheet.get().getGameClass() + " "
						+ MainWindow.current_sheet.get().getParam(CharSheet.Param.LEVEL));

				showInputDialog("New level value", "Enter new level value",
						"" + MainWindow.current_sheet.get().params.get(CharSheet.Param.LEVEL)).ifPresent(value -> {
							MainWindow.current_sheet.get().params.put(CharSheet.Param.LEVEL, Integer.parseInt(value));
							update();
						});
			}
		});

		MenuItem xp_add = new MenuItem("Add");
		xp_add.setOnAction(e -> {
			showInputDialog("Add EXP", "Enter EXP", "" + 0).ifPresent(value -> {
				MainWindow.current_sheet.get().params.put(CharSheet.Param.XP,
						MainWindow.current_sheet.get().params.get(CharSheet.Param.XP) + Integer.parseInt(value));
				update();
			});
		});

		MenuItem gp_substract = new MenuItem("Substract");
		gp_substract.setOnAction(e -> {
			showInputDialog("Sub gp", "Enter gp", "" + 0).ifPresent(value -> {
				MainWindow.current_sheet.get().walet.put(CharSheet.Money.GOLD,
						MainWindow.current_sheet.get().walet.get(CharSheet.Money.GOLD) - Integer.parseInt(value));
				update();
			});
		});
		
		MenuItem gp_add = new MenuItem("Add");
		gp_add.setOnAction(e -> {
			showInputDialog("Add gp", "Enter gp", "" + 0).ifPresent(value -> {
				MainWindow.current_sheet.get().walet.put(CharSheet.Money.GOLD,
						MainWindow.current_sheet.get().walet.get(CharSheet.Money.GOLD) + Integer.parseInt(value));
				update();
			});
		});
		
		MenuItem sp_substract = new MenuItem("Substract");
		sp_substract.setOnAction(e -> {
			showInputDialog("Sub sp", "Enter sp", "" + 0).ifPresent(value -> {
				MainWindow.current_sheet.get().walet.put(CharSheet.Money.SILVER,
						MainWindow.current_sheet.get().walet.get(CharSheet.Money.SILVER) - Integer.parseInt(value));
				update();
			});
		});
		
		MenuItem sp_add = new MenuItem("Add");
		sp_add.setOnAction(e -> {
			showInputDialog("Add sp", "Enter sp", "" + 0).ifPresent(value -> {
				MainWindow.current_sheet.get().walet.put(CharSheet.Money.SILVER,
						MainWindow.current_sheet.get().walet.get(CharSheet.Money.SILVER) + Integer.parseInt(value));
				update();
			});
		});
		
		MenuItem cp_substract = new MenuItem("Substract");
		cp_substract.setOnAction(e -> {
			showInputDialog("Sub cp", "Enter cp", "" + 0).ifPresent(value -> {
				MainWindow.current_sheet.get().walet.put(CharSheet.Money.COPPER,
						MainWindow.current_sheet.get().walet.get(CharSheet.Money.COPPER) - Integer.parseInt(value));
				update();
			});
		});
		
		MenuItem cp_add = new MenuItem("Add");
		cp_add.setOnAction(e -> {
			showInputDialog("Add cp", "Enter cp", "" + 0).ifPresent(value -> {
				MainWindow.current_sheet.get().walet.put(CharSheet.Money.COPPER,
						MainWindow.current_sheet.get().walet.get(CharSheet.Money.COPPER) + Integer.parseInt(value));
				update();
			});
		});

		expirience.setContextMenu(new ContextMenu(xp_add));
		gold.setContextMenu(new ContextMenu(gp_add, gp_substract));
		silver.setContextMenu(new ContextMenu(sp_add, sp_substract));
		copper.setContextMenu(new ContextMenu(cp_add, cp_substract));
	}

	private Optional<String> showInputDialog(String title, String text, String def) {
		TextInputDialog dialog = new TextInputDialog(def);
		dialog.setHeaderText(null);
		dialog.setTitle(title);
		dialog.setContentText(text);
		return dialog.showAndWait();
	}

	private void update() {
		CharSheet sheet = MainWindow.current_sheet.get();
		Serealizator.serealize(sheet, "res/sheets/" + sheet.getToken() + ".chr");
		MainWindow.current_sheet.set(null);
		MainWindow.current_sheet.set(sheet);
	}

	public GridPane getPane() {
		return pane;
	}

	public void setPane(GridPane pane) {
		this.pane = pane;
	}
}
