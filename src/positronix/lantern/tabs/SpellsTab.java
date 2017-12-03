package positronix.lantern.tabs;
import positronix.lantern.*;

import java.util.*;
import java.sql.*;

import positronix.lantern.tabs.Dialog;
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

@SuppressWarnings("unchecked")
public class SpellsTab {
	static Scene scene = Main.scene;
	
	Tab spells = Main.tabPane.getTabs().get(5);
	
	Connection classDB, spellDB;
	
	public static ChoiceBox<String> spellChoice = (ChoiceBox<String>) scene.lookup("#spellChoice");
	
	Button removeSpells = (Button) scene.lookup("#removeSpells");
	Button addSpells = (Button) scene.lookup("#addSpells");
	Button spellDetails = (Button) scene.lookup("#spellDetails");
	public static String lookupSpell;
	
	ListView<String> spellList = (ListView<String>) scene.lookup("#spellList");
	TableView<Spell> spellTable = (TableView<Spell>) scene.lookup("#spellTable");
	ObservableList<Spell> spellTableData = FXCollections.observableArrayList();
	
	Button trackReset = (Button) scene.lookup("#trackReset");
	
	static List<SpellInitializer> spellLevels;
	
	static CharacterClass curClass;
	static int[] spellBonus;
	
	
	
	public SpellsTab() {
		spellLevels = new ArrayList<>();
		for (int i = 0; i <= 9; i++) {
			spellLevels.add(new SpellInitializer(Integer.toString(i)));
		}
		
		//spellList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//spellTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		TableColumn<Spell, String> spellLevel = (TableColumn<Spell, String>) spellTable.getColumns().get(0);
		TableColumn<Spell, String> spellName = (TableColumn<Spell, String>) spellTable.getColumns().get(1);
		TableColumn<Spell, String> spellComp = (TableColumn<Spell, String>) spellTable.getColumns().get(2);
		TableColumn<Spell, String> spellDesc = (TableColumn<Spell, String>) spellTable.getColumns().get(3);
		
		spellLevel.setCellValueFactory(new PropertyValueFactory<Spell, String>("level"));
		spellName.setCellValueFactory(new PropertyValueFactory<Spell, String>("name"));
		spellComp.setCellValueFactory(new PropertyValueFactory<Spell, String>("comp"));
		spellDesc.setCellValueFactory(new PropertyValueFactory<Spell, String>("desc"));
		
		spellTable.setItems(spellTableData);
		
		spellChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldVal, String newVal) {
				for (SpellInitializer si : spellLevels) {
					si.reset();
				}
				if (newVal != null) {
					try {
						openClassDB();
						openSpellDB();
						
						int space = newVal.indexOf(" ");
						String className = newVal.substring(0, space);
						curClass = Character.classMap.get(className);
						int classLevel = Integer.parseInt(newVal.substring(space + 1));
						int maxSpellLevel = 0;
						
						for (int i = 0; i <= 9; i++) {
							if (curClass.spellLevels.get(i) == 0) {
								spellLevels.get(i).disable();
							} else if (curClass.spellLevels.get(i) <= classLevel) {
								spellLevels.get(i).enable();
								maxSpellLevel = i;
							} else {
								spellLevels.get(i).disable();
							}
						}
						
						ResultSet classResults = Main.queryDB(classDB, "select * from " + className + " where Level = " + classLevel);
						ResultSetMetaData classMetaData = classResults.getMetaData();
						classResults.next();
						
						switch (classMetaData.getColumnCount()) {
							case 9:
								for (int i = 6; i < 10; i++) {
									spellLevels.get(i-5).perDay.setText(classResults.getString(i));
								}
								break;
							case 11:
								for (int i = 6; i < 12; i++) {
									spellLevels.get(i-5).perDay.setText(classResults.getString(i));
								}
								break;
							case 12:
								for (int i = 6; i < 13; i++) {
									spellLevels.get(i-6).perDay.setText(classResults.getString(i));
								}
								break;
							case 15:
								for (int i = 6; i < 16; i++) {
									spellLevels.get(i-6).perDay.setText(classResults.getString(i));
								}
								break;
							case 18:
								for (int i = 6; i <= 11; i++) {
									spellLevels.get(i-5).perDay.setText(classResults.getString(i));
								}
								for (int i = 12; i < 19; i++) {
									spellLevels.get(i-12).known.setText(classResults.getString(i));
								}
								break;
							case 24:
								for (int i = 6; i <= 14; i++) {
									spellLevels.get(i-5).perDay.setText(classResults.getString(i));
								}
								for (int i = 15; i < 25; i++) {
									spellLevels.get(i-15).known.setText(classResults.getString(i));
								}
								break;
						}
						
						updateSaveDC();
						calcBonusSpells();
						
						ResultSet spellResults;
						switch (className) {
							case "Sorcerer":
							case "Wizard":
								spellResults = Main.queryDB(spellDB, "select * from SorcererWizard where Level <= " + maxSpellLevel + 
										" order by Level, Name;");
								break;
							case "Cleric":
							case "Oracle":
								spellResults = Main.queryDB(spellDB, "select * from ClericOracle where Level <= " + maxSpellLevel + 
										" order by Level, Name;");
								break;
							default:
								spellResults = Main.queryDB(spellDB, "select * from " + className + 
										" where Level <= " + maxSpellLevel +
										" order by Level, Name;");
						}
						
						spellTableData.clear();
						while (spellResults.next()) {
							spellTableData.add(new Spell(spellResults.getString("Level"), spellResults.getString("Name"), 
												spellResults.getString("Component"), spellResults.getString("Description")));
						}
						
						classDB.close();
						spellDB.close();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		removeSpells.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	int removeIndex;
	            List<Integer> il = spellList.getSelectionModel().getSelectedIndices();
	            for (int i = (il.size() - 1); i >= 0; i--) {
	            	removeIndex = il.get(i);
	            	spellList.getItems().remove(removeIndex);
	            }
	        }
	    });
		
