package positronix.lantern;

import positronix.lantern.tabs.*;

import java.util.ArrayList;
import java.util.List;

import positronix.lantern.tabs.OverviewTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

public class Skill {
	public int abMod, ranks, miscMod, total, skillMod;
	
	Scene scene = Main.scene;
	
	//public static boolean isClassSkill;
	public TextField skillTotal, skillAbility, skillRanks, skillMisc;
	public CheckBox CS, skillShow; 
	public boolean csChecked, showChecked;
	public Button skillCheck;
	public String skillName, overviewName;
	public ShortSkill skillInfo;
	
	//Tooltip tooltip = new Tooltip("Class skills that are trained (have ranks spent) get +3 to their total.");
	
	
	public Skill(String skillName, int skillMod) {
		this.skillName = skillName;
		//tooltip.setAutoHide(true);
		//tooltip.setOpacity(0.6);
		//tooltip.setPrefSize(300, 65);
		//tooltip.setStyle("-fx-background-color: Black; -fx-font-size: 15; -fx-alignment: baseline-left; width:auto; word-wrap: break-word;");
		skillCheck = (Button) scene.lookup("#" + skillName + "Check");
		
		/*Match up TextFields */
		skillTotal = (TextField) scene.lookup("#" + skillName + "Total");
		skillAbility = (TextField) scene.lookup("#" + skillName + "Ability");
		skillRanks = (TextField) scene.lookup("#" + skillName + "Ranks");
		skillMisc = (TextField) scene.lookup("#" + skillName + "Misc");
		CS = (CheckBox) scene.lookup("#" + skillName +"CS");
		CS.setDisable(true);
		skillShow = (CheckBox) scene.lookup("#" + skillName +"Show");
		
		overviewName = skillCheck.getText();
		if (overviewName.contains("*")) {
			overviewName = overviewName.replace("+", "");
		}
		skillInfo = new ShortSkill(overviewName, Integer.toString(total));
		
		skillTotal.setText(Integer.toString(0));
		skillAbility.setText(Integer.toString(0));
		skillRanks.setText(Integer.toString(0));
		skillMisc.setText(Integer.toString(0));
		/*Retrieve and set values*/
		abMod = skillMod;
		skillAbility.setText(Integer.toString(skillMod));
		ranks = Integer.parseInt(skillRanks.getText());
		miscMod = Integer.parseInt(skillMisc.getText());
		refreshTotal();
		
		//skillTotal.setTooltip(tooltip);
		
		/*Add Listeners  */
		
		
		if (skillAbility.isEditable()) {
			skillAbility.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean wasFocused, Boolean isFocused) {
					if (wasFocused) {
						abMod = Integer.parseInt(skillAbility.getText());
						refreshTotal();
					}
				}
	        });
			
			skillAbility.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        	public void handle(KeyEvent e) {
	        		if (e.getCode() == KeyCode.ENTER) {
	        			abMod = Integer.parseInt(skillAbility.getText());
						refreshTotal();
	        		}
	        		if (e.getCode() == KeyCode.UP) {
	        			abMod++;
	        			skillAbility.setText(Integer.toString(abMod));
	        			refreshTotal();
	        		}
	        		if (e.getCode() == KeyCode.DOWN) {
	        			abMod--;
	        			skillAbility.setText(Integer.toString(abMod));
	        			refreshTotal();
	        		}
	        	}
	        });
		} else {
			skillAbility.textProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable,
						String oldVal, String newVal) {
					abMod = Integer.parseInt(skillAbility.getText());
					refreshTotal();
				}
	        });
		}
		
		skillRanks.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					skillRanks.setText(Calculations.filterInt(skillRanks.getText(), ranks));
					updateRanks();
				}
				/*
				if(isFocused){
					if (csChecked) {
						skillRanks.setTooltip(tooltip);
						Point2D p = skillRanks.localToScene(55, -100);
						tooltip.show(skillRanks, 
							p.getX() + skillRanks.getScene().getX() + skillRanks.getScene().getWindow().getX(), 
							p.getY() + skillRanks.getScene().getY() + skillRanks.getScene().getWindow().getY());
					}
				}*/
			}
        });
		
		skillRanks.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ENTER) {
        			skillRanks.setText(Calculations.filterInt(skillRanks.getText(), ranks));
        			updateRanks();
        		}
        		if (e.getCode() == KeyCode.UP) {
        			skillRanks.setText(Integer.toString(ranks + 1));
        			updateRanks();
        		}
        		if (e.getCode() == KeyCode.DOWN) {
        			if (ranks > 0) {
	        			skillRanks.setText(Integer.toString(ranks - 1));
	        			updateRanks();
        			}
        		}
        	}
        });
		
		skillMisc.setEditable(false);
		
		if (skillMisc.isEditable()) {
			skillMisc.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean wasFocused, Boolean isFocused) {
					if (wasFocused) {
						skillMisc.setText(Calculations.filterInt(skillMisc.getText(), miscMod));					
						miscMod = Integer.parseInt(skillMisc.getText());
						refreshTotal();
					}
				}
	        });
			skillMisc.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        	public void handle(KeyEvent e) {
	        		if (e.getCode() == KeyCode.ENTER) {
	        			skillMisc.setText(Calculations.filterInt(skillMisc.getText(), miscMod));
	        			miscMod = Integer.parseInt(skillMisc.getText());
						refreshTotal();
	        		}
	        		if (e.getCode() == KeyCode.UP) {
	        			miscMod++;
	        			skillMisc.setText(Integer.toString(miscMod));
	        			refreshTotal();
	        		}
	        		if (e.getCode() == KeyCode.DOWN) {
	        			miscMod--;
	        			skillMisc.setText(Integer.toString(miscMod));
	        			refreshTotal();
	        		}
	        	}
	        });
		} else {
			skillMisc.textProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable,
						String oldVal, String newVal) {
					miscMod = Integer.parseInt(skillMisc.getText());
					refreshTotal();
				}
			});
		}
		
		CS.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	                Boolean oldVal, Boolean newVal) {
	                   csChecked = newVal;
	            }
	        });
		
		skillShow.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	                Boolean oldVal, Boolean newVal) {
               showChecked = newVal;
               if(showChecked){
            	   OverviewTab.skillOverviewData.add(skillInfo);
               } else {
            	   OverviewTab.skillOverviewData.remove(skillInfo);
               }
            }
        });
		
		skillCheck.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	Dialog.rollCount.setText("1");
            	Dialog.rollDie.setValue(20);
            	Dialog.rollMod.setText(skillTotal.getText());
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
                Dialog.rollButton.fire();
		    }
		}); 
	}
	
	public void refreshTotal(){
		total = abMod + ranks + miscMod;
		if(ranks > 0 && csChecked)
			total += 3;
		
		skillTotal.setText(Integer.toString(total));
		skillInfo.setTotal(Integer.toString(total));
	}
	
	void updateRanks() {
		int oldRanks = ranks;
		int newRanks = Integer.parseInt(skillRanks.getText());
		int ranksDiff = newRanks - oldRanks;
		if (SkillTab.skillPoints - ranksDiff >= 0) {
			ranks = newRanks;
			SkillTab.skillPoints -= ranksDiff;
			SkillTab.unspentRanks.setText(Integer.toString(SkillTab.skillPoints));
			refreshTotal();
		} else {
			skillRanks.setText(Integer.toString(oldRanks));
		}
	}
	
	public void loadValues() {
		abMod = Integer.parseInt(skillAbility.getText());
		ranks = Integer.parseInt(skillRanks.getText());
		miscMod = Integer.parseInt(skillMisc.getText());
		total = Integer.parseInt(skillTotal.getText());
		csChecked = CS.isSelected();
		showChecked = skillShow.isSelected();
	}
	
	public String toString() {
		return overviewName;
	}
}