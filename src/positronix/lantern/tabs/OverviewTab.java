package positronix.lantern.tabs;

import positronix.lantern.*;

import java.util.List;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

@SuppressWarnings("unchecked")
public class OverviewTab {
	static Scene scene = Main.scene;
	
	public static Ability STR, DEX, CON, INT, WIS, CHA;
	
	public static TextField levels = (TextField) scene.lookup("#levels"); 
	
	public static TextField size = (TextField) scene.lookup("#size");
	
	public static ChoiceBox<String> race = (ChoiceBox<String>) scene.lookup("#race");
	public static Boolean firstTime, confirmRace;
	
	public static Button generateScores = (Button) scene.lookup("#generateScores");
	
	public static int totalHP = 0;
	public static int currentHP = 0;
	public static int tempHP = 0;
	public static int nlDmg = 0;
	public static Button hpMinus = (Button) scene.lookup("#hpMinus");
	public static Button hpReset = (Button) scene.lookup("#hpReset");
	public static Button hpPlus = (Button) scene.lookup("#hpPlus");
	public static Button tempReset = (Button) scene.lookup("#tempReset");
	public static Button tempPlus = (Button) scene.lookup("#tempPlus");
	public static Button ndMinus = (Button) scene.lookup("#ndMinus");
	public static Button ndReset = (Button) scene.lookup("#ndReset");
	public static Button ndPlus = (Button) scene.lookup("#ndPlus");
	public static TextField hpTotal = (TextField) scene.lookup("#hpTotal");
	public static TextField hpCurrent = (TextField) scene.lookup("#hpCurrent");
	public static TextField hpTemp = (TextField) scene.lookup("#hpTemp");
	public static TextField nonlethal = (TextField) scene.lookup("#nonlethal");
	public static Text ndStatus = (Text) scene.lookup("#ndStatus");
	
	public static Button initRoll = (Button) scene.lookup("#initRoll");
	public static TextField initTotal = (TextField) scene.lookup("#initTotal");
	public static TextField initDex = (TextField) scene.lookup("#initDex");
	public static TextField initMisc = (TextField) scene.lookup("#initMisc");
	
	public static double speed;
	public static TextField baseSpeed = (TextField) scene.lookup("#baseSpeed");
	public static TextField armorSpeed = (TextField) scene.lookup("#armorSpeed");
	public static TextField flySpeed = (TextField) scene.lookup("#flySpeed");
	public static TextField flyManeuv = (TextField) scene.lookup("#flyManeuv");
	public static TextField swimSpeed = (TextField) scene.lookup("#swimSpeed");
	public static TextField climbSpeed = (TextField) scene.lookup("#climbSpeed");
	public static TextField burrowSpeed = (TextField) scene.lookup("#burrowSpeed");
	
	public static int maxDex = -1;
	public static TextField acTotal = (TextField) scene.lookup("#acTotal");
	public static TextField acArmor = (TextField) scene.lookup("#acArmor");
	public static TextField acShield = (TextField) scene.lookup("#acShield");
	public static TextField acDex = (TextField) scene.lookup("#acDex");
	public static TextField acSize = (TextField) scene.lookup("#acSize");
	public static TextField acNat = (TextField) scene.lookup("#acNat");
	public static TextField acDef = (TextField) scene.lookup("#acDef");
	public static TextField acMisc = (TextField) scene.lookup("#acMisc");
	public static TextField touchAC = (TextField) scene.lookup("#touchAC");
	public static TextField ffAC = (TextField) scene.lookup("#ffAC");
	
	public static int fortInt = 0;
	public static int refInt = 0;
	public static int willInt = 0;
	public static Button fortSave = (Button) scene.lookup("#fortSave");
	public static Button refSave = (Button) scene.lookup("#refSave");
	public static Button willSave = (Button) scene.lookup("#willSave");
	public static TextField fortTotal = (TextField) scene.lookup("#fortTotal");
	public static TextField refTotal = (TextField) scene.lookup("#refTotal");
	public static TextField willTotal = (TextField) scene.lookup("#willTotal");
	public static TextField fortBase = (TextField) scene.lookup("#fortBase");
	public static TextField refBase = (TextField) scene.lookup("#refBase");
	public static TextField willBase = (TextField) scene.lookup("#willBase");
	public static TextField fortAbility = (TextField) scene.lookup("#fortAbility");
	public static TextField refAbility = (TextField) scene.lookup("#refAbility");
	public static TextField willAbility = (TextField) scene.lookup("#willAbility");
	public static TextField fortMagic = (TextField) scene.lookup("#fortMagic");
	public static TextField refMagic = (TextField) scene.lookup("#refMagic");
	public static TextField willMagic = (TextField) scene.lookup("#willMagic");
	public static TextField fortMisc = (TextField) scene.lookup("#fortMisc");
	public static TextField refMisc = (TextField) scene.lookup("#refMisc");
	public static TextField willMisc = (TextField) scene.lookup("#willMisc");
	
	public static int babInt = 0;
	public static TextField bab = (TextField) scene.lookup("#bab");
	public static TextField sr = (TextField) scene.lookup("#sr");
	
