package positronix.lantern.tabs;

import positronix.lantern.*;

import java.util.*;
import java.util.Collections.*;
import java.sql.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;

@SuppressWarnings("unchecked")
public class ClassesTab {
	static Scene scene = Main.scene;
	
	Connection classDB;
	
	public static TextField totalLevel = (TextField) scene.lookup("#totalLevel");
	public static int level = 0;
	
	public static ChoiceBox<String> favoredClass = (ChoiceBox<String>) scene.lookup("#favoredClass");
	public static ChoiceBox<String> favoredClass2 = (ChoiceBox<String>) scene.lookup("#favoredClass2");
	
	public static Button addLevels = (Button) scene.lookup("#addLevels");
	public static ChoiceBox<String> classChoice = (ChoiceBox<String>) scene.lookup("#classChoice");
	public static TextField levelsField = (TextField) scene.lookup("#levelsField");
	public static Button levelsMinus = (Button) scene.lookup("#levelsMinus");
	public static Button levelsPlus = (Button) scene.lookup("#levelsPlus");
	public static ChoiceBox<String> favoredBonus = (ChoiceBox<String>) scene.lookup("#favoredBonus");
	
	public static ListView<String> classList = (ListView<String>) scene.lookup("#classList");
	public static Button removeClass = (Button) scene.lookup("#removeClass");
	
	public static ChoiceBox<String> domain1 = (ChoiceBox<String>) scene.lookup("#domain1");
	public static ChoiceBox<String> domain2 = (ChoiceBox<String>) scene.lookup("#domain2");
	
