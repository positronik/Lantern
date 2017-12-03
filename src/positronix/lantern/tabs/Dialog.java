package positronix.lantern.tabs;

import java.util.Random;

import positronix.lantern.*;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("unchecked")
public class Dialog {
	Scene scene = Main.scene;
	Stage stage = Main.primaryStage;
	
	static Scene rollerScene = Main.rollerScene;
	static Scene lookupScene = Main.lookupScene;
	static Scene generatorScene = Main.generatorScene;
	static Scene costsScene = Main.costsScene;
	static Scene bonusScene = Main.bonusScene;
	static Scene increaseScene = Main.increaseScene;
	static Scene continueScene = Main.continueScene;
	static Scene levelErrorScene = Main.levelErrorScene;
	static Scene modSelectorScene = Main.modSelectorScene;
	static Scene modWeaponSelectorScene = Main.modWeaponSelectorScene;
	static Scene raceConfirmScene = Main.raceConfirmScene;
	static Scene spellDetailsScene = Main.spellDetailsScene;
	static Scene spellKeyScene = Main.spellKeyScene;
	
	public static Stage rollerStage = new Stage();
	public static Stage lookupStage = new Stage();
	public static Stage generatorStage = new Stage();
	public static Stage costsStage = new Stage();
	public static Stage bonusStage = new Stage();
	public static Stage increaseStage = new Stage();
	public static Stage continueStage = new Stage();
	public static Stage levelErrorStage = new Stage();
	public static Stage modSelectorStage = new Stage();
	public static Stage modWeaponSelectorStage = new Stage();
	public static Stage raceConfirmStage = new Stage();
	public static Stage spellDetailsStage = new Stage();
	public static Stage spellKeyStage = new Stage();
	
	Connection spellDB; //equipDB, featDB
	
	//DiceRoller
	public static TextField rollCount = (TextField) rollerScene.lookup("#rollCount");
	public static ComboBox<Integer> rollDie = (ComboBox<Integer>) rollerScene.lookup("#rollDie");
	public static TextField rollMod = (TextField) rollerScene.lookup("#rollMod");
	public static Button rollButton = (Button) rollerScene.lookup("#rollButton");
	public static TextField rollOutput = (TextField) rollerScene.lookup("#rollOutput");
	public static TextArea rollHistory = (TextArea) rollerScene.lookup("#rollHistory");
	
	//Lookup
	static String lookupType = "";
	public static ToggleGroup lookupGroup = new ToggleGroup();
	public static RadioButton lookupFeats = (RadioButton) lookupScene.lookup("#lookupFeats");
	public static RadioButton lookupEquipment = (RadioButton) lookupScene.lookup("#lookupEquipment");
	public static RadioButton lookupSpells = (RadioButton) lookupScene.lookup("#lookupSpells");
	public static TextField lookupText = (TextField) lookupScene.lookup("#lookupText");
	public static Button lookupGo = (Button) lookupScene.lookup("#lookupGo");
	public static ListView<String> lookupList = (ListView<String>) lookupScene.lookup("#lookupList");
	public static Button lookupButton = (Button) lookupScene.lookup("#lookupButton");
	
	//AbilityGenerator
	static boolean generated;
	public static Button rollScores = (Button) generatorScene.lookup("#rollScores");
	public static ListView<String> abRollList = (ListView<String>) generatorScene.lookup("#abRollList");
	public static Button applyRolls = (Button) generatorScene.lookup("#applyRolls");
	
	public static ChoiceBox<String> startingPoints = (ChoiceBox<String>) generatorScene.lookup("#startingPoints");
	
	public static TextField pointsLeft = (TextField) generatorScene.lookup("#pointsLeft");
	public static Button pointCosts = (Button) generatorScene.lookup("#pointCosts");
	public static Button resetPoints = (Button) generatorScene.lookup("#resetPoints");
	public static Button applyPoints = (Button) generatorScene.lookup("#applyPoints");
	public static int pointsUsed = 0, startPoints;
	public static Button pointBuyOK = (Button) costsScene.lookup("#pointBuyOK");
	public static AbilityGenerator strgen, dexgen, congen, intgen, wisgen, chagen;
	