	public static Button cmbRoll = (Button) scene.lookup("#cmbRoll");
	public static TextField cmb = (TextField) scene.lookup("#cmb");
	public static TextField cmbBAB = (TextField) scene.lookup("#cmbBAB");
	public static TextField cmbStr = (TextField) scene.lookup("#cmbStr");
	public static TextField cmbSize = (TextField) scene.lookup("#cmbSize");
	public static TextField cmbMisc = (TextField) scene.lookup("#cmbMisc");
	public static TextField cmd = (TextField) scene.lookup("#cmd");
	public static TextField cmdBAB = (TextField) scene.lookup("#cmdBAB");
	public static TextField cmdStr = (TextField) scene.lookup("#cmdStr");
	public static TextField cmdDex = (TextField) scene.lookup("#cmdDex");
	public static TextField cmdSize = (TextField) scene.lookup("#cmdSize");
	public static TextField cmdMisc = (TextField) scene.lookup("#cmdMisc");
	
	
	public static TableView<ShortSkill> skillOverview = (TableView<ShortSkill>) scene.lookup("#skillOverview");
	public static final ObservableList<ShortSkill> skillOverviewData = FXCollections.observableArrayList();
	
	public static Button atkRoll = (Button) scene.lookup("#atkRoll");
	public static Button dmgRoll = (Button) scene.lookup("#dmgRoll");
	public static TableView<ShortWeapon> weaponOverview = (TableView<ShortWeapon>) scene.lookup("#weaponOverview");
	public static final ObservableList<ShortWeapon> weaponOverviewData = FXCollections.observableArrayList();
	
	
	public static TextArea specialAbilities = (TextArea) scene.lookup("#specialAbilities");
	public static TextArea languages = (TextArea) scene.lookup("#languages");
	