	public ClassesTab() {
		classChoice.getItems().addAll("Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Wizard",
									  "Alchemist", "Cavalier", "Gunslinger", "Inquisitor", "Magus", "Oracle", "Summoner", "Witch", 
									  "Antipaladin", "Ninja", "Samurai");
		
		totalLevel.setText("0");
		
		addLevels.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            if (classChoice.getValue() != null) {
	            	int addedLevels = Integer.parseInt(Calculations.filterInt(levelsField.getText(), 1));
	            	if (addedLevels + level <= 20) {
		            	String entry;
		            	String className1 = classChoice.getValue();
		            	int classIndex = 0;
		            	int oldLevel, newLevel;
		            	boolean exists = false;
		            	for (String s : classList.getItems()) {
		            		if (s.contains(className1)) {
		            			exists = true;
		            			oldLevel = Integer.parseInt(s.substring(className1.length()+1));
		            			newLevel = oldLevel + Integer.parseInt(levelsField.getText());
		            			classList.getItems().set(classIndex, className1 + " " + Integer.toString(newLevel));
		            		}
		            		classIndex++;
		            	}
		            	if (!exists) {
	            			entry = className1 + " " + levelsField.getText();
	    		            classList.getItems().add(entry);
	            		}
		            	
		            	int levels = Integer.parseInt(levelsField.getText());
		            	CharacterClass curClass = Character.classMap.get(className1);
		            	
		            	//Adds ability points, hp, skillPoints, and feat points based on class and level:
		            	for (int i = 0; i < levels; i++) {
		            		level++;
			            	totalLevel.setText(Integer.toString(level));
	            		//Ability
		            		if (level % 4 == 0) {
		            			Dialog.increaseStage.showAndWait();
		            		}
	            		//HP
		            		if (level == 1) {
		            			OverviewTab.totalHP = curClass.hd + OverviewTab.CON.mod;
		            			OverviewTab.hpTotal.setText(Integer.toString(OverviewTab.totalHP));
		            			OverviewTab.currentHP = OverviewTab.totalHP;
		            			OverviewTab.hpCurrent.setText(Integer.toString(OverviewTab.currentHP));
		            		} else {
		            			OverviewTab.totalHP += Calculations.roll(1, curClass.hd, OverviewTab.CON.mod);
		            			OverviewTab.hpTotal.setText(Integer.toString(OverviewTab.totalHP));
		            		}
			            //Skills:
			            	SkillTab.skillPoints += curClass.sr + OverviewTab.INT.mod;
			            	SkillTab.unspentRanks.setText(Integer.toString(SkillTab.skillPoints));
			            //Money
			            	EquipmentTab.totalGP = curClass.asw;
			            	EquipmentTab.gp.setText(Integer.toString(EquipmentTab.totalGP));
			            //Spells
			            	SpellsTab.calcBonusSpells();
			            //Feats
			            	if((level+1) % 2 == 0){
				            	FeatsTab.featPoints++;
				            	FeatsTab.featsAvailable.setText(Integer.toString(FeatsTab.featPoints));
				            }
		            	}
		            	//Adds skills based on class:
		            	for(int i = 0; i < curClass.classSkills.size(); i++){
		            		curClass.classSkills.get(i).CS.setSelected(true);
		            	}
	            		try {
							openClassDB();
							int space;
							String className2, classLevel;
							OverviewTab.babInt = 0;
							OverviewTab.fortInt = 0;
							OverviewTab.refInt = 0;
							OverviewTab.willInt = 0;
							for (String classString : classList.getItems()) {
								space = classString.indexOf(" ");
								className2 = classString.substring(0, space);
								classLevel = classString.substring(space + 1);
								ResultSet results = Main.queryDB(classDB, "select * from " + className2 + " where Level = " + classLevel);
								results.next();
								OverviewTab.babInt += results.getInt("BAB");
								OverviewTab.fortInt += results.getInt("FortSave");
								OverviewTab.refInt += results.getInt("RefSave");
								OverviewTab.willInt += results.getInt("WillSave");
							}
							OverviewTab.bab.setText(Integer.toString(OverviewTab.babInt));
							OverviewTab.fortBase.setText(Integer.toString(OverviewTab.fortInt));
							OverviewTab.refBase.setText(Integer.toString(OverviewTab.refInt));
							OverviewTab.willBase.setText(Integer.toString(OverviewTab.willInt));
							classDB.close();
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            		SpellsTab.populateSpellChoice();
		            	
		            	levelsField.setText("1");
		            	
		            	String levelsString = classList.getItems().toString()
	    									.replace("[", "").replace("]", "");
		            	if (classList.getItems().size() == 1) {
		            		OverviewTab.levels.setText(levelsString);
		            	} else {
		            		OverviewTab.levels.setText("(" + level + ") " + levelsString);
		            	}
		            } else {
		            	Dialog.levelErrorStage.show();
		            }
	            }
	        }
	    });
		
		levelsField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					levelsField.setText(Calculations.filterInt(levelsField.getText(), 1));
					int addedLevels = Integer.parseInt(Calculations.filterInt(levelsField.getText(), 1));
					if (addedLevels + level > 20) {
						int difference = 20 - level;
						if (difference < 1) {
							difference = 1;
						}
						levelsField.setText(Integer.toString(difference));
					}
					if (addedLevels < 1) {
						levelsField.setText("1");
					}
				}
			}
		});
		
		levelsField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				int addedLevels = Integer.parseInt(Calculations.filterInt(levelsField.getText(), 1));
				if (e.getCode() == KeyCode.ENTER) {
					levelsField.setText(Calculations.filterInt(levelsField.getText(), 1));
					if (addedLevels + level > 20) {
						int difference = 20 - level;
						if (difference < 1) {
							difference = 1;
						}
						levelsField.setText(Integer.toString(difference));
					}
					if (addedLevels < 1) {
						levelsField.setText("1");
					}
					addLevels.fire();
				}
				if (e.getCode() == KeyCode.UP) {
					if (addedLevels + level < 20) {
						addedLevels++;
						levelsField.setText(Integer.toString(addedLevels));
					}
				}
				if (e.getCode() == KeyCode.DOWN) {
					if (addedLevels > 1) {
						addedLevels--;
						levelsField.setText(Integer.toString(addedLevels));
					}
				}
			}
		});
		
		levelsMinus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	int addedLevels = Integer.parseInt(levelsField.getText());
	            if (addedLevels > 1) {
	            	addedLevels--;
	            	levelsField.setText(Integer.toString(addedLevels));
	            }
	        }
	    });
		
		levelsPlus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	int addedLevels = Integer.parseInt(levelsField.getText());
	            if (addedLevels + level < 20) {
	            	addedLevels++;
	            	levelsField.setText(Integer.toString(addedLevels));
	            }
	        }
	    });
		
		classList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		removeClass.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	int removeIndex;
	            List<Integer> il = classList.getSelectionModel().getSelectedIndices();
	            for (int i = (il.size() - 1); i >= 0; i--) {
	            	removeIndex = il.get(i);
	            	classList.getItems().remove(removeIndex);		//for some reason I cannot fathom, just saying remove(il.get(i)) does not work
	            }
	        }
	    });
	}
	
	void openClassDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		if (Main.release) {
			classDB = DriverManager.getConnection("jdbc:h2:./Databases/PathfinderClasses", "sa", "");
		} else {
			classDB = DriverManager.getConnection("jdbc:h2:~/Databases/PathfinderClasses", "sa", "");
		}
	}
}
