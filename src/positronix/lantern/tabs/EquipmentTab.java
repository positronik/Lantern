/*EquipmentTab
 * Last Modified: 7/14/2015 
 * Description: This class is used to instantiate the equipment tab. It allows the user to buy/sell
 * weapons and armor and add in custom items. All weight is tallied up and lets the 
 * user know how much they can carry, lift, and drag depending on their strength.
 * 
 * TODO:
 * Have total weight affect speed and dexterity if it is a medium or heavy load.
 */
package positronix.lantern.tabs;

import positronix.lantern.*;

import java.util.*;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("unchecked")
public class EquipmentTab {
	static Scene scene = Main.scene;
	
	Connection equipDB;
	
	Tab equipment = Main.tabPane.getTabs().get(4);
	
	public static TextField pp = (TextField) scene.lookup("#pp");
	public static TextField gp = (TextField) scene.lookup("#gp");
	public static TextField sp = (TextField) scene.lookup("#sp");
	public static TextField cp = (TextField) scene.lookup("#cp");
	public static int totalPP = 0, totalGP = 0, totalSP = 0, totalCP = 0, costInt;
	static String cost, type;
	static boolean notEnoughGold = false;
	
	public static Button removeWeapons = (Button) scene.lookup("#removeWeapons");
	public static Button addWeapons = (Button) scene.lookup("#addWeapons");
	//Button weaponDetails = (Button) scene.lookup("#weaponDetails");
	
	public static ListView<Weapon> weaponList = (ListView<Weapon>) scene.lookup("#weaponList");
	public static Button unequipWeapon = (Button) scene.lookup("#unequipWeapon");
	public static Button equipWeapon = (Button) scene.lookup("#equipWeapon");
	public static ListView<Weapon> equippedWeapons = (ListView<Weapon>) scene.lookup("#equippedWeapons");
	public static TableView<Weapon> weaponTable = (TableView<Weapon>) scene.lookup("#weaponTable");
	public static ObservableList<Weapon> weaponTableData = FXCollections.observableArrayList();
	
	public static Button removeArmor = (Button) scene.lookup("#removeArmor");
	public static Button addArmor = (Button) scene.lookup("#addArmor");
	//Button armorDetails = (Button) scene.lookup("#armorDetails");
	
	public static ListView<Armor> armorList = (ListView<Armor>) scene.lookup("#armorList");
	public static Button unequipArmor = (Button) scene.lookup("#unequipArmor");
	public static Button equipArmor = (Button) scene.lookup("#equipArmor");
	public static ListView<Armor> equippedArmor = (ListView<Armor>) scene.lookup("#equippedArmor");
	public static TableView<Armor> armorTable = (TableView<Armor>) scene.lookup("#armorTable");
	public static ObservableList<Armor> armorTableData = FXCollections.observableArrayList();
	
	boolean tablesPopulated = false;
	
	public static TextField wtTotal = (TextField) scene.lookup("#wtTotal");
	public static String weight, totalWeight;
	public static double weightDouble, totalWeightDouble;
	
	static TextField lightLoad = (TextField) scene.lookup("#lightLoad");
	static TextField medLoad = (TextField) scene.lookup("#medLoad");
	static TextField heavyLoad = (TextField) scene.lookup("#heavyLoad");
	static TextField liftOver = (TextField) scene.lookup("#liftOver");
	static TextField liftOff = (TextField) scene.lookup("#liftOff");
	static TextField dragPush = (TextField) scene.lookup("#dragPush");
	
