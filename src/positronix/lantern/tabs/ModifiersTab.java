package positronix.lantern.tabs;

import positronix.lantern.*;

import java.util.List;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

@SuppressWarnings("unchecked")
public class ModifiersTab {
	static Scene scene = Main.scene;
	
	public static TableView conditionTable = (TableView) scene.lookup("#conditionTable"); //TODO: Add Condition type parameter
	public static ListView conditionList = (ListView) scene.lookup("#conditionList"); //TODO: Add Condition type parameter
	public static Button addCondition = (Button) scene.lookup("#addCondition");
	public static Button removeCondition = (Button) scene.lookup("#removeCondition");
	public static Button conditionDetails = (Button) scene.lookup("#conditionDetails");
	
	public static TableView<Adjustment> modTable = (TableView<Adjustment>) scene.lookup("#modTable");
	public static final ObservableList<Adjustment> modTableData = FXCollections.observableArrayList();
	public static Button modChoose = (Button) scene.lookup("#modChoose");
	public static TextField modChosen = (TextField) scene.lookup("#modChosen");
	public static TextField modAmt = (TextField) scene.lookup("#modAmt");
	public static TextField modReason = (TextField) scene.lookup("#modReason");
	public static Button addMod = (Button) scene.lookup("#addMod");
	public static Button removeMod = (Button) scene.lookup("#removeMod");
	public static Object target;
	
	public ModifiersTab() {
		
		
		TableColumn<Adjustment, String> adjustmentTarget = (TableColumn<Adjustment, String>) modTable.getColumns().get(0);
		TableColumn<Adjustment, Integer> adjustmentAmount = (TableColumn<Adjustment, Integer>) modTable.getColumns().get(1);
		TableColumn<Adjustment, String> adjustmentReason = (TableColumn<Adjustment, String>) modTable.getColumns().get(2);
		TableColumn<Adjustment, Boolean> adjustmentEnabled = (TableColumn<Adjustment, Boolean>) modTable.getColumns().get(3);
		
		adjustmentTarget.setCellValueFactory(new PropertyValueFactory<Adjustment, String>("target"));
		adjustmentAmount.setCellValueFactory(new PropertyValueFactory<Adjustment, Integer>("amount"));
		adjustmentReason.setCellValueFactory(new PropertyValueFactory<Adjustment, String>("reason"));
		adjustmentEnabled.setCellValueFactory(new PropertyValueFactory<Adjustment, Boolean>("enabled"));
		
		modTable.setItems(modTableData);
		
		modChoose.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Dialog.modSelectorStage.show();
				Dialog.initializeModSelector();
			}
		});
		
		modAmt.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					modAmt.setText(Calculations.filterInt(modAmt.getText(), 0));
				}
			}
		});
		
		Tooltip adjWarning = new Tooltip();
		adjWarning.setAutoHide(true);
		
		addMod.setDisable(true);
		addMod.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (modChosen.getText().isEmpty()) {
					adjWarning.setText("Please choose a field.");
					adjWarning.show(scene.getWindow());
				} else if (modAmt.getText().isEmpty()) {
					adjWarning.setText("Please enter a valid amount.");
					adjWarning.show(scene.getWindow());
				} else if (modReason.getText().isEmpty()) {
					adjWarning.setText("Please enter a reason.");
					adjWarning.show(scene.getWindow());
				} else {
					int amount = Integer.parseInt(modAmt.getText());
					
					if (target instanceof Ability) {
						Ability ability = (Ability) target;
						modTableData.add(new Adjustment(modChosen.getText(), ability, amount, modReason.getText(), true));
					} else if (target instanceof TextField) {
						TextField field = (TextField) target;
						modTableData.add(new Adjustment(modChosen.getText(), field, amount, modReason.getText(), true));
					} else if (target instanceof ShortWeapon) {
						ShortWeapon weapon = (ShortWeapon) target;
						modTableData.add(new Adjustment(modChosen.getText(), weapon, amount, modReason.getText(), true));
					} else if (target instanceof ObservableList) {
						ObservableList<ShortWeapon> weapons = (ObservableList<ShortWeapon>) target;
						modTableData.add(new Adjustment(modChosen.getText(), weapons, amount, modReason.getText(), true));
					}
					
					target = null;
					modChosen.setText("");
					modAmt.setText("");
					modReason.setText("");
				}
			}
		});
		
		removeMod.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!modTable.getSelectionModel().isEmpty()) {
					Adjustment selected = modTable.getSelectionModel().getSelectedItem();
					selected.remove();
					modTableData.remove(selected);
				}
			}
		});
		
		modTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent click) {
        		if (!modTable.getSelectionModel().isEmpty()) {
	        		if (click.getButton().equals(MouseButton.PRIMARY)
	        				&& click.getClickCount() == 2) {
	    				Adjustment selected = modTable.getSelectionModel().getSelectedItem();
	    				selected.toggleEnabled();
	        		}
        		}
        	}
        });
	}
}