	//AbilityBonus
	public static ToggleGroup bonusGroup = new ToggleGroup();
	public static RadioButton strBonus = (RadioButton) bonusScene.lookup("#strBonus");
	public static RadioButton dexBonus = (RadioButton) bonusScene.lookup("#dexBonus");
	public static RadioButton conBonus = (RadioButton) bonusScene.lookup("#conBonus");
	public static RadioButton intBonus = (RadioButton) bonusScene.lookup("#intBonus");
	public static RadioButton wisBonus = (RadioButton) bonusScene.lookup("#wisBonus");
	public static RadioButton chaBonus = (RadioButton) bonusScene.lookup("#chaBonus");
	public static Button applyBonus = (Button) bonusScene.lookup("#applyBonus");
	
	//AbilityIncrease
	public static ToggleGroup increaseGroup = new ToggleGroup();
	public static RadioButton strIncrease = (RadioButton) increaseScene.lookup("#strIncrease");
	public static RadioButton dexIncrease = (RadioButton) increaseScene.lookup("#dexIncrease");
	public static RadioButton conIncrease = (RadioButton) increaseScene.lookup("#conIncrease");
	public static RadioButton intIncrease = (RadioButton) increaseScene.lookup("#intIncrease");
	public static RadioButton wisIncrease = (RadioButton) increaseScene.lookup("#wisIncrease");
	public static RadioButton chaIncrease = (RadioButton) increaseScene.lookup("#chaIncrease");
	public static Button applyIncrease = (Button) increaseScene.lookup("#applyIncrease");
	
	//ContinueConfirm
	public static Button continueSave = (Button) continueScene.lookup("#continueSave");
	public static Button continueOK = (Button) continueScene.lookup("#continueOK");
	public static Button continueCancel = (Button) continueScene.lookup("#continueCancel");
	
	//LevelError
	public static Button levelErrorOK = (Button) levelErrorScene.lookup("#levelErrorOK");
	
	/*
	 * ModSelector (controls inside a ScrollPane or TitledPane are inaccessible until the scene
	 * is shown, so ModSelector controls are assigned and event-handled below in initializeModSelector
	 */
	public static Button modStr, modDex, modCon, modInt, modWis, modCha,
							modFort, modRef, modWill,
							modInit, modSpeed, modAC,
							modWepAtk, modWepDmg,
							modMeleeAtk, modMeleeDmg,
							modRangedAtk, modRangedDmg,
							modCMB, modCMD,
							modSkill;
	public static ListView<Skill> skillModList;
	public static boolean modSelectorInitialized = false;
	
	//ModWeaponSelector
	public static ListView<ShortWeapon> modWeaponList = (ListView<ShortWeapon>) modWeaponSelectorScene.lookup("#modWeaponList");
	public static Button modWeaponOK = (Button) modWeaponSelectorScene.lookup("#modWeaponOK");
	public static Text modWeaponText = (Text) modWeaponSelectorScene.lookup("#modWeaponText");
	
	//RaceConfirm
	public static Text raceConfirmName = (Text) raceConfirmScene.lookup("#raceConfirmName");
	public static Text raceConfirmStats = (Text) raceConfirmScene.lookup("#raceConfirmStats");
	public static Button raceConfirmOK = (Button) raceConfirmScene.lookup("#raceConfirmOK");
	public static Button raceConfirmCancel = (Button) raceConfirmScene.lookup("#raceConfirmCancel");
	
	
	//SpellDetails
	public static Text spellName = (Text) spellDetailsScene.lookup("#spellName");
	public static Text spellSchool = (Text) spellDetailsScene.lookup("#spellSchool");
	public static Text spellCast = (Text) spellDetailsScene.lookup("#spellCast");
	public static Text spellRange = (Text) spellDetailsScene.lookup("#spellRange");
	public static Text spellComp = (Text) spellDetailsScene.lookup("#spellComp");
	public static Text spellAim = (Text) spellDetailsScene.lookup("#spellAim");
	public static Text spellDuration = (Text) spellDetailsScene.lookup("#spellDuration");
	public static Text spellSave = (Text) spellDetailsScene.lookup("#spellSave");
	public static Text spellResist = (Text) spellDetailsScene.lookup("#spellResist");
	public static Button spellKey = (Button) spellDetailsScene.lookup("#spellKey");
	public static TextArea spellDesc = (TextArea) spellDetailsScene.lookup("#spellDesc");
	
