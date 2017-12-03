package positronix.lantern;

import positronix.lantern.tabs.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Ability {
	public String name;
	public int score = 0;
	public int mod = 0;
	public int bonus = 0;
	public int miscMod = 0;
	public Scene scene = Main.scene;
	public TextField abScore, abMod, abTemp, abTempMod;
	public Button abilityCheck;
	
	public Ability(String abName) {
		name = abName;
		abScore = (TextField)scene.lookup("#" + abName + "Score");
		abMod = (TextField)scene.lookup("#" + abName + "Mod");
		abTemp = (TextField)scene.lookup("#" + abName + "Temp");
		abTempMod = (TextField)scene.lookup("#" + abName + "TempMod");
		abilityCheck = (Button)scene.lookup("#" + abName + "Check");
		
		abScore.setText(Integer.toString(0));
		abScore.setEditable(false);
		abMod.setText(Integer.toString(0));
		abMod.setEditable(false);
		abTemp.setText(Integer.toString(0));
		abTempMod.setText(Integer.toString(0));
		

		abilityCheck.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.rollCount.setText("1");
            	Dialog.rollDie.setValue(20);
            	Dialog.rollMod.setText(Integer.toString(mod + miscMod));
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
                Dialog.rollButton.fire();
            }
        });
		
		abScore.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {
					abScore.setText(Calculations.filterInt(abScore.getText(), 0));
					score = Integer.parseInt(abScore.getText());
					mod = Calculations.modCalc(score);
					abMod.setText(Integer.toString(mod));	
			}
        });
		/*
		abScore.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ENTER) {
        			abScore.setText(Calculations.filterInt(abScore.getText(), 0));
					score = Integer.parseInt(abScore.getText());
					mod = Calculations.modCalc(score);
					abMod.setText(Integer.toString(mod));
        		}
        	}
        });
		*/
		abTemp.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {
					abTemp.setText(Calculations.filterInt(abTemp.getText(), 0));
					bonus = Integer.parseInt(abTemp.getText());
					miscMod = bonus / 2;
					abTempMod.setText(Integer.toString(miscMod));
				
			}
        });
		/*
		abTemp.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ENTER) {
        			abTemp.setText(Calculations.filterInt(abTemp.getText(), 0));
					bonus = Integer.parseInt(abTemp.getText());
					miscMod = bonus / 2;
					abTempMod.setText(Integer.toString(miscMod));
        		}
        	}
        });
        */
	}

	public void updateMods(){
		mod = Calculations.modCalc(score);
		abMod.setText(Integer.toString(mod));
	}
	
	public void adjustScore(int adjustment) {
		score += adjustment;
		abScore.setText(Integer.toString(score));
	}
	
	public void loadValues() {
		score = Integer.parseInt(abScore.getText());
		mod = Integer.parseInt(abMod.getText());
		bonus = Integer.parseInt(abTemp.getText());
		miscMod = Integer.parseInt(abTempMod.getText());
	}
	
	public String toString() {
		switch (name) {
			case "str":
				return "Strength";
			case "dex":
				return "Dexterity";
			case "con":
				return "Constitution";
			case "int":
				return "Intelligence";
			case "wis":
				return "Wisdom";
			case "cha":
				return "Charisma";
			default:
				return "";
		}
	}
}