	public OverviewTab() {
        STR = new Ability("str");
        DEX = new Ability("dex");
        CON = new Ability("con");
        INT = new Ability("int");
        WIS = new Ability("wis");
        CHA = new Ability("cha");
        
        firstTime = true;
        
        race.getItems().addAll("Dwarf", "Elf", "Gnome", "Half-elf", "Halfling", "Half-orc", "Human");
        
        race.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        	public void changed(ObservableValue<? extends String> observable,
        			String oldVal, String newVal) {
        		if (!race.getSelectionModel().isEmpty() && firstTime) {
        			race.setDisable(true);
        			CharacterRace selectedRace = Character.raceMap.get(race.getSelectionModel().getSelectedItem());
	        		confirmRace = false;
	        		String raceName = race.getSelectionModel().getSelectedItem();
	        		Dialog.raceConfirmName.setText(raceName + " characters receive the following:");
	        		String raceStats = "";
	        		if (selectedRace.choiceOfBonus) {
	        			raceStats += "+2 to a choice of one ability score.\n";
	        		} else {
		        		if (selectedRace.strb > 0) {
		        			raceStats += "Strength: +" + selectedRace.strb + "\n";
		        		} else if (selectedRace.strb < 0) {
		        			raceStats += "Strength: " + selectedRace.strb + "\n";
		        		}
		        		if (selectedRace.dexb > 0) {
		        			raceStats += "Dexterity: +" + selectedRace.dexb + "\n";
		        		} else if (selectedRace.dexb < 0) {
		        			raceStats += "Dexterity: " + selectedRace.dexb + "\n";
		        		}
		        		if (selectedRace.conb > 0) {
		        			raceStats += "Constitution: +" + selectedRace.conb + "\n";
		        		} else if (selectedRace.conb < 0) {
		        			raceStats += "Constitution: " + selectedRace.conb + "\n";
		        		}
		        		if (selectedRace.intb > 0) {
		        			raceStats += "Intelligence: +" + selectedRace.intb + "\n";
		        		} else if (selectedRace.intb < 0) {
		        			raceStats += "Intelligence: " + selectedRace.intb + "\n";
		        		}
		        		if (selectedRace.wisb > 0) {
		        			raceStats += "Wisdom: +" + selectedRace.wisb + "\n";
		        		} else if (selectedRace.wisb < 0) {
		        			raceStats += "Wisdom: " + selectedRace.wisb + "\n";
		        		}
		        		if (selectedRace.chab > 0) {
		        			raceStats += "Charisma: +" + selectedRace.chab + "\n";
		        		} else if (selectedRace.chab < 0) {
		        			raceStats += "Charisma: " + selectedRace.chab + "\n";
		        		}
		        		
	        		}
	        		if (raceName.equals("Human")) {
	        			raceStats += "\nOne bonus feat.\n";
	        		}
	        		raceStats += "\n";
	        		raceStats += "Size: " + selectedRace.size + "\n";
	        		raceStats += "Speed: " + selectedRace.speed + " ft.\n\n";
	        		String textLanguages = selectedRace.languagesKnown.toString()
	        								.replace("[", "").replace("]", "");
	        		raceStats += "Languages: " + textLanguages;
	        		Dialog.raceConfirmStats.setText(raceStats);
	        		Dialog.raceConfirmStage.showAndWait();
	        		if (confirmRace) {
	        			if (selectedRace.choiceOfBonus) {
	        				Dialog.bonusStage.show();
	        			} else {
	        				STR.adjustScore(selectedRace.strb);
	        				DEX.adjustScore(selectedRace.dexb);
	        				CON.adjustScore(selectedRace.conb);
	        				INT.adjustScore(selectedRace.intb);
	        				WIS.adjustScore(selectedRace.wisb);
	        				CHA.adjustScore(selectedRace.chab);
	        			}
	        			size.setText(selectedRace.size);
	        			baseSpeed.setText(Integer.toString(selectedRace.speed));
	        			String textAreaLanguages = "";
	        			for (String language : selectedRace.languagesKnown) {
	        				textAreaLanguages += language + "\n";
	        			}
	        			languages.setText(textAreaLanguages);
	        			if(race.getValue() == "Human"){
	        				FeatsTab.featPoints++;
			            	FeatsTab.featsAvailable.setText(Integer.toString(FeatsTab.featPoints));
	        			}
	        			ClassesTab.addLevels.setDisable(false);
	        		} else {
	        			race.getSelectionModel().clearSelection();
	        			race.setDisable(false);
	        		}
        		}
        	}
        });
        
        
		generateScores.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            Dialog.generatorStage.show();
	        }
	    });
		
		STR.abScore.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				EquipmentTab.calcCarryingCapacity();	
			}
        });
		
		STR.abMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateStr();
				updateAttackBonus();
				
			}
        });
		STR.abTempMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateStr();
				updateAttackBonus();
			}
        });
		
		DEX.abMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateDex();
				updateAttackBonus();
				
			}
        });
		DEX.abTempMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateDex();
				updateAttackBonus();
			}
        });
		
		CON.abMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateCon();
			}
        });
		CON.abTempMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateCon();
			}
        });
		
		INT.abMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateInt();
				SpellsTab.updateSaveDC();
				SpellsTab.calcBonusSpells();
			}
        });
		INT.abTempMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateInt();
				SpellsTab.updateSaveDC();
				SpellsTab.calcBonusSpells();
			}
        });
		
		WIS.abMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateWis();
				SpellsTab.updateSaveDC();
				SpellsTab.calcBonusSpells();
			}
        });
		WIS.abTempMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateWis();
				SpellsTab.updateSaveDC();
				SpellsTab.calcBonusSpells();
			}
        });
		
		CHA.abMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateCha();
				SpellsTab.updateSaveDC();
				SpellsTab.calcBonusSpells();
			}
        });
		CHA.abTempMod.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateCha();
				SpellsTab.updateSaveDC();
				SpellsTab.calcBonusSpells();
			}
        });
		
		hpTotal.setText("0");
		if (hpTotal.isEditable()) {
			hpTotal.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean wasFocused, Boolean isFocused) {
					if (wasFocused) {
						hpTotal.setText(Calculations.filterInt(hpTotal.getText(), totalHP));
					}
				}
			});
		}
		
		hpCurrent.setText("0");
		hpCurrent.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					hpCurrent.setText(Calculations.filterInt(hpCurrent.getText(), currentHP));
					int newCurrent = Integer.parseInt(hpCurrent.getText());
					if (newCurrent > totalHP) {
						hpCurrent.setText(Integer.toString(totalHP));
					} else {
						if (tempHP > 0 && newCurrent < currentHP) {
							int difference = currentHP - newCurrent;
							if (difference > tempHP) {
								difference -= tempHP;
								currentHP -= difference;
								tempHP = 0;
								hpTemp.setText("0");
								hpCurrent.setText(Integer.toString(currentHP));
							} else {
								tempHP -= difference;
								hpTemp.setText(Integer.toString(tempHP));
								hpCurrent.setText(Integer.toString(currentHP));
							}
						} else {
							currentHP = newCurrent;	
						}
					}
				}
			}
		});
		
		hpCurrent.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (!hpTotal.getText().equals("0")) {
					if (e.getCode() == KeyCode.UP) {
						hpCurrent.setText(Calculations.filterInt(hpCurrent.getText(), currentHP));
						if (currentHP < totalHP) {
							currentHP = Integer.parseInt(hpCurrent.getText());
							currentHP++;
							hpCurrent.setText(Integer.toString(currentHP));
						}
					}
					if (e.getCode() == KeyCode.DOWN) {
						hpCurrent.setText(Calculations.filterInt(hpCurrent.getText(), currentHP));
						currentHP = Integer.parseInt(hpCurrent.getText());
						if (tempHP > 0) {
		        			tempHP--;
		        			hpTemp.setText(Integer.toString(tempHP));
		        		} else {
		        			currentHP--;
		        			hpCurrent.setText(Integer.toString(currentHP));
		        		}
					}
				}
			}
		});
		
		hpMinus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {		//Subtracts from temp. HP (if any), then current HP
	        	if (!hpTotal.getText().equals("0")) {
	        		if (tempHP > 0) {
	        			tempHP--;
	        			hpTemp.setText(Integer.toString(tempHP));
	        		} else {
	        			currentHP--;
	        			hpCurrent.setText(Integer.toString(currentHP));
	        		}
	        	}
	        }
	    });
		
		hpReset.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (!hpTotal.getText().equals("0")) {
	        		currentHP = totalHP;
	        		hpCurrent.setText(Integer.toString(currentHP));
	        	}
	        }
	    });
		
		hpPlus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (!hpTotal.getText().equals("0")) {
		            if (currentHP < totalHP) {
		            	currentHP++;
		            	hpCurrent.setText(Integer.toString(currentHP));
		            }
	        	}
	        }
	    });
		
		hpTemp.setText("0");
		hpTemp.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					if (!hpTotal.getText().equals("0")) {
						hpTemp.setText(Calculations.filterInt(hpTemp.getText(), tempHP));
						tempHP = Integer.parseInt(hpTemp.getText());
					}
				}
			}
		});
		
		hpTemp.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (!hpTotal.getText().equals("0")) {
					if (e.getCode() == KeyCode.UP) {
						hpTemp.setText(Calculations.filterInt(hpTemp.getText(), tempHP));
						tempHP = Integer.parseInt(hpTemp.getText());
						tempHP++;
						hpTemp.setText(Integer.toString(tempHP));
					}
					if (e.getCode() == KeyCode.DOWN) {
						hpTemp.setText(Calculations.filterInt(hpTemp.getText(), tempHP));
						tempHP = Integer.parseInt(hpTemp.getText());
						if (tempHP > 0) {
		        			tempHP--;
		        			hpTemp.setText(Integer.toString(tempHP));
		        		} else {
		        			currentHP--;
		        			hpCurrent.setText(Integer.toString(currentHP));
		        		}
					}
				}
			}
		});
		
		tempReset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!hpTotal.getText().equals("0")) {
					tempHP = 0;
					hpTemp.setText("0");
				}
			}
		});
		
		tempPlus.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!hpTotal.getText().equals("0")) {
					tempHP++;
					hpTemp.setText(Integer.toString(tempHP));
				}
			}
		});
		
		nonlethal.setText("0");
		nonlethal.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					if (!hpTotal.getText().equals("0")) {
						nonlethal.setText(Calculations.filterInt(nonlethal.getText(), nlDmg));
						nlDmg = Integer.parseInt(nonlethal.getText());
						if (nlDmg == currentHP) {
			            	ndStatus.setText("Staggered!");
			            }
			            if (nlDmg > currentHP) {
			            	ndStatus.setText("Unconscious!");
			            }
			            if (nlDmg < currentHP) {
			            	ndStatus.setText("");
			            }
					}
				}
			}
		});
		
		nonlethal.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (!hpTotal.getText().equals("0")) {
					if (e.getCode() == KeyCode.ENTER) {
						nonlethal.setText(Calculations.filterInt(nonlethal.getText(), nlDmg));
						nlDmg = Integer.parseInt(nonlethal.getText());
						if (nlDmg == currentHP) {
			            	ndStatus.setText("Staggered!");
			            }
			            if (nlDmg > currentHP) {
			            	ndStatus.setText("Unconscious!");
			            }
			            if (nlDmg < currentHP) {
			            	ndStatus.setText("");
			            }
					}
					if (e.getCode() == KeyCode.UP) {
						nonlethal.setText(Calculations.filterInt(nonlethal.getText(), nlDmg));
						nlDmg = Integer.parseInt(nonlethal.getText());
						nlDmg++;
						nonlethal.setText(Integer.toString(nlDmg));
					}
					if (e.getCode() == KeyCode.DOWN) {
						nonlethal.setText(Calculations.filterInt(nonlethal.getText(), nlDmg));
						nlDmg = Integer.parseInt(nonlethal.getText());
						if (nlDmg > 0) {
							nlDmg--;
							nonlethal.setText(Integer.toString(nlDmg));
						}
					}
				}
			}
		});
		
		ndMinus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (!hpTotal.getText().equals("0")) {
	        		if (nlDmg > 0) {
			            nlDmg--;
			            nonlethal.setText(Integer.toString(nlDmg));
	        		}
	        	}
	        }
	    });
		
		ndReset.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (!hpTotal.getText().equals("0")) {
	        		nlDmg = 0;
		        	nonlethal.setText("0");
		        	ndStatus.setText("");
	        	}
	        }
	    });
		
		ndPlus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (!hpTotal.getText().equals("0")) {
		            nlDmg++;
		            nonlethal.setText(Integer.toString(nlDmg));
	        	}
	        }
	    });
		
		
		initRoll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.rollCount.setText("1");
            	Dialog.rollDie.setValue(20);
            	Dialog.rollMod.setText(initTotal.getText());
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
                Dialog.rollButton.fire();
            }
        });
		
		initTotal.setText("0");
		
		ChangeListener<String> initListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, 
					String oldVal, String newVal) {
				int dex, misc, total;
				dex = Integer.parseInt(initDex.getText());
				misc = Integer.parseInt(initMisc.getText());
				total = dex + misc;
				initTotal.setText(Integer.toString(total));
			}
		};
		
		initDex.setText("0");
		if (initDex.isEditable()) {
			initDex.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean oldValue, Boolean newValue) {
						if (oldValue) {
							initDex.setText(Calculations.filterInt(initDex.getText(), 0));
							int dex, misc, total;
							dex = Integer.parseInt(initDex.getText());
							misc = Integer.parseInt(initMisc.getText());
							total = dex + misc;
							initTotal.setText(Integer.toString(total));
						}
					}
			});
		} else {
			initDex.textProperty().addListener(initListener);
		}
		
		initMisc.setText("0");
		if (initMisc.isEditable()) {
			initMisc.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
						if (oldValue) {
							initMisc.setText(Calculations.filterInt(initMisc.getText(), 0));
							int dex, misc, total;
							dex = Integer.parseInt(initDex.getText());
							misc = Integer.parseInt(initMisc.getText());
							total = dex + misc;
							initTotal.setText(Integer.toString(total));
						}
					}
			});
			initMisc.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {
					if (e.getCode() == KeyCode.ENTER) {
						initMisc.setText(Calculations.filterInt(initMisc.getText(), 0));
						int dex, misc, total;
						dex = Integer.parseInt(initDex.getText());
						misc = Integer.parseInt(initMisc.getText());
						total = dex + misc;
						initTotal.setText(Integer.toString(total));
					}
					int misc = Integer.parseInt(initMisc.getText());
					if (e.getCode() == KeyCode.UP) {
						misc++;
						initMisc.setText(Integer.toString(misc));
					}
					if (e.getCode() == KeyCode.DOWN) {
						misc--;
						initMisc.setText(Integer.toString(misc));
					}
				}
			});
		} else {
			initMisc.textProperty().addListener(initListener);
		}
		
		
		if (baseSpeed.isEditable()) {
			baseSpeed.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean wasFocused, Boolean isFocused) {
					if (wasFocused) {
						speed = Double.parseDouble(baseSpeed.getText());
						double quarterSpeed = speed / 4;
						
						swimSpeed.setText(Double.toString(quarterSpeed));
						climbSpeed.setText(Double.toString(quarterSpeed));
					}
				}
			});
		} else {
			baseSpeed.textProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable,
						String oldVal, String newVal) {
					speed = Double.parseDouble(baseSpeed.getText());
					double quarterSpeed = speed / 4;
					
					swimSpeed.setText(Double.toString(quarterSpeed));
					climbSpeed.setText(Double.toString(quarterSpeed));
				}
			});
		}
		
		
		ChangeListener<Boolean> acFocusListener = new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					int armor = Integer.parseInt(acArmor.getText());
					int shield = Integer.parseInt(acShield.getText());
					int dex = Integer.parseInt(acDex.getText());
					int size = Integer.parseInt(acSize.getText());
					int nat = Integer.parseInt(acNat.getText());
					int def = Integer.parseInt(acDef.getText());
					int misc = Integer.parseInt(acMisc.getText());
					int total = 10 + armor + shield + dex + size + nat + def + misc;
					int touch = total - armor - shield - nat;
					int flatFooted = total;
					if (dex > 0) flatFooted -= dex;
					acTotal.setText(Integer.toString(total));
					touchAC.setText(Integer.toString(touch));
					ffAC.setText(Integer.toString(flatFooted));
				}
			}
		};
		ChangeListener<String> acTextListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldVal, String newVal) {
				int armor = Integer.parseInt(acArmor.getText());
				int shield = Integer.parseInt(acShield.getText());
				int dex = Integer.parseInt(acDex.getText());
				int size = Integer.parseInt(acSize.getText());
				int nat = Integer.parseInt(acNat.getText());
				int def = Integer.parseInt(acDef.getText());
				int misc = Integer.parseInt(acMisc.getText());
				int total = 10 + armor + shield + dex + size + nat + def + misc;
				int touch = total - armor - shield - nat;
				int flatFooted = total;
				if (dex > 0) flatFooted -= dex;
				acTotal.setText(Integer.toString(total));
				touchAC.setText(Integer.toString(touch));
				ffAC.setText(Integer.toString(flatFooted));
			}
		};
		
		acTotal.setText("10");
		List<TextField> acFields = new ArrayList<>();
		acFields.add(acArmor);
		acFields.add(acShield);
		acFields.add(acDex);
		acFields.add(acSize);
		acFields.add(acNat);
		acFields.add(acDef);
		acFields.add(acMisc);
		for (TextField field : acFields) {
			field.setText("0");
			if (field.isEditable()) {
				field.focusedProperty().addListener(acFocusListener);
			} else {
				field.textProperty().addListener(acTextListener);
			}
		}
		
		
		ChangeListener<Boolean> fortFocusListener = new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> obserable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					int base = Integer.parseInt(fortBase.getText());
					int ability = Integer.parseInt(fortAbility.getText());
					int magic = Integer.parseInt(fortMagic.getText());
					int misc = Integer.parseInt(fortMisc.getText());
					int total = base + ability + magic + misc;
					fortTotal.setText(Integer.toString(total));
				}
			}
		};
		ChangeListener<String> fortTextListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, 
					String oldVal, String newVal) {
				int base = Integer.parseInt(fortBase.getText());
				int ability = Integer.parseInt(fortAbility.getText());
				int magic = Integer.parseInt(fortMagic.getText());
				int misc = Integer.parseInt(fortMisc.getText());
				int total = base + ability + magic + misc;
				fortTotal.setText(Integer.toString(total));
			}
		};
		
		fortSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.rollCount.setText("1");
            	Dialog.rollDie.setValue(20);
            	Dialog.rollMod.setText(fortTotal.getText());
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
                Dialog.rollButton.fire();
            }
        });
		fortTotal.setText("0");
		List<TextField> fortFields = new ArrayList<>();
		fortFields.add(fortBase);
		fortFields.add(fortAbility);
		fortFields.add(fortMagic);
		fortFields.add(fortMisc);
		for (TextField field : fortFields) {
			field.setText("0");
			if (field.isEditable()) {
				field.focusedProperty().addListener(fortFocusListener);
			} else {
				field.textProperty().addListener(fortTextListener);
			}
		}
		
		ChangeListener<Boolean> refFocusListener = new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> obserable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					int base = Integer.parseInt(refBase.getText());
					int ability = Integer.parseInt(refAbility.getText());
					int magic = Integer.parseInt(refMagic.getText());
					int misc = Integer.parseInt(refMisc.getText());
					int total = base + ability + magic + misc;
					refTotal.setText(Integer.toString(total));
				}
			}
		};
		
		ChangeListener<String> refTextListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, 
					String oldVal, String newVal) {
				int base = Integer.parseInt(refBase.getText());
				int ability = Integer.parseInt(refAbility.getText());
				int magic = Integer.parseInt(refMagic.getText());
				int misc = Integer.parseInt(refMisc.getText());
				int total = base + ability + magic + misc;
				refTotal.setText(Integer.toString(total));
			}
		};
		
		refSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.rollCount.setText("1");
            	Dialog.rollDie.setValue(20);
            	Dialog.rollMod.setText(refTotal.getText());
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
                Dialog.rollButton.fire();
            }
        });
		refTotal.setText("0");
		List<TextField> refFields = new ArrayList<>();
		refFields.add(refBase);
		refFields.add(refAbility);
		refFields.add(refMagic);
		refFields.add(refMisc);
		for (TextField field : refFields) {
			field.setText("0");
			if (field.isEditable()) {
				field.focusedProperty().addListener(refFocusListener);
			} else {
				field.textProperty().addListener(refTextListener);
			}
		}
		
		ChangeListener<Boolean> willFocusListener = new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> obserable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					int base = Integer.parseInt(willBase.getText());
					int ability = Integer.parseInt(willAbility.getText());
					int magic = Integer.parseInt(willMagic.getText());
					int misc = Integer.parseInt(willMisc.getText());
					int total = base + ability + magic + misc;
					willTotal.setText(Integer.toString(total));
				}
			}
		};
		
		ChangeListener<String> willTextListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, 
					String oldVal, String newVal) {
				int base = Integer.parseInt(willBase.getText());
				int ability = Integer.parseInt(willAbility.getText());
				int magic = Integer.parseInt(willMagic.getText());
				int misc = Integer.parseInt(willMisc.getText());
				int total = base + ability + magic + misc;
				willTotal.setText(Integer.toString(total));
			}
		};
		
		willSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.rollCount.setText("1");
            	Dialog.rollDie.setValue(20);
            	Dialog.rollMod.setText(willTotal.getText());
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
                Dialog.rollButton.fire();
            }
        });
		
		willTotal.setText("0");
		List<TextField> willFields = new ArrayList<>();
		willFields.add(willBase);
		willFields.add(willAbility);
		willFields.add(willMagic);
		willFields.add(willMisc);
		for (TextField field : willFields) {
			field.setText("0");
			if (field.isEditable()) {
				field.focusedProperty().addListener(willFocusListener);
			} else {
				field.textProperty().addListener(willTextListener);
			}
		}
		
		
		bab.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldVal, String newVal) {
				cmbBAB.setText(bab.getText());
				cmdBAB.setText(bab.getText());
				updateAttackBonus();
			}
		});
		
		
		ChangeListener<Boolean> cmbFocusListener = new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					int BAB = Integer.parseInt(cmbBAB.getText());
					int str = Integer.parseInt(cmbStr.getText());
					int size = Integer.parseInt(cmbSize.getText());
					int misc = Integer.parseInt(cmbMisc.getText());
					int total = BAB + str + size + misc;
					cmb.setText(Integer.toString(total));
				}
			}
		};
		ChangeListener<String> cmbTextListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldVal, String newVal) {
				int BAB = Integer.parseInt(cmbBAB.getText());
				int str = Integer.parseInt(cmbStr.getText());
				int size = Integer.parseInt(cmbSize.getText());
				int misc = Integer.parseInt(cmbMisc.getText());
				int total = BAB + str + size + misc;
				cmb.setText(Integer.toString(total));
			}
		};
		
		cmbRoll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.rollCount.setText("1");
            	Dialog.rollDie.setValue(20);
            	Dialog.rollMod.setText(cmb.getText());
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
                Dialog.rollButton.fire();
            }
        });
		cmb.setText("0");
		List<TextField> cmbFields = new ArrayList<>();
		cmbFields.add(cmbBAB);
		cmbFields.add(cmbStr);
		cmbFields.add(cmbSize);
		cmbFields.add(cmbMisc);
		for (TextField field : cmbFields) {
			field.setText("0");
			if (field.isEditable()) {
				field.focusedProperty().addListener(cmbFocusListener);
			} else {
				field.textProperty().addListener(cmbTextListener);
			}
		}
		
		ChangeListener<Boolean> cmdFocusListener = new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					int BAB = Integer.parseInt(cmdBAB.getText());
					int str = Integer.parseInt(cmdStr.getText());
					int dex = Integer.parseInt(cmdDex.getText());
					int size = Integer.parseInt(cmdSize.getText());
					int misc = Integer.parseInt(cmdMisc.getText());
					int total = BAB + str + dex + size + misc + 10;
					cmd.setText(Integer.toString(total));
				}
			}
		};
		ChangeListener<String> cmdTextListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldVal, String newVal) {
				int BAB = Integer.parseInt(cmdBAB.getText());
				int str = Integer.parseInt(cmdStr.getText());
				int dex = Integer.parseInt(cmdDex.getText());
				int size = Integer.parseInt(cmdSize.getText());
				int misc = Integer.parseInt(cmdMisc.getText());
				int total = BAB + str + dex + size + misc + 10;
				cmd.setText(Integer.toString(total));
			}
		};
		
		cmd.setText("0");
		List<TextField> cmdFields = new ArrayList<>();
		cmdFields.add(cmdBAB);
		cmdFields.add(cmdStr);
		cmdFields.add(cmdDex);
		cmdFields.add(cmdSize);
		cmdFields.add(cmdMisc);
		for (TextField field : cmdFields) {
			field.setText("0");
			if (field.isEditable()) {
				field.focusedProperty().addListener(cmdFocusListener);
			} else {
				field.textProperty().addListener(cmdTextListener);
			}
		}
		
		
        TableColumn<ShortSkill, String> skillOverviewName = (TableColumn<ShortSkill, String>) skillOverview.getColumns().get(0);
        TableColumn<ShortSkill, String> skillOverviewTotal = (TableColumn<ShortSkill, String>) skillOverview.getColumns().get(1);
        
        skillOverviewName.setCellValueFactory(new PropertyValueFactory<ShortSkill, String>("name"));
        skillOverviewTotal.setCellValueFactory(new PropertyValueFactory<ShortSkill, String>("total"));
        
        skillOverview.setItems(skillOverviewData);
        
        skillOverview.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent click) {
        		if (!skillOverview.getSelectionModel().isEmpty()) {
	        		if (click.getButton().equals(MouseButton.PRIMARY)
	        				&& click.getClickCount() == 2) {
	    				Dialog.rollCount.setText("1");
	                	Dialog.rollDie.setValue(20);
	                	Dialog.rollMod.setText(skillOverview.getSelectionModel().getSelectedItem().getTotal());
	                    Dialog.rollerStage.show();
	                    Dialog.rollerStage.requestFocus();
	                    Dialog.rollButton.fire();
	        		}
        		}
        	}
        });
        
        
        TableColumn<ShortWeapon, String> weaponOverviewName = (TableColumn<ShortWeapon, String>) weaponOverview.getColumns().get(0);
        TableColumn<ShortWeapon, String> weaponOverviewBonus = (TableColumn<ShortWeapon, String>) weaponOverview.getColumns().get(1);
        TableColumn<ShortWeapon, String> weaponOverviewCritical = (TableColumn<ShortWeapon, String>) weaponOverview.getColumns().get(2);
        TableColumn<ShortWeapon, String> weaponOverviewDamage = (TableColumn<ShortWeapon, String>) weaponOverview.getColumns().get(3);
        TableColumn<ShortWeapon, String> weaponOverviewAmmo = (TableColumn<ShortWeapon, String>) weaponOverview.getColumns().get(4);
        TableColumn<ShortWeapon, String> weaponOverviewRange = (TableColumn<ShortWeapon, String>) weaponOverview.getColumns().get(5);
        TableColumn<ShortWeapon, String> weaponOverviewType = (TableColumn<ShortWeapon, String>) weaponOverview.getColumns().get(6);
        
        weaponOverviewName.setCellValueFactory(new PropertyValueFactory<ShortWeapon, String>("name"));
        weaponOverviewBonus.setCellValueFactory(new PropertyValueFactory<ShortWeapon, String>("bonus"));
        weaponOverviewCritical.setCellValueFactory(new PropertyValueFactory<ShortWeapon, String>("critical"));
        weaponOverviewDamage.setCellValueFactory(new PropertyValueFactory<ShortWeapon, String>("damage"));
        weaponOverviewAmmo.setCellValueFactory(new PropertyValueFactory<ShortWeapon, String>("ammo"));
        weaponOverviewRange.setCellValueFactory(new PropertyValueFactory<ShortWeapon, String>("range"));
        weaponOverviewType.setCellValueFactory(new PropertyValueFactory<ShortWeapon, String>("type"));
        
        weaponOverview.setItems(weaponOverviewData);
        
        weaponOverview.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent click) {
        		if (!weaponOverview.getSelectionModel().isEmpty()) {
	        		if (click.getButton().equals(MouseButton.PRIMARY)
	        				&& click.getClickCount() == 2) {
	    				Dialog.rollCount.setText("1");
	                	Dialog.rollDie.setValue(20);
	                	Dialog.rollMod.setText(weaponOverview.getSelectionModel().getSelectedItem().getBonus());
	                    Dialog.rollerStage.show();
	                    Dialog.rollerStage.requestFocus();
	                    Dialog.rollButton.fire();
	                    String dmg = weaponOverview.getSelectionModel().getSelectedItem().getDamage();
	                    Dialog.rollCount.setText(dmg.substring(0, dmg.indexOf("d")));
	                    if (dmg.contains("+")) {
	                    	Dialog.rollDie.setValue(Integer.parseInt(dmg.substring(dmg.indexOf("d")+1, dmg.indexOf("+"))));
	                    	Dialog.rollMod.setText(dmg.substring(dmg.indexOf("+")+1));
	                    } else {
	                    	Dialog.rollDie.setValue(Integer.parseInt(dmg.substring(dmg.indexOf("d")+1)));
	                    	Dialog.rollMod.setText("0");
	                    }
	                    Dialog.rollButton.fire();
	        		}
        		}
        	}
        });
        
        atkRoll.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		if (!weaponOverview.getSelectionModel().isEmpty()) {
	        		Dialog.rollCount.setText("1");
	            	Dialog.rollDie.setValue(20);
	            	Dialog.rollMod.setText(weaponOverview.getSelectionModel().getSelectedItem().getBonus());
	                Dialog.rollerStage.show();
	                Dialog.rollerStage.requestFocus();
	                Dialog.rollButton.fire();
        		}
        	}
        });
        
        dmgRoll.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		if (!weaponOverview.getSelectionModel().isEmpty()) {
	        		String dmg = weaponOverview.getSelectionModel().getSelectedItem().getDamage();
	                Dialog.rollCount.setText(dmg.substring(0, dmg.indexOf("d")));
	                if (dmg.contains("+")) {
	                	Dialog.rollDie.setValue(Integer.parseInt(dmg.substring(dmg.indexOf("d")+1, dmg.indexOf("+"))));
	                	Dialog.rollMod.setText(dmg.substring(dmg.indexOf("+")+1));
	                } else {
	                	Dialog.rollDie.setValue(Integer.parseInt(dmg.substring(dmg.indexOf("d")+1)));
	                	Dialog.rollMod.setText("0");
	                }
	                Dialog.rollerStage.show();
	                Dialog.rollerStage.requestFocus();
	                Dialog.rollButton.fire();
        		}
        	}
        });
	}
	
	void updateStr() {
		String strTotal = Integer.toString(STR.mod + STR.miscMod);
		
		cmbStr.setText(strTotal);
		cmdStr.setText(strTotal);
		
		SkillTab.climb.skillAbility.setText(strTotal);
		SkillTab.swim.skillAbility.setText(strTotal);
	}
	
	public static void updateDex() {
		String dexTotal = Integer.toString(DEX.mod + DEX.miscMod);
		
		initDex.setText(dexTotal);
		refAbility.setText(dexTotal);
		cmdDex.setText(dexTotal);
		
		if (!EquipmentTab.equippedArmor.getItems().isEmpty()) {
			int current;
			int lowest = 9;
			for (Armor armor : EquipmentTab.equippedArmor.getItems()) {
				try {
					current = Integer.parseInt(armor.getMaxDex());
					if (current < lowest)
						lowest = current;
				} catch (NumberFormatException e) {
					//Armor has no max dex bonus
				}
			}
			if (lowest == 9)
				lowest = -1;
			maxDex = lowest;
		} else {
			maxDex = -1;
		}
		if (maxDex != -1 && Integer.parseInt(dexTotal) > maxDex) {
			acDex.setText(Integer.toString(maxDex));
		} else {
			acDex.setText(dexTotal);
		}
		
		SkillTab.acrobatics.skillAbility.setText(dexTotal);
		SkillTab.disableDevice.skillAbility.setText(dexTotal);
		SkillTab.escapeArtist.skillAbility.setText(dexTotal);
		SkillTab.fly.skillAbility.setText(dexTotal);
		SkillTab.ride.skillAbility.setText(dexTotal);
		SkillTab.sleightOfHand.skillAbility.setText(dexTotal);
		SkillTab.stealth.skillAbility.setText(dexTotal);
	}
	
	void updateCon() {
		String conTotal = Integer.toString(CON.mod + CON.miscMod);
		
		fortAbility.setText(conTotal);
	}
	
	void updateInt() {
		String intTotal = Integer.toString(INT.mod + INT.miscMod);
		
		SkillTab.appraise.skillAbility.setText(intTotal);
		SkillTab.craft1.skillAbility.setText(intTotal);
		SkillTab.craft2.skillAbility.setText(intTotal);
		SkillTab.craft3.skillAbility.setText(intTotal);
		SkillTab.arcana.skillAbility.setText(intTotal);
		SkillTab.dungeon.skillAbility.setText(intTotal);
		SkillTab.engineering.skillAbility.setText(intTotal);
		SkillTab.geography.skillAbility.setText(intTotal);
		SkillTab.history.skillAbility.setText(intTotal);
		SkillTab.local.skillAbility.setText(intTotal);
		SkillTab.nature.skillAbility.setText(intTotal);
		SkillTab.nobility.skillAbility.setText(intTotal);
		SkillTab.planes.skillAbility.setText(intTotal);
		SkillTab.religion.skillAbility.setText(intTotal);
		SkillTab.linguistics.skillAbility.setText(intTotal);
		SkillTab.spellcraft.skillAbility.setText(intTotal);
	}
	
	void updateWis() {
		String wisTotal = Integer.toString(WIS.mod + WIS.miscMod);
		
		willAbility.setText(wisTotal);
		
		SkillTab.heal.skillAbility.setText(wisTotal);
		SkillTab.perception.skillAbility.setText(wisTotal);
		SkillTab.profession1.skillAbility.setText(wisTotal);
		SkillTab.profession2.skillAbility.setText(wisTotal);
		SkillTab.senseMotive.skillAbility.setText(wisTotal);
		SkillTab.survival.skillAbility.setText(wisTotal);
	}
	
	void updateCha() {
		String chaTotal = Integer.toString(CHA.mod + CHA.miscMod);
		
		SkillTab.bluff.skillAbility.setText(chaTotal);
		SkillTab.diplomacy.skillAbility.setText(chaTotal);
		SkillTab.disguise.skillAbility.setText(chaTotal);
		SkillTab.handleAnimal.skillAbility.setText(chaTotal);
		SkillTab.intimidate.skillAbility.setText(chaTotal);
		SkillTab.perform1.skillAbility.setText(chaTotal);
		SkillTab.perform2.skillAbility.setText(chaTotal);
		SkillTab.useMagicDevice.skillAbility.setText(chaTotal);
	}
	
	public static void updateAttackBonus() {	//Also updates damage (don't be fooled like I was)
		int strBonus = STR.mod + STR.miscMod + babInt;
		int dexBonus = DEX.mod + DEX.miscMod + babInt;
		
		for (ShortWeapon sw : weaponOverviewData) {
			if (sw.getCategory().contains("unarmed")
				|| sw.getCategory().contains("melee")) {
				sw.setBonus(Integer.toString(strBonus));
				if (STR.mod + STR.miscMod != 0) {
					String dmg = sw.getDamage();
					if (dmg.contains("+")) {
						dmg = dmg.substring(0, dmg.indexOf("+"));
					} else if (dmg.contains("-")) {
						dmg = dmg.substring(0, dmg.indexOf("-"));
					}
					if (STR.mod + STR.miscMod > 0) {
						sw.setDamage(dmg + "+" + (STR.mod + STR.miscMod));
					} else if (STR.mod + STR.miscMod < 0) {
						sw.setDamage(dmg + (STR.mod + STR.miscMod));
					}
				}
			}
			if (sw.getCategory().contains("ranged")) {
				sw.setBonus(Integer.toString(dexBonus));
			}
			
			for (Adjustment a : ModifiersTab.modTableData) {
				if (a.getType().equals("Weapon")) {
					if (a.getWeapon().equals(sw)) {
						a.apply();
					}
				}
			}
		}
		
		for (Adjustment a : ModifiersTab.modTableData) {
			if ((a.getTarget().contains("Attack") || a.getTarget().contains("Damage"))
					&& a.isEnabled()) {
				a.apply();
			}
		}
	}
}