	//SpellDetailsKey
	public static Button spellKeyOK = (Button) spellKeyScene.lookup("#spellKeyOK");
	
	public Dialog() {
		rollerStage.setScene(rollerScene);
        rollerStage.setTitle("Dice Roller");
        
        rollDie.getItems().addAll(2, 3, 4, 6, 8, 10, 12, 20, 100);
        rollDie.setValue(20);
        
        rollCount.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ENTER) {
        			rollButton.fire();
        		}
        	}
        });
        
        rollDie.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ENTER) {
        			rollButton.fire();
        		}
        	}
        });
        
        rollMod.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ENTER) {
        			rollButton.fire();
        		}
        	}
        });
        
        rollButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                int count = Integer.parseInt(rollCount.getText());
                int die = Integer.parseInt(rollDie.getEditor().getText());
                int mod = Integer.parseInt(rollMod.getText());
                int result = Calculations.roll(count, die, mod);
                rollOutput.setText(Integer.toString(result));
                String record = count + "d" + die + "+" + mod + " = " + result + "\n";
                rollHistory.appendText(record);
            }
        });
        
        rollerStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ESCAPE) {
        			rollerStage.close();
        		}
        	}
        });
        
        
        lookupStage.setScene(lookupScene);
        lookupStage.setTitle("Lookup");
        
        lookupFeats.setToggleGroup(lookupGroup);
        lookupEquipment.setToggleGroup(lookupGroup);
        lookupSpells.setToggleGroup(lookupGroup);
        
        { //TODO: Remove when feat and equipment details are implemented
        	lookupFeats.setDisable(true);
        	lookupEquipment.setDisable(true);
        }
        
        lookupText.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ENTER) {
        			lookupGo.fire();
        		}
        	}
        });
        
        lookupGo.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		lookupList.getItems().clear();
        		String searchString = lookupText.getText();
        		if (!searchString.isEmpty()) {
            		searchString = searchString.replace(" ", "%");	//% is a SQL wildcard matching zero or more characters
            		searchString = searchString.toLowerCase();
            		
	        		if (lookupFeats.isSelected()) {
	        			lookupType = "Feat";
	        			
	        		} else if (lookupEquipment.isSelected()) {
					    lookupType = "Equipment";
					    
	        		} else if (lookupSpells.isSelected()) {
						lookupType = "Spell";
						
						try {
							openSpellDB();
							ResultSet results = Main.queryDB(spellDB, "select Name from Master where lower(Name) like '" + searchString + " %'" + 
																			"or lower(Name) like '% " + searchString + "'"); //Matches whole words only
							while (results.next()) {
								lookupList.getItems().add(results.getString("Name"));
							}
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						lookupType = "";
					}
        		}
        	}
        });
        
        lookupList.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent click) {
        		if (!lookupList.getItems().isEmpty()) {
        			if (click.getButton().equals(MouseButton.PRIMARY)
	        				&& click.getClickCount() == 2) {
        				lookupButton.fire();
        			}
        		}
        	}
        });
        
        lookupButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		if (lookupType.equals("Feat")) {
            		
				} else if (lookupType.equals("Equipment")) {
					
				} else if (lookupType.equals("Spell")) {
					SpellsTab.lookupSpell = lookupList.getSelectionModel().getSelectedItem();
            		spellDetailsStage.show();
				} else {
					
				}
        	}
        });
        
        
        //Generating ability scores
        generatorStage.setScene(generatorScene);
        generatorStage.setTitle("Ability Score Generator");
        generatorStage.initOwner(stage);
        generatorStage.initModality(Modality.WINDOW_MODAL);
        
        generatorStage.showingProperty().addListener(new ChangeListener<Boolean>() {
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean wasShowing, Boolean isShowing) {
        		if (wasShowing && costsStage.isShowing())
        			costsStage.close();
        	}
        });
        
        strgen = new AbilityGenerator("str");
        dexgen = new AbilityGenerator("dex");
        congen = new AbilityGenerator("con");
        intgen = new AbilityGenerator("int");
        wisgen = new AbilityGenerator("wis");
        chagen = new AbilityGenerator("cha");
        startingPoints.setValue("15 - Standard");
        pointsLeft.setText("15");
        pointsLeft.setEditable(false);
        startPoints = Integer.parseInt(pointsLeft.getText());
        AbilityGenerator.startingPoints = startPoints;
        
        generated = false;
        
        rollScores.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	generated = true;
	        	
	        	abRollList.getItems().clear();
	        	strgen.roll.clear();
	        	dexgen.roll.clear();
	        	congen.roll.clear();
	        	intgen.roll.clear();
	        	wisgen.roll.clear();
	        	chagen.roll.clear();
            	
	            for (int i = 0; i < 6; i++) {
	            	abRollList.getItems().add(Integer.toString(rollAbility()));
	            }
	        }
	    });
        
        applyRolls.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            if (generated == true && abRollList.getItems().size() == 0) {
	            	OverviewTab.STR.abScore.setText(strgen.roll.getText());
	            	OverviewTab.DEX.abScore.setText(dexgen.roll.getText());
	            	OverviewTab.CON.abScore.setText(congen.roll.getText());
	            	OverviewTab.INT.abScore.setText(intgen.roll.getText());
	            	OverviewTab.WIS.abScore.setText(wisgen.roll.getText());
	            	OverviewTab.CHA.abScore.setText(chagen.roll.getText());
	            	generatorStage.close();
	            	afterAbility();
	            }
	        }
	    });
        
        startingPoints.getItems().addAll("10 - Low", "15 - Standard", "20 - High", "25 - Epic");
        startingPoints.valueProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					String amt = startingPoints.getValue().substring(0, 2);
					pointsLeft.setText(amt);
					startPoints = Integer.parseInt(pointsLeft.getText());
			}
        });
        
        costsStage.setScene(costsScene);
        costsStage.setTitle("Point Costs");
        costsStage.setWidth(136);
        costsStage.initStyle(StageStyle.UTILITY);
        
        costsStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ESCAPE) {
        			costsStage.close();
        		}
        	}
        });
        
        pointCosts.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            costsStage.show();
	            costsStage.requestFocus();
	        }
	    });
        
        pointBuyOK.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            costsStage.close();
	        }
	    });
        
        resetPoints.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		resetPointBuy();
        	}
        });
        
        applyPoints.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            if (pointsLeft.getText().equals("0")) {
	            	OverviewTab.STR.abScore.setText(strgen.points.getText());
	            	OverviewTab.DEX.abScore.setText(dexgen.points.getText());
	            	OverviewTab.CON.abScore.setText(congen.points.getText());
	            	OverviewTab.INT.abScore.setText(intgen.points.getText());
	            	OverviewTab.WIS.abScore.setText(wisgen.points.getText());
	            	OverviewTab.CHA.abScore.setText(chagen.points.getText());
	            	generatorStage.close();
	            	afterAbility();
	            }
	        }
	    });
        
        
        bonusStage.setScene(bonusScene);
        bonusStage.setTitle("Racial Ability Bonus");
        bonusStage.initStyle(StageStyle.UNDECORATED);
        bonusStage.initOwner(stage);
        bonusStage.initModality(Modality.WINDOW_MODAL);
        
        strBonus.setToggleGroup(bonusGroup);
        dexBonus.setToggleGroup(bonusGroup);
        conBonus.setToggleGroup(bonusGroup);
        intBonus.setToggleGroup(bonusGroup);
        wisBonus.setToggleGroup(bonusGroup);
        chaBonus.setToggleGroup(bonusGroup);
        
        strBonus.setUserData("str");
        dexBonus.setUserData("dex");
        conBonus.setUserData("con");
        intBonus.setUserData("int");
        wisBonus.setUserData("wis");
        chaBonus.setUserData("cha");
        
        applyBonus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (bonusGroup.getSelectedToggle() != null) {
	        		Ability target = getAbility(bonusGroup.getSelectedToggle().getUserData().toString());
	        		target.score += 2;
	        		target.abScore.setText(Integer.toString(target.score));
	            	bonusStage.close();
	        	}
	        }
	    });
        
        
        increaseStage.setScene(increaseScene);
        increaseStage.setTitle("Ability Increase");
        increaseStage.initStyle(StageStyle.UNDECORATED);
        increaseStage.initOwner(stage);
        increaseStage.initModality(Modality.WINDOW_MODAL);
        
        strIncrease.setToggleGroup(increaseGroup);
        dexIncrease.setToggleGroup(increaseGroup);
        conIncrease.setToggleGroup(increaseGroup);
        intIncrease.setToggleGroup(increaseGroup);
        wisIncrease.setToggleGroup(increaseGroup);
        chaIncrease.setToggleGroup(increaseGroup);
        
        strIncrease.setUserData("str");
        dexIncrease.setUserData("dex");
        conIncrease.setUserData("con");
        intIncrease.setUserData("int");
        wisIncrease.setUserData("wis");
        chaIncrease.setUserData("cha");
        
        increaseStage.showingProperty().addListener(new ChangeListener<Boolean>() {
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean wasShowing, Boolean isShowing) {
        		if (isShowing) {
	        		for (Toggle t : increaseGroup.getToggles()) {
	        			t.setSelected(false);
	        			Text current = (Text) increaseScene.lookup("#cur" + t.getUserData().toString());
	        			Ability a = getAbility(t.getUserData().toString());
	        			current.setText(a.abScore.getText());
	        		}
        		}
        	}
        });
        
        increaseGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
        		if (old_toggle != null) {
        			Text oldCur = (Text) increaseScene.lookup("#cur" + old_toggle.getUserData().toString());
        			int i = Integer.parseInt(oldCur.getText()) - 1;
        			oldCur.setText(Integer.toString(i));
        		}
        		if (new_toggle != null) {
	        		Text newCur = (Text) increaseScene.lookup("#cur" + new_toggle.getUserData().toString());
	    			int i = Integer.parseInt(newCur.getText()) + 1;
	    			newCur.setText(Integer.toString(i));
        		}
            }
        });
        
        applyIncrease.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (increaseGroup.getSelectedToggle() != null) {
	        		Ability target = getAbility(increaseGroup.getSelectedToggle().getUserData().toString());
	        		target.score++;
	        		target.abScore.setText(Integer.toString(target.score));
	            	increaseStage.close();
	        	}
	        }
	    });
        
        increaseStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ESCAPE) {
        			increaseStage.close();
        		}
        	}
        });
        
        
        continueStage.setScene(continueScene);
        continueStage.setTitle("Warning");
        continueStage.initOwner(stage);
        continueStage.initModality(Modality.WINDOW_MODAL);

        continueSave.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	Main.cont = false;
	        	continueStage.close();
	        	Main.saveFile();
	        }
	    });
        
        continueOK.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	Main.cont = true;
	        	continueStage.close();
	        }
	    });
        
        continueCancel.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	Main.cont = false;
	        	continueStage.close();
	        }
	    });
        
        
        levelErrorStage.setScene(levelErrorScene);
        levelErrorStage.setTitle("Level Error");
        levelErrorStage.setWidth(281);
        levelErrorStage.setHeight(125);
        levelErrorStage.initStyle(StageStyle.UTILITY);
        levelErrorStage.initOwner(stage);
        levelErrorStage.initModality(Modality.WINDOW_MODAL);
        
        levelErrorOK.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	levelErrorStage.close();
	        }
	    });
        
        levelErrorStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ESCAPE) {
        			levelErrorStage.close();
        		}
        	}
        });
        
        
        modSelectorStage.setScene(modSelectorScene);
        modSelectorStage.setTitle("Adjustment Selector");
        modSelectorStage.initStyle(StageStyle.UTILITY);
        modSelectorStage.setWidth(464);
        modSelectorStage.initOwner(stage);
        modSelectorStage.initModality(Modality.WINDOW_MODAL);
        
        
        modWeaponSelectorStage.setScene(modWeaponSelectorScene);
        modWeaponSelectorStage.setTitle("Weapon to be adjusted");
        modWeaponSelectorStage.initStyle(StageStyle.UNDECORATED);
        modWeaponSelectorStage.initOwner(modSelectorStage);
        modWeaponSelectorStage.initModality(Modality.WINDOW_MODAL);
        
        modWeaponOK.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		if (modWeaponList.getItems().isEmpty()) {
        			modWeaponSelectorStage.close();
        		} else {
        			if (!modWeaponList.getSelectionModel().isEmpty()) {
	        			ModifiersTab.target = modWeaponList.getSelectionModel().getSelectedItem();
	        			modWeaponSelectorStage.close();
	        			modSelectorStage.close();
        			}
        		}
        	}
        });
        
        
        raceConfirmStage.setScene(raceConfirmScene);
        raceConfirmStage.setTitle("Confirm Race Selection");
        raceConfirmStage.initOwner(stage);
        raceConfirmStage.initModality(Modality.WINDOW_MODAL);
        
        raceConfirmOK.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		OverviewTab.confirmRace = true;
        		raceConfirmStage.close();
        	}
        });
        
        raceConfirmCancel.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		OverviewTab.confirmRace = false;
        		raceConfirmStage.close();
        	}
        });
        
        
        spellDetailsStage.setScene(spellDetailsScene);
        spellDetailsStage.setTitle("Spell Details");
        
        spellDetailsStage.showingProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {
					//String spellNameIn;
					try {
						try {
							openSpellDB();
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							System.out.println("Database opening error");
						}
						ResultSet results = Main.queryDB(spellDB, "select * from Master where Name = '" + SpellsTab.lookupSpell + "';");
						results.next();
						spellName.setText(results.getString("Name"));
						spellSchool.setText(results.getString("School"));
						spellCast.setText(results.getString("CastTime"));
						spellRange.setText(results.getString("Range"));
						spellComp.setText(results.getString("Components"));
						spellAim.setText(results.getString("Aim"));
						spellDuration.setText(results.getString("Duration"));
						spellSave.setText(results.getString("SavingThrow"));
						spellResist.setText(results.getString("Resistance"));
						spellDesc.setText(results.getString("Description"));
						spellDB.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						spellDesc.setText("Database error.");
						e.printStackTrace();
					}
				}
			}
		});
        
        spellDetailsStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ESCAPE) {
        			spellDetailsStage.close();
        		}
        	}
        });
		
        
        spellKeyStage.setScene(spellKeyScene);
        spellKeyStage.setTitle("Spell Key");
        spellKeyStage.setResizable(false);
        spellKeyStage.setWidth(395);
        spellKeyStage.setHeight(460);
        
        spellKey.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	spellKeyStage.show();
	        }
	    });
        
        spellKeyOK.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	spellKeyStage.close();
	        }
	    });
        
        spellKeyStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if (e.getCode() == KeyCode.ESCAPE) {
        			spellKeyStage.close();
        		}
        	}
        });
	}
	
	int rollAbility()  {
		Random r = new Random();
		int current;
		int lowest = r.nextInt(6) + 1;
		int result = lowest;

		for (int i = 0; i < 3; i++) {
			current = r.nextInt(6) + 1;
			if (current < lowest) {
				lowest = current;
			}
			result += current;	
		}
		result -= lowest;
		return result;
	}
	
	Ability getAbility(String name) {
		Ability ab;
		
		switch (name) {
			case "str":
				ab = OverviewTab.STR;
				break;
			case "dex":
				ab = OverviewTab.DEX;
				break;
			case "con":
				ab = OverviewTab.CON;
				break;
			case "int":
				ab = OverviewTab.INT;
				break;
			case "wis":
				ab = OverviewTab.WIS;
				break;
			case "cha":
				ab = OverviewTab.CHA;
				break;
			default:
				ab = null;
		}
		
		return ab;
	}
	
	void openSpellDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		if (Main.release) {
			spellDB = DriverManager.getConnection("jdbc:h2:./Databases/PathfinderSpells", "sa", "");
		} else {
			spellDB = DriverManager.getConnection("jdbc:h2:~/Databases/PathfinderSpells", "sa", "");
		}
	}
	
	void closeDBs() throws SQLException { //TODO: Close other DB's when added
		if (!spellDB.isClosed()) {
			spellDB.close();
		}
	}
	
	/*Gets total current points the user hasn't spent inside the point buy window.
	 * It was done outside the abilityGenerator because the total points were reset
	 * every time the user changed the points added in another ability. */
    public static void getTotalPoints(){
    	pointsUsed = strgen.getPointsUsed() + dexgen.getPointsUsed() + congen.getPointsUsed()
    			+ intgen.getPointsUsed() + wisgen.getPointsUsed() + chagen.getPointsUsed();
    	int totalPoints = startPoints - pointsUsed;
    	pointsLeft.setText(Integer.toString(totalPoints));
    }
    
    public static void resetPointBuy() {
    	pointsUsed = 0;
    	strgen.resetPoints();
    	dexgen.resetPoints();
    	congen.resetPoints();
    	intgen.resetPoints();
    	wisgen.resetPoints();
    	chagen.resetPoints();
    	int totalPoints = startPoints;
    	pointsLeft.setText(Integer.toString(totalPoints));
    }
    
    public static void afterAbility() {
    	OverviewTab.generateScores.setVisible(false);
    	if (OverviewTab.race.getSelectionModel().isEmpty()) {
    		OverviewTab.race.setDisable(false);
    	}
    	ModifiersTab.addMod.setDisable(false);
    }
    
    public static void initializeModSelector() {
    	if (!modSelectorInitialized) {
	    	modStr = (Button) modSelectorScene.lookup("#modStr");
	    	modDex = (Button) modSelectorScene.lookup("#modDex");
	    	modCon = (Button) modSelectorScene.lookup("#modCon");
	    	modInt = (Button) modSelectorScene.lookup("#modInt");
	    	modWis = (Button) modSelectorScene.lookup("#modWis");
	    	modCha = (Button) modSelectorScene.lookup("#modCha");
	    	modFort = (Button) modSelectorScene.lookup("#modFort");
	    	modRef = (Button) modSelectorScene.lookup("#modRef");
	    	modWill = (Button) modSelectorScene.lookup("#modWill");
	    	modInit = (Button) modSelectorScene.lookup("#modInit");
	    	modSpeed = (Button) modSelectorScene.lookup("#modSpeed");
	    	modAC = (Button) modSelectorScene.lookup("#modAC");
	    	modWepAtk = (Button) modSelectorScene.lookup("#modWepAtk");
	    	modWepDmg = (Button) modSelectorScene.lookup("#modWepDmg");
	    	modMeleeAtk = (Button) modSelectorScene.lookup("#modMeleeAtk");
	    	modMeleeDmg = (Button) modSelectorScene.lookup("#modMeleeDmg");
	    	modRangedAtk = (Button) modSelectorScene.lookup("#modRangedAtk");
	    	modRangedDmg = (Button) modSelectorScene.lookup("#modRangedDmg");
	    	modCMB = (Button) modSelectorScene.lookup("#modCMB");
	    	modCMD = (Button) modSelectorScene.lookup("#modCMD");
	    	skillModList = (ListView<Skill>) modSelectorScene.lookup("#skillModList");
	    	modSkill = (Button) modSelectorScene.lookup("#modSkill");
	    	
	    	modStr.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.STR;
	        		ModifiersTab.modChosen.setText("Strength");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modDex.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.DEX;
	        		ModifiersTab.modChosen.setText("Dexterity");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modCon.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.CON;
	        		ModifiersTab.modChosen.setText("Constitution");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modInt.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.INT;
	        		ModifiersTab.modChosen.setText("Intelligence");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modWis.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.WIS;
	        		ModifiersTab.modChosen.setText("Wisdom");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modCha.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.CHA;
	        		ModifiersTab.modChosen.setText("Charisma");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modFort.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.fortMisc;
	        		ModifiersTab.modChosen.setText("Fortitude save");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modRef.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.refMisc;
	        		ModifiersTab.modChosen.setText("Reflex save");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modWill.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.willMisc;
	        		ModifiersTab.modChosen.setText("Will save");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modInit.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.initMisc;
	        		ModifiersTab.modChosen.setText("Initiative");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modSpeed.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.baseSpeed;
	        		ModifiersTab.modChosen.setText("Speed");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modAC.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.acMisc;
	        		ModifiersTab.modChosen.setText("Armor Class");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modWepAtk.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		if (OverviewTab.weaponOverviewData.isEmpty()) {
	        			modWeaponList.getItems().clear();
	                	modWeaponText.setText("No weapons found.");
	                } else {
	                	modWeaponText.setText("");
	                	modWeaponList.getItems().clear();
	                	modWeaponList.getItems().addAll(OverviewTab.weaponOverviewData);
	                }
	        		
	        		modWeaponSelectorStage.showAndWait();
	        		if (ModifiersTab.target != null) {
	        			ModifiersTab.modChosen.setText("Weapon Attack (" + ModifiersTab.target + ")");
	        		}
	        	}
	        });
	        
	        modWepDmg.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		if (OverviewTab.weaponOverviewData.isEmpty()) {
	        			modWeaponList.getItems().clear();
	                	modWeaponText.setText("No weapons found.");
	                } else {
	                	modWeaponText.setText("");
	                	modWeaponList.getItems().clear();
	                	modWeaponList.getItems().addAll(OverviewTab.weaponOverviewData);
	                }
	        		
	        		modWeaponSelectorStage.showAndWait();
	        		if (ModifiersTab.target != null) {
	        			ModifiersTab.modChosen.setText("Weapon Damage (" + ModifiersTab.target + ")");
	        		}
	        	}
	        });
	        
	        modMeleeAtk.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ObservableList<ShortWeapon> weapons = FXCollections.observableArrayList();
	        		for (ShortWeapon sw : OverviewTab.weaponOverviewData) {
	        			if (sw.getCategory().contains("unarmed") || 
	        					sw.getCategory().contains("melee")) {
	        				weapons.add(sw);
	        			}
	        		}
	        		
	        		ModifiersTab.target = weapons;
	    			ModifiersTab.modChosen.setText("Melee Attack");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modMeleeDmg.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ObservableList<ShortWeapon> weapons = FXCollections.observableArrayList();
	        		for (ShortWeapon sw : OverviewTab.weaponOverviewData) {
	        			if (sw.getCategory().contains("unarmed") || 
	        					sw.getCategory().contains("melee")) {
	        				weapons.add(sw);
	        			}
	        		}
	        		
	        		ModifiersTab.target = weapons;
	    			ModifiersTab.modChosen.setText("Melee Damage");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modRangedAtk.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ObservableList<ShortWeapon> weapons = FXCollections.observableArrayList();
	        		for (ShortWeapon sw : OverviewTab.weaponOverviewData) {
	        			if (sw.getCategory().contains("ranged")) {
	        				weapons.add(sw);
	        			}
	        		}
	        		
	        		ModifiersTab.target = weapons;
	    			ModifiersTab.modChosen.setText("Ranged Attack");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modRangedDmg.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ObservableList<ShortWeapon> weapons = FXCollections.observableArrayList();
	        		for (ShortWeapon sw : OverviewTab.weaponOverviewData) {
	        			if (sw.getCategory().contains("ranged")) {
	        				weapons.add(sw);
	        			}
	        		}
	        		
	        		ModifiersTab.target = weapons;
	    			ModifiersTab.modChosen.setText("Ranged Damage");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modCMB.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.cmbMisc;
	        		ModifiersTab.modChosen.setText("CMB");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modCMD.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		ModifiersTab.target = OverviewTab.cmdMisc;
	        		ModifiersTab.modChosen.setText("CMD");
	        		
	        		modSelectorStage.close();
	        	}
	        });
	        
	        modSkill.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent e) {
	        		Skill skill = skillModList.getSelectionModel().getSelectedItem();
	        		ModifiersTab.target = skill.skillMisc;
	        		ModifiersTab.modChosen.setText(skill.toString());
	        		
	        		modSelectorStage.close();
	        	}
	        });
    	}
    	
    	skillModList.getItems().clear();
    	skillModList.getItems().addAll(SkillTab.allSkills);
    	
        modSelectorInitialized = true;
    }
}