		addSpells.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	//List<String> items = spellTable.getSelectionModel().getSelectedItems();
	        	ArrayList<String> items = new ArrayList<String>();
	        	for(Spell s : spellTable.getSelectionModel().getSelectedItems()) {
	        		items.add(s.toString());
	        	}
	            for (int i = 0; i < items.size(); i++) {
	            	if (!spellList.getItems().contains(items.get(i))) {
	            		spellList.getItems().add(items.get(i));
	            	}
	            }
	            Collections.sort(spellList.getItems());
	        }
	    });
		
		spellDetails.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (!spellTable.getSelectionModel().isEmpty()) {
		            lookupSpell = spellTable.getSelectionModel().getSelectedItem().getName();
		            Dialog.spellDetailsStage.show();
	        	}
	        }
	    });
		
		spellTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent click) {
        		if (!spellTable.getSelectionModel().isEmpty()) {
	        		if (click.getButton().equals(MouseButton.PRIMARY)
	        				&& click.getClickCount() == 2) {
	        			if (!spellTable.getSelectionModel().isEmpty()) {
	    		            lookupSpell = spellTable.getSelectionModel().getSelectedItem().getName();
	    		            Dialog.spellDetailsStage.show();
	    	        	}
	        		}
        		}
        	}
        });
		
		trackReset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				for (SpellInitializer si : spellLevels) {
					si.resetTracker();
				}
			}
		});
	}
	
	void openClassDB() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		if (Main.release) {
			classDB = DriverManager.getConnection("jdbc:h2:./Databases/PathfinderClasses", "sa", "");
		} else {
			classDB = DriverManager.getConnection("jdbc:h2:~/Databases/PathfinderClasses", "sa", "");
		}
	}
	
	void openSpellDB() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		if (Main.release) {
			spellDB = DriverManager.getConnection("jdbc:h2:./Databases/PathfinderSpells", "sa", "");
		} else {
			spellDB = DriverManager.getConnection("jdbc:h2:~/Databases/PathfinderSpells", "sa", "");
		}
	}
	
	public static void updateSaveDC() {
		if (curClass != null) {
			int saveDC = 10 + curClass.spellAbility.mod + curClass.spellAbility.miscMod;
			for (int i = 0; i <= 9; i++) {
				if (!spellLevels.get(i).saveDC.isDisabled()) {
					spellLevels.get(i).saveDC.setText(Integer.toString(saveDC));
					saveDC++;
				}
			}
		}
	}
	
	public static void calcBonusSpells(){
		if (curClass != null) {
			int mod = curClass.spellAbility.mod;
			
			if (mod >= 0) {
				spellBonus = new int[mod];
				
				if ((mod > 0) && (mod < 18)) {
					int num = 1, count = 0;
					for(int i = mod; i > 0; i--) {
						spellBonus[i-1] = num;
						spellLevels.get(i).bonus.setText(Integer.toString(spellBonus[i-1]));
						count++;
						if(count%4 == 0) num++;
					}
				}
			}
		}
	}
	
	public static void populateSpellChoice() {
		if (!ClassesTab.classList.getItems().isEmpty()) {
			int space;
			String className;
			
			spellChoice.getItems().clear();
			
			for (String classString : ClassesTab.classList.getItems()) {
				space = classString.indexOf(" ");
				className = classString.substring(0, space);
				
				if (Character.classMap.get(className).castsSpells) {
					spellChoice.getItems().add(classString);
				}
			}
		}
	}
	
}
