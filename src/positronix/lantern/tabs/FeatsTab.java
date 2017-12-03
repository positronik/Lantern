package positronix.lantern.tabs;

import positronix.lantern.*;

import java.util.*;
import java.sql.*;

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
public class FeatsTab {
	static Scene scene = Main.scene;
	
	Connection featsDB;
	
	public static int featPoints = 0;
	public static boolean canChangePoints = false;
	
	public static TextField featsAvailable = (TextField) scene.lookup("#featsAvailable");
	public static Button removeFeats = (Button) scene.lookup("#removeFeats");
	public static Button addFeats = (Button) scene.lookup("#addFeats");
	//Button featDetails = (Button) scene.lookup("#featDetails");
	
	public static ToggleGroup featGroup = new ToggleGroup();
	public static RadioButton generalFeats = (RadioButton) scene.lookup("#generalFeats");
	public static RadioButton combatFeats = (RadioButton) scene.lookup("#combatFeats");
	public static RadioButton metamagicFeats = (RadioButton) scene.lookup("#metamagicFeats");
	public static RadioButton itemFeats = (RadioButton) scene.lookup("#itemFeats");
	
	public static ListView<Feat> featsList = (ListView<Feat>) scene.lookup("#featsList");
	public static TableView<Feat> featsTable = (TableView<Feat>) scene.lookup("#featsTable");
	public static ObservableList<Feat> featsTableData = FXCollections.observableArrayList();
	
	
	
	public FeatsTab() {
		featsAvailable.setText(Integer.toString(featPoints));
		featsAvailable.setEditable(false);
		featsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		featsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		removeFeats.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	int removeIndex;
	            List<Integer> il = featsList.getSelectionModel().getSelectedIndices();
	            
	            if (!il.isEmpty()) {
	            	for (int i = (il.size() - 1); i >= 0; i--) {
	            		removeIndex = il.get(i);
	            		featsList.getItems().remove(removeIndex);
	            		featPoints++;
		            	featsAvailable.setText(Integer.toString(featPoints));
	            	}
	            }
	        }
	    });
		
		addFeats.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	List<Feat> items = featsTable.getSelectionModel().getSelectedItems();
	        	if(featPoints > 0 && !items.isEmpty() && items.size() <= featPoints){
	        		for (int i = 0; i < items.size(); i++) {
	        			if (!featsList.getItems().contains(items.get(i))) {
	        				featsList.getItems().add(items.get(i));
	        				featPoints--;
	    	        		featsAvailable.setText(Integer.toString(featPoints));
	        			}
	        		}
	        	}
	        }
	    });
		
		
		generalFeats.setToggleGroup(featGroup);
		combatFeats.setToggleGroup(featGroup);
		metamagicFeats.setToggleGroup(featGroup);
		itemFeats.setToggleGroup(featGroup);
		
		
		TableColumn<Feat, String> featName = (TableColumn<Feat, String>) featsTable.getColumns().get(0);
		TableColumn<Feat, String> featPrereq = (TableColumn<Feat, String>) featsTable.getColumns().get(1);
		TableColumn<Feat, String> featBenefit = (TableColumn<Feat, String>) featsTable.getColumns().get(2);
		TableColumn<Feat, String> mmLvlInc = new TableColumn<>();
		mmLvlInc.setText("Level Increase");
		mmLvlInc.setPrefWidth(105);
		
		featName.setCellValueFactory(new PropertyValueFactory<Feat, String>("name"));
		featPrereq.setCellValueFactory(new PropertyValueFactory<Feat, String>("prereq"));
		featBenefit.setCellValueFactory(new PropertyValueFactory<Feat, String>("benefit"));
		mmLvlInc.setCellValueFactory(new PropertyValueFactory<Feat, String>("levelinc"));
		
		featsTable.setItems(featsTableData);
		
		generalFeats.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasSelected, Boolean isSelected) {
				if (isSelected) {
					try {
						openFeatsDB();
						ResultSet results = Main.queryDB(featsDB, "select * from General order by Name;");
						while (results.next()) {
							featsTableData.add(new Feat(results.getString("Name"), results.getString("Prereq"), results.getString("Benefit")));
						}
						featsDB.close();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (wasSelected) {
					featsTableData.clear();
				}
			}
		});
		
		combatFeats.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasSelected, Boolean isSelected) {
				if (isSelected) {
					try {
						openFeatsDB();
						ResultSet results = Main.queryDB(featsDB, "select * from Combat order by Name;");
						while (results.next()) {
							featsTableData.add(new Feat(results.getString("Name"), results.getString("Prereq"), results.getString("Benefit")));
						}
						featsDB.close();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (wasSelected) {
					featsTableData.clear();
				}
			}
		});
		
		metamagicFeats.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasSelected, Boolean isSelected) {
				if (isSelected) {
					try {
						openFeatsDB();
						ResultSet results = Main.queryDB(featsDB, "select * from Metamagic order by LevelInc, Name;");
						featPrereq.setPrefWidth(205);
						featBenefit.setPrefWidth(240);
						featsTable.getColumns().add(mmLvlInc);
						while (results.next()) {
							featsTableData.add(new Feat(results.getString("Name"), results.getString("Prereq"), results.getString("Benefit"), results.getString("LevelInc")));
						}
						featsDB.close();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (wasSelected) {
					featsTableData.clear();
					featsTable.getColumns().remove(mmLvlInc);
					featPrereq.setPrefWidth(250);
					featBenefit.setPrefWidth(300);
				}
			}
		});
		
		itemFeats.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean wasSelected, Boolean isSelected) {
				if (isSelected) {
					try {
						openFeatsDB();
						ResultSet results = Main.queryDB(featsDB, "select * from ItemCreation order by Name;");
						while (results.next()) {
							featsTableData.add(new Feat(results.getString("Name"), results.getString("Prereq"), results.getString("Benefit")));
						}
						featsDB.close();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (wasSelected) {
					featsTableData.clear();
				}
			}
		});
		/* TODO: Resurrect when feat details are implemented
		featsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent click) {
        		if (click.getButton().equals(MouseButton.PRIMARY)
        				&& click.getClickCount() == 2) {
        			
        		}
        	}
        }); */
		
		/*
		featGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
        		
            }
        }); */
	}
	
	void openFeatsDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		if (Main.release) {
			featsDB = DriverManager.getConnection("jdbc:h2:./Databases/PathfinderFeats", "sa", "");
		} else {
			featsDB = DriverManager.getConnection("jdbc:h2:~/Databases/PathfinderFeats", "sa", "");
		}
	}
	
	public static void updateFeats(){
		
	}
}