	public EquipmentTab() {
		equipment.selectedProperty().addListener(new ChangeListener<Boolean>() { //TODO: Change when categories are implemented
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasSelected, Boolean isSelected) {
				if (isSelected && !tablesPopulated) {
					try {
						openEquipDB();
						ResultSet results = Main.queryDB(equipDB, "select * from Weapons;");
						while (results.next()) {
							weaponTableData.add(new Weapon(results.getString("Name"), results.getString("Cost"), results.getString("SDamage"),
												results.getString("MDamage"), results.getString("Critical"), results.getString("Range"),
												results.getString("Weight"), results.getString("Type"), results.getString("Special"),
												results.getString("Category")));
						}
						results = Main.queryDB(equipDB, "select * from Armor;");
						while (results.next()) {
							armorTableData.add(new Armor(results.getString("Name"), results.getString("Cost"), results.getString("Bonus"),
												results.getString("MaxDex"), results.getString("Penalty"), results.getString("ArcaneFail"),
												results.getString("Speed30"), results.getString("Speed20"), results.getString("Weight"),
												results.getString("Category")));
						}
						equipDB.close();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					tablesPopulated = true;
				}
			}
		});
		
		pp.setText(Integer.toString(totalPP));
		gp.setText(Integer.toString(totalGP));
		sp.setText(Integer.toString(totalSP));
		cp.setText(Integer.toString(totalCP));

		wtTotal.setText("0");
		wtTotal.setEditable(false);
		//weaponList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//weaponTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		removeWeapons.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            List<Weapon> items = weaponList.getSelectionModel().getSelectedItems();
	            if (!items.isEmpty()) {
	            	int removeIndex;
		            List<Integer> il = weaponList.getSelectionModel().getSelectedIndices();
		            
		            for (int i = (il.size() - 1); i >= 0; i--) {
		            	removeIndex = il.get(i);
		            	weight = items.get(i).getWeight();
		            	weaponList.getItems().remove(removeIndex);
		            	for (int j = 0; j < ModifiersTab.modTableData.size(); j++) {
		            		Adjustment a = ModifiersTab.modTableData.get(j);
		            		ShortWeapon weapon = OverviewTab.weaponOverviewData.get(removeIndex);
		            		if (a.getTarget().contains("Melee") || a.getTarget().contains("Ranged")) {
		            			a.getWeapons().remove(weapon);
		            		}
		            		if (a.getType().equals("Weapon")) {	//TODO: Always ignores one match if there are >1; doesn't seem to be seen by the loop at all
		            			if (a.getWeapon().equals(weapon)) {
		            				a.remove();
		            				ModifiersTab.modTableData.remove(a);
		            			}
		            		}
		            	}
		            }
		            addWealth();
		            removeWeight();
	            }
	        }
	    });
		
		addWeapons.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            List<Weapon> items = weaponTable.getSelectionModel().getSelectedItems();
	            if (!items.isEmpty()) {
		            for (int i = 0; i < items.size(); i++) {
	            		cost = items.get(i).getCost();
	            		weight = items.get(i).getWeight();
	            		checkWealth();
	            		if(!notEnoughGold){
	            			weaponList.getItems().add(items.get(i));
	            		}
		            }
		            if(!notEnoughGold){
		            	subtractWealth();
		            	addWeight();
		            } else {
		            	Stage dialogStage = new Stage();
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.setScene(new Scene(VBoxBuilder.create().
						children(new Text("You do not have enough wealth.")).
						alignment(Pos.CENTER).padding(new Insets(5)).build()));
						dialogStage.show();
		            }
	            }
	        }
	    });
		
		unequipWeapon.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				List<Weapon> items = equippedWeapons.getSelectionModel().getSelectedItems();
				
				if (!items.isEmpty()) {
	            	int removeIndex;
		            List<Integer> il = equippedWeapons.getSelectionModel().getSelectedIndices();
		            
		            for (int i = (il.size() - 1); i >= 0; i--) {
		            	removeIndex = il.get(i);
		            	
		            	weaponList.getItems().add(items.get(i));
		            	OverviewTab.weaponOverviewData.remove(removeIndex);
		            	equippedWeapons.getItems().remove(items.get(i));
		            }
				}
			}
		});
		
		equipWeapon.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				List<Weapon> items = weaponList.getSelectionModel().getSelectedItems();
				
				if (!items.isEmpty() && equippedWeapons.getItems().size() < 2) {
		            for (int i = 0; i < items.size(); i++) {
		            	equippedWeapons.getItems().add(items.get(i));
		            	
			            ArrayList<ShortWeapon> shortItems = new ArrayList<>();
			            for (Weapon w : items) {
			            	shortItems.add(new ShortWeapon(w.getName(), "", w.getCritical(), w.getMDamage(), "", w.getRange(), w.getType(), w.getCategory()));
			            }
						
		    			OverviewTab.weaponOverviewData.add(shortItems.get(i));
		    			
		    			for (Adjustment a : ModifiersTab.modTableData) {
		    				if (a.getTarget().contains("Melee") && //Adds melee weapons to melee adjustment weapons lists
		    						(shortItems.get(i).getCategory().contains("unarmed") || 
									 shortItems.get(i).getCategory().contains("melee"))) {
		    					a.getWeapons().add(shortItems.get(i));
		    				}
		    				if (a.getTarget().contains("Ranged") &&	//Adds ranged weapons to ranged adjustment weapons lists
		    						shortItems.get(i).getCategory().contains("ranged")) {
		    					a.getWeapons().add(shortItems.get(i));
		    				}
		    			}
		    			
		    			weaponList.getItems().remove(items.get(i));
		            }
		            
		            OverviewTab.updateAttackBonus();
				}
			}
		});
		
		TableColumn<Weapon, String> weaponName = (TableColumn<Weapon, String>) weaponTable.getColumns().get(0);
		TableColumn<Weapon, String> weaponCost = (TableColumn<Weapon, String>) weaponTable.getColumns().get(1);
		TableColumn<Weapon, String> weaponSDamage = (TableColumn<Weapon, String>) weaponTable.getColumns().get(2);
		TableColumn<Weapon, String> weaponMDamage = (TableColumn<Weapon, String>) weaponTable.getColumns().get(3);
		TableColumn<Weapon, String> weaponCritical = (TableColumn<Weapon, String>) weaponTable.getColumns().get(4);
		TableColumn<Weapon, String> weaponRange = (TableColumn<Weapon, String>) weaponTable.getColumns().get(5);
		TableColumn<Weapon, String> weaponWeight = (TableColumn<Weapon, String>) weaponTable.getColumns().get(6);
		TableColumn<Weapon, String> weaponType = (TableColumn<Weapon, String>) weaponTable.getColumns().get(7);
		TableColumn<Weapon, String> weaponSpecial = (TableColumn<Weapon, String>) weaponTable.getColumns().get(8);
		
		weaponName.setCellValueFactory(new PropertyValueFactory<Weapon, String>("name"));
		weaponCost.setCellValueFactory(new PropertyValueFactory<Weapon, String>("cost"));
		weaponSDamage.setCellValueFactory(new PropertyValueFactory<Weapon, String>("sdamage"));
		weaponMDamage.setCellValueFactory(new PropertyValueFactory<Weapon, String>("mdamage"));
		weaponCritical.setCellValueFactory(new PropertyValueFactory<Weapon, String>("critical"));
		weaponRange.setCellValueFactory(new PropertyValueFactory<Weapon, String>("range"));
		weaponWeight.setCellValueFactory(new PropertyValueFactory<Weapon, String>("weight"));
		weaponType.setCellValueFactory(new PropertyValueFactory<Weapon, String>("type"));
		weaponSpecial.setCellValueFactory(new PropertyValueFactory<Weapon, String>("special"));
		
		weaponTable.setItems(weaponTableData);
		/* TODO: Resurrect when equipment details are implemented
		weaponTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent click) {
				if (click.getButton().equals(MouseButton.PRIMARY)
						&& click.getClickCount() == 2) {
					
				}
			}
		}); */
		
		
		//armorList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//armorTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		removeArmor.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            List<Armor> items = armorList.getSelectionModel().getSelectedItems();
	            if (!items.isEmpty()) {
	            	int removeIndex;
		            List<Integer> il = armorList.getSelectionModel().getSelectedIndices();
		            
		            for (int i = (il.size() - 1); i >= 0; i--) {
		            	removeIndex = il.get(i);
		            	weight = items.get(i).getWeight();
		            	armorList.getItems().remove(removeIndex);
		            }
		            addWealth();
		            removeWeight();
	            }
	        }
	    });
		
		addArmor.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            List<Armor> items = armorTable.getSelectionModel().getSelectedItems();
	            if (!items.isEmpty()) {
		            for (Armor armor : items) {
	            		cost = armor.getCost();
	            		weight = armor.getWeight();
	            		checkWealth();
	            		if(!notEnoughGold)
	            			armorList.getItems().add(armor);
		            }
		            if(!notEnoughGold) {
		            	subtractWealth();
		            	addWeight();
		            } else {
		            	Stage dialogStage = new Stage();
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.setScene(new Scene(VBoxBuilder.create().
						children(new Text("You do not have enough wealth.")).
						alignment(Pos.CENTER).padding(new Insets(5)).build()));
						dialogStage.show();
		            }
	            }
	        }
	    });
		
		unequipArmor.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				List<Armor> items = equippedArmor.getSelectionModel().getSelectedItems();
				
				if (!items.isEmpty()) {
					for (Armor armor : items) {
						if (armor.getCategory().contains("shield")) {
							OverviewTab.acShield.setText("0");
						} else if (armor.getCategory().contains("extras")) {
							
						} else {
							OverviewTab.acArmor.setText("0");
							OverviewTab.armorSpeed.setText("");
						}
						
						armorList.getItems().add(armor);
						equippedArmor.getItems().remove(armor);
						
						OverviewTab.updateDex(); //Update max dex bonus
					}
				}
			}
		});
		
		equipArmor.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				List<Armor> items = armorList.getSelectionModel().getSelectedItems();
				List<Armor> equipped = equippedArmor.getItems();
				boolean hasArmor = false, hasShield = false;
				
				if (!equipped.isEmpty()) {
					for (Armor armor : equipped) {
						if (armor.getCategory().contains("shield")) {
							hasShield = true;
						} else if (armor.getCategory().contains("extras")) {
							
						} else {
							hasArmor = true;
						}
					}
				}
				
				if (!items.isEmpty()) {
					for (Armor armor : items) {
						if (armor.getCategory().contains("shield") && !hasShield) {
							equippedArmor.getItems().add(armor);
							
	            			String shieldAC = armor.getBonus();
	            			shieldAC = shieldAC.replace("+", "");
	            			OverviewTab.acShield.setText(shieldAC);
	            			
	            			armorList.getItems().remove(armor);
	            		} else if (armor.getCategory().contains("extras")) {
	            			equippedArmor.getItems().add(armor);
	            			armorList.getItems().remove(armor);
	            		} else if (!hasArmor) {
	            			equippedArmor.getItems().add(armor);
	            			
	            			String armorAC = armor.getBonus();
	            			armorAC = armorAC.replace("+", "");
	            			OverviewTab.acArmor.setText(armorAC);
	            			
	            			switch (Character.raceMap.get(OverviewTab.race.getValue()).speed) {
		            			case 30:
		            				OverviewTab.armorSpeed.setText(armor.getSpeed30().replace(" ft.", ""));
		            				break;
		            			case 20:
		            				OverviewTab.armorSpeed.setText(armor.getSpeed20().replace(" ft.", ""));
		            				break;
	            			}
	            			
	            			armorList.getItems().remove(armor);
	            		}

        				OverviewTab.updateDex(); //Update max dex bonus
					}
				}
			}
		});
		
		TableColumn<Armor, String> armorName = (TableColumn<Armor, String>) armorTable.getColumns().get(0);
		TableColumn<Armor, String> armorCost = (TableColumn<Armor, String>) armorTable.getColumns().get(1);
		TableColumn<Armor, String> armorBonus = (TableColumn<Armor, String>) armorTable.getColumns().get(2);
		TableColumn<Armor, String> armorMaxDex = (TableColumn<Armor, String>) armorTable.getColumns().get(3);
		TableColumn<Armor, String> armorPenalty = (TableColumn<Armor, String>) armorTable.getColumns().get(4);
		TableColumn<Armor, String> armorFailure = (TableColumn<Armor, String>) armorTable.getColumns().get(5);
		TableColumn<Armor, String> armorSpeed30 = (TableColumn<Armor, String>) armorTable.getColumns().get(6);
		TableColumn<Armor, String> armorSpeed20 = (TableColumn<Armor, String>) armorTable.getColumns().get(7);
		TableColumn<Armor, String> armorWeight = (TableColumn<Armor, String>) armorTable.getColumns().get(8);
		
		armorName.setCellValueFactory(new PropertyValueFactory<Armor, String>("name"));
		armorCost.setCellValueFactory(new PropertyValueFactory<Armor, String>("cost"));
		armorBonus.setCellValueFactory(new PropertyValueFactory<Armor, String>("bonus"));
		armorMaxDex.setCellValueFactory(new PropertyValueFactory<Armor, String>("maxdex"));
		armorPenalty.setCellValueFactory(new PropertyValueFactory<Armor, String>("penalty"));
		armorFailure.setCellValueFactory(new PropertyValueFactory<Armor, String>("failure"));
		armorSpeed30.setCellValueFactory(new PropertyValueFactory<Armor, String>("speed30"));
		armorSpeed20.setCellValueFactory(new PropertyValueFactory<Armor, String>("speed20"));
		armorWeight.setCellValueFactory(new PropertyValueFactory<Armor, String>("weight"));
		
		armorTable.setItems(armorTableData);
		/* TODO: Resurrect when equipment details are implemented
		armorTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent click) {
        		if (click.getButton().equals(MouseButton.PRIMARY)
        				&& click.getClickCount() == 2) {
        			
        		}
        	}
        }); */
		
		//For the weight fields underneath weapons and armor:
		TextField[] customItemWeights = new TextField[26];
		ChangeListener<String> weightListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, 
					String oldVal, String newVal) {
				oldVal = Calculations.filterDouble(oldVal, 0);
				newVal = Calculations.filterDouble(newVal, 0);
				totalWeightDouble = getTotalWeight() - Double.parseDouble(oldVal);
				totalWeightDouble += Double.parseDouble(newVal);
				totalWeight = Double.toString(totalWeightDouble);
				wtTotal.setText(totalWeight);
			}
		};
		
		for(int i = 0; i < 26; i++) {
			customItemWeights[i] = (TextField) scene.lookup("#wt"+(i+1));
			customItemWeights[i].textProperty().addListener(weightListener);
		}
	}
	

	
	void openEquipDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		if (Main.release) {
			equipDB = DriverManager.getConnection("jdbc:h2:./Databases/PathfinderEquipment", "sa", "");
		} else {
			equipDB = DriverManager.getConnection("jdbc:h2:~/Databases/PathfinderEquipment", "sa", "");
		}
	}
	
	
	void subtractWealth(){
		/*Copper piece = 1/10 Silver piece (1 cent)
		 * Silver piece = 1/10 Gold piece (10 cents)
		 * Gold piece = 1/10 Platinum piece
		 */
		switch(type){
			case "pp":
				totalPP -= costInt;
				break;
			case "gp":
				if(costInt > totalGP){
					totalPP -= 1;
					totalGP += 10;
				}
				totalGP -= costInt;
				break;
			case "sp":
				if(costInt > totalSP){
					totalGP -= 1;
					totalSP += 10;
				}
				totalSP -= costInt;
				break;
			case "cp":
				if(costInt > totalCP){
					totalSP -= 1;
					totalCP += 10;
				}
				totalCP -= costInt;
				break;
		}
		pp.setText(Integer.toString(totalPP));
		gp.setText(Integer.toString(totalGP));
		sp.setText(Integer.toString(totalSP));
		cp.setText(Integer.toString(totalCP));
	}
	
	void addWealth(){
		/*Copper piece = 1/10 Silver piece (1 cent)
		 * Silver piece = 1/10 Gold piece (10 cents)
		 * Gold piece = 1/10 Platinum piece
		 */
		switch(type){
			case "—":
				 
				break;
			case "pp":
				totalPP += costInt;
				break;
			case "gp":
				totalGP += costInt;
				break;
			case "sp":
				if(totalSP >= 10){
					totalGP += 1;
					totalSP -= 10;
				}
				totalSP += costInt;
				break;
			case "cp":
				if(totalCP >= 10){
					totalSP += 1;
					totalCP -= 10;
				}
				totalCP += costInt;
				break;
		}
		pp.setText(Integer.toString(totalPP));
		gp.setText(Integer.toString(totalGP));
		sp.setText(Integer.toString(totalSP));
		cp.setText(Integer.toString(totalCP));
	}
	
	void checkWealth(){
		if(!cost.equals("—")) {
			type = cost.split(" ")[1];
			cost = cost.split(" ")[0];
			costInt = Integer.parseInt(cost);
		} else {
			costInt = 0;
			type = "—";
		}
		/*Copper piece = 1/10 Silver piece (1 cent)
		 * Silver piece = 1/10 Gold piece (10 cents)
		 * Gold piece = 1/10 Platinum piece
		 */
		switch(type){
			case "—":
				notEnoughGold = false;
				break;
			case "pp":
				if((costInt > totalPP)){
					notEnoughGold = true;
				} else notEnoughGold = false;
				break;
			case "gp":
				if((costInt > totalGP) && totalPP == 0){
					notEnoughGold = true;
				} else notEnoughGold = false;
				break;
			case "sp":
				if((costInt > totalSP) && totalGP == 0){
					notEnoughGold = true;
				} else notEnoughGold = false;
				break;
			case "cp":
				if((costInt > totalCP) && totalSP == 0){
					notEnoughGold = true;
				} else notEnoughGold = false;
				break;
		}
	}
	
	void addWeight() {
		if(!weight.equals("—")) 
			weight = weight.split(" ")[0];
		else
			weight = "0";
		if(weight.equals("1/2"))
			weightDouble = 0.5;
		else
			weightDouble = Double.parseDouble(weight);
		
		totalWeightDouble = getTotalWeight() + weightDouble;
		wtTotal.setText(Double.toString(totalWeightDouble));
	}
	
	void removeWeight() {
		if(!weight.equals("—")) 
			weight = weight.split(" ")[0];
		else
			weight = "0";
		if(weight.equals("1/2"))
			weightDouble = 0.5;
		else
			weightDouble = Double.parseDouble(weight);
		
		totalWeightDouble = getTotalWeight() - weightDouble;
		wtTotal.setText(Double.toString(totalWeightDouble));
	}
	
	double getTotalWeight(){
		return Double.parseDouble(wtTotal.getText().split(" ")[0]);
	}
	
	static void calcCarryingCapacity(){
		String light="0", medium="0", heavy="0";
		int overHead=0, offGround=0, drag=0;
		switch(OverviewTab.STR.score){
			case 1: light = "3"; medium = "4-6"; heavy = "7-10";
				break;
			case 2: light = "6"; medium = "7-13"; heavy = "14-20";
				break;
			case 3: light = "10"; medium = "11-20"; heavy = "21-30";
				break;
			case 4: light = "13"; medium = "14-26"; heavy = "27-40";
				break;
			case 5: light = "16"; medium = "17-33"; heavy = "34-50";
				break;
			case 6: light = "20"; medium = "21-40"; heavy = "41-60";
				break;
			case 7: light = "23"; medium = "24-46"; heavy = "47-70";
				break;
			case 8: light = "36"; medium = "27-53"; heavy = "54-80";
				break;
			case 9: light = "30"; medium = "31-60"; heavy = "61-90";
				break;
			case 10: light = "33"; medium = "34-66"; heavy = "67-100";
				break;
			case 11: light = "38"; medium = "39-76"; heavy = "77-115";
				break;
			case 12: light = "43"; medium = "44-86"; heavy = "87-130";
				break;
			case 13: light = "50"; medium = "51-100"; heavy = "101-150";
				break;
			case 14: light = "58"; medium = "59-116"; heavy = "117-175";
				break;
			case 15: light = "66"; medium = "67-133"; heavy = "134-200";
				break;
			case 16: light = "76"; medium = "77-153"; heavy = "154-230";
				break;
			case 17: light = "86"; medium = "87-173"; heavy = "174-260";
				break;
			case 18: light = "100"; medium = "101-200"; heavy = "201-300";
				break;
			case 19: light = "116"; medium = "117-233"; heavy = "234-350";
				break;
			case 20: light = "133"; medium = "134-266"; heavy = "267-400";
				break;
			case 21: light = "153"; medium = "154-306"; heavy = "307-460";
				break;
			case 22: light = "173"; medium = "174-346"; heavy = "347-520";
				break;
			case 23: light = "200"; medium = "201-400"; heavy = "401-600";
				break;
			case 24: light = "233"; medium = "234-466"; heavy = "467-700";
				break;
			case 25: light = "266"; medium = "267-533"; heavy = "534-800";
				break;
			case 26: light = "306"; medium = "307-613"; heavy = "614-920";
				break;
			case 27: light = "346"; medium = "347-693"; heavy = "694-1,040";
				break;
			case 28: light = "400"; medium = "401-800"; heavy = "801-1,200";
				break;
			case 29: light = "466"; medium = "467-933"; heavy = "934-1400";
				break;
		}
		overHead = Integer.parseInt(heavy.split("-")[1]);
		offGround = overHead * 2;
		drag = overHead * 5;
		liftOver.setText(Integer.toString(overHead) + " lbs.");
		liftOff.setText(Integer.toString(offGround) + " lbs.");
		dragPush.setText(Integer.toString(drag) + " lbs.");
		
		light += " lbs. or less";
		medium += " lbs.";
		heavy += " lbs.";
		lightLoad.setText(light);
		medLoad.setText(medium);
		heavyLoad.setText(heavy);
	}
}
