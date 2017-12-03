package positronix.lantern;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.sql.*;

import positronix.lantern.tabs.*;
import positronix.lantern.tabs.Dialog; //Differentiate from other Dialog classes

import com.sun.javafx.beans.IDProperty;
import com.sun.javafx.logging.Logger;


//import javafx.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/*
 * #TODO
 * Finish programming menu items (plus ContinueConfirm) [in progress; only need New]
 * Design proper dialog boxes for invalid save file and insufficient wealth
 * Change gear/weight listener to focusedProperty
 * Equipment tables don't account for multiple selection
 * Differentiate among knowing and preparing spellcasters
 * Prevent user adding more spells than they know
 * When manual mode is implemented, change when editable-dependent listeners are added
 * Equipment categories
 * Notes tab?
 * Consider letting users enter, e.g., +2 or -2 in numeric fields to increment or
 * 		decrement them by that amount, roll20 style
 * 
 * Nitpicks
 * If a save file is made, then Save As is selected and canceled, choosing the normal Save
 * 		command will then open the Save dialog rather than automatically saving to the same file
 */

public class Main extends Application  {
	static TextField tName, tAlign, tPlayer, tLevel, tDeity, tHomeland, 
			         tSize, tGender, tAge, tHeight, tWeight, tHair, tEyes;
	static ChoiceBox<String> tRace;

	static List<Control> dText = new ArrayList<Control>();

	
	public static Scene scene;
	
	public static TabPane tabPane;
	
	//Dialog boxes
	public static Scene rollerScene, lookupScene, bonusScene, generatorScene, costsScene,
						increaseScene, continueScene, levelErrorScene, raceConfirmScene,
						modSelectorScene, modWeaponSelectorScene, spellDetailsScene, spellKeyScene;
	static File file;
	final static FileChooser fileChooser = new FileChooser();
	static List<Object> info = new ArrayList<Object>();
	static List<Object> infoObjects = new ArrayList<Object>();
	
	public static Stage primaryStage;
	
	public static boolean release = false;
	
	public static boolean cont = false;
	
	static Tooltip saved = new Tooltip("Saved.");
	
	public void start(Stage primaryStage) throws IOException {
		if (release) {
			FileOutputStream log = new FileOutputStream("log.txt");
			System.setErr(new PrintStream(log));
		}
		
		this.primaryStage = primaryStage;
		Pane parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
        scene = new Scene(parent);
        scene.getStylesheets().add("Default.css");
        primaryStage.setTitle("Lantern");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(933);
        primaryStage.setHeight(848);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	public void handle(WindowEvent e) {
        		Dialog.continueStage.showAndWait();
        		if (cont) {
        			Platform.exit();
        		} else {
        			e.consume();
        		}
        	}
        });
        
        tabPane = (TabPane) scene.lookup("#tabPane");
        
        rollerScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/DiceRoller.fxml")));
        lookupScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/Lookup.fxml")));
        generatorScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/AbilityGenerator.fxml")));
        costsScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/AbilityGeneratorCosts.fxml")));
        bonusScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/AbilityBonus.fxml")));
        increaseScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/AbilityIncrease.fxml")));
        continueScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/ContinueConfirm.fxml")));
        levelErrorScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/LevelError.fxml")));
        modSelectorScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/ModSelector.fxml")));
        modWeaponSelectorScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/ModWeaponSelector.fxml")));
        raceConfirmScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/RaceConfirm.fxml")));
        spellDetailsScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/SpellDetails.fxml")));
        spellKeyScene = new Scene(FXMLLoader.load(getClass().getResource("Dialogs/SpellDetailsKey.fxml")));
        
        OverviewTab overviewTab = new OverviewTab();
        SkillTab skillTab = new SkillTab();
        ClassesTab classesTab = new ClassesTab();
        FeatsTab featsTab = new FeatsTab();
        EquipmentTab equipmentTab = new EquipmentTab();
        SpellsTab spellTab = new SpellsTab();
        ModifiersTab modTab = new ModifiersTab();
        
        Dialog dialog = new Dialog();
        
        MenuBar menuBar = (MenuBar) scene.lookup("#menuBar");
        
        Menu fileMenu = menuBar.getMenus().get(0);
        MenuItem knew = fileMenu.getItems().get(0);
        MenuItem load = fileMenu.getItems().get(1);
        MenuItem save = fileMenu.getItems().get(2);
        MenuItem saveAs = fileMenu.getItems().get(3);
        MenuItem close = fileMenu.getItems().get(4);
        
        knew.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.continueStage.showAndWait();
        		if (cont) {
        			
        		}
            }
        });
        
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                saveFile();
            }
        });
        
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                saveFileAs();
            }
        });
        
        load.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.continueStage.showAndWait();
        		if (cont) {
        			loadFile();
        		}
            }
        });
        
        close.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Dialog.continueStage.showAndWait();
        		if (cont) {
        			Platform.exit();
        		}
            }
        });
        
        Menu editMenu = menuBar.getMenus().get(1);
        for (MenuItem m : editMenu.getItems()) {
        	m.setDisable(true);
        }
        
        Menu window = menuBar.getMenus().get(2);
        MenuItem diceRollerWindow = window.getItems().get(7);
        
        for (int i = 0; i < 7; i++) {
        	final int j = i;	//If i was used inside the listener, threw error "Local variable i defined in an enclosing scope must be final or effectively final"
	        window.getItems().get(i).setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent e) {
	            	tabPane.getSelectionModel().select(j);
	            }
	        });
        }
        
        diceRollerWindow.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Dialog.rollerStage.show();
                Dialog.rollerStage.requestFocus();
            }
        });
        
        Menu lookup = menuBar.getMenus().get(3);
        MenuItem lookupFeats = lookup.getItems().get(0);
        MenuItem lookupEquipment = lookup.getItems().get(1);
        MenuItem lookupSpells = lookup.getItems().get(2);
        
        lookupFeats.setDisable(true); //TODO: Remove when feat details implemented
        lookupFeats.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		Dialog.lookupStage.show();
        		Dialog.lookupStage.requestFocus();
        		Dialog.lookupFeats.setSelected(true);
        	}
        });
        
        lookupEquipment.setDisable(true); //TODO: Remove when equipment details implemented
        lookupEquipment.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		Dialog.lookupStage.show();
        		Dialog.lookupStage.requestFocus();
        		Dialog.lookupEquipment.setSelected(true);
        	}
        });
        
        lookupSpells.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		Dialog.lookupStage.show();
        		Dialog.lookupStage.requestFocus();
        		Dialog.lookupSpells.setSelected(true);
        	}
        });
        
        Menu help = menuBar.getMenus().get(4);
        for (MenuItem m : help.getItems()) {
        	m.setDisable(true);
        }
        
        saved.setAutoHide(true);
        
        
        dText.add(tName = (TextField) scene.lookup("#name"));
        dText.add(tAlign = (TextField) scene.lookup("#align"));
        dText.add(tPlayer = (TextField) scene.lookup("#player"));
        dText.add(tLevel = (TextField) scene.lookup("#level"));
        dText.add(tDeity = (TextField) scene.lookup("#deity"));
        dText.add(tHomeland = (TextField) scene.lookup("#homeland"));
        dText.add(tRace = (ChoiceBox) scene.lookup("#race"));
        dText.add(tSize = (TextField) scene.lookup("#size"));
        dText.add(tGender = (TextField) scene.lookup("#gender"));
        dText.add(tAge = (TextField) scene.lookup("#age"));
        dText.add(tHeight = (TextField) scene.lookup("#height"));
        dText.add(tWeight = (TextField) scene.lookup("#weight"));
        dText.add(tHair = (TextField) scene.lookup("#hair"));
        dText.add(tEyes = (TextField) scene.lookup("#eyes"));
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

	
	/*Prints arrayList to a save file*/
	public static void saveFile(){
		info.clear();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Lantern characters (*.lantern)", "*.lantern");
		FileChooser.ExtensionFilter extFilterTxt = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilterTxt);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save");
        
        //Show save file dialog
        if(file==null){ file = fileChooser.showSaveDialog(primaryStage); }
		
		if(file != null){
			saveFile2(tabPane);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
				for(int i = 0; i < info.size(); i++){
					bw.write(info.get(i).toString());
					bw.newLine();
				}
				bw.flush();
				bw.close();
				saved.show(scene.getWindow());
			} catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	public static void saveFileAs(){
		info.clear();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Lantern characters (*.lantern)", "*.lantern");
		FileChooser.ExtensionFilter extFilterTxt = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilterTxt);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save as");
        
        //Show save file dialog
        file = fileChooser.showSaveDialog(primaryStage); 
		
		if(file != null){
			saveFile2(tabPane);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
				for(int i = 0; i < info.size(); i++){
					bw.write(info.get(i).toString());
					bw.newLine();
				}
				bw.flush();
				bw.close();
				saved.show(scene.getWindow());
			} catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
	}
	/*Gathers info from program and adds to an ArrayList*/
	public static void saveFile2(Node node){		
		String s = "";

	    if(node instanceof TabPane){
			TabPane tabPane = (TabPane) node;
			for (Tab tab : tabPane.getTabs()) {
				saveFile2(tab.getContent());
			}
		} else if (node instanceof Pane) {
			Pane pane = (Pane) node;
			for (Node n : pane.getChildren()) {
				saveFile2(n);
			}
		} else if (node instanceof TitledPane) {
			TitledPane titledPane = (TitledPane) node;
			saveFile2(titledPane.getContent());
		} else if (node instanceof ScrollPane) {
			ScrollPane scrollPane = (ScrollPane) node;
			saveFile2(scrollPane.getContent());
		} else if (node instanceof TextField) {
			TextField textField = (TextField) node;
			s = textField.getText();
			info.add(s);
		} else if (node instanceof TextArea) {
			TextArea textArea = (TextArea) node;
			s = textArea.getText();
			if (s.contains("<br>")) {
				s = s.replace("<br>", "");
			}
			if (s.contains("\n")) {
				s = s.replace("\n", "<br>");
			}
			info.add(s);
		} else if (node instanceof ChoiceBox) {
			ChoiceBox choiceBox = (ChoiceBox) node;
			s = Integer.toString(choiceBox.getSelectionModel().getSelectedIndex());
			info.add(s);
		} else if (node instanceof ComboBox) {
			ComboBox comboBox = (ComboBox) node;
			s = comboBox.getValue().toString();
			info.add(s);
		} else if (node instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) node;
			s = "" + checkBox.isSelected();
			info.add(s);
		} else if (node instanceof Text) {
			try {
				if (!node.getId().equals(null)) {
					Text text = (Text) node;
					s = text.getText();
					info.add(s);
				}
			} catch (NullPointerException e) {
				//Do nothing if getId() is null
			}
		} else if (node instanceof ListView) {
			ListView listView = (ListView) node;
			List<String> list = new ArrayList<>();
			for (Object o : listView.getItems()) {
				list.add(o.toString());
			}
			s = "";
			for (int i = 0; i < list.size(); i++) {
				s += list.get(i);
				if (i != list.size() - 1) {
					s += "; ";
				}
			}

			info.add(s);
		} else if (node instanceof Label || node instanceof Button ||
				node instanceof RadioButton || node instanceof TableView) {
			//Do nothing
		} else {
			//System.out.println(node.getClass().getSimpleName() + " " + node.getId());
		}
	}
	private static void configureFileChooser(final FileChooser fileChooser){
		fileChooser.setTitle("Choose a character");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Lantern characters (*.lantern)", "*.lantern");
		FileChooser.ExtensionFilter extFilterTxt = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilterTxt);
	}
	private Desktop desktop = Desktop.getDesktop();
	
	public static void loadFile(){
		info.clear();
		configureFileChooser(fileChooser);
		file = fileChooser.showOpenDialog(primaryStage);
		if(file != null){
			try (Scanner sc = new Scanner(file)) {
				String s;
				int i = 0;
				while(sc.hasNextLine()) {
					s = sc.nextLine();
					if (s != null) {
						info.add(s);
						i++;
					} else {
						s = "";
					}
				}
			} catch(IOException ex) {
				System.out.println("Error reading from file.");
				ex.printStackTrace();
			}
			
			loadFile2(tabPane);
			String s = " ";
			if(infoObjects.size() != info.size()) {
				Stage dialogStage = new Stage();
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.setScene(new Scene(VBoxBuilder.create().
				    children(new Text("Invalid save file.")).
				    alignment(Pos.CENTER).padding(new Insets(5)).build()));
				dialogStage.show();
			} else {
				for(int i = 0; i < info.size(); i++){
					s = info.get(i).toString();
					try {
						if(infoObjects.get(i) instanceof TextField){
							((TextField) infoObjects.get(i)).setText(s);
						} else if(infoObjects.get(i) instanceof TextArea){
							((TextArea) infoObjects.get(i)).setText(s.replace("<br>", "\n"));
						} else if(infoObjects.get(i) instanceof ComboBox){
							((ComboBox) infoObjects.get(i)).setValue(s);
						} else if(infoObjects.get(i) instanceof ChoiceBox){
							ChoiceBox<String> cb = (ChoiceBox<String>) infoObjects.get(i);
							if (!s.equals("-1") && cb.getId().equals("race")) {
								OverviewTab.firstTime = false;
							}
							cb.getSelectionModel().select(Integer.parseInt(s));
						} else if(infoObjects.get(i) instanceof CheckBox){
							((CheckBox) infoObjects.get(i)).setSelected(Boolean.valueOf(s));
						} else if(infoObjects.get(i) instanceof Text){
							((Text) infoObjects.get(i)).setText(s);
						} else if (infoObjects.get(i) instanceof ListView) {
							ObservableList<String> list = FXCollections.observableArrayList();
							list.clear();
							String[] substring = s.split("; ");
							for(int j = 0; j < substring.length; j++){
								list.add(substring[j]);
							}
						//Doesn't add the first entry if it's blank so that the file loads correctly
							if(!list.get(0).equals("") ){
								((ListView) infoObjects.get(i)).setItems(list);
							} 
						}	
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}//END OF FOR LOOP
			 }
		}//END OF FIRST IF STATEMENT
		//System.out.println(info.size() +" "+infoObjects.size());
		loadValues();
	}
	
	/*public static void newFile() {
		String file = "";
		
		info.clear();
		loadFile2(tabPane);
		
		for (Object o : infoObjects) {
			file += "\n";
		}
		
		try (Scanner sc = new Scanner(file)) {
			String s;
			int i = 0;
			while(sc.hasNextLine()) {
				s = sc.nextLine();
				if (s != null) {
					info.add(s);
					i++;
				} else {
					s = "";
				}
			}
		}
		
		String s = " ";
		if (infoObjects.size() != info.size()) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setScene(new Scene(VBoxBuilder.create().
			    children(new Text("Invalid save file.")).
			    alignment(Pos.CENTER).padding(new Insets(5)).build()));
			dialogStage.show();
		} else {
			for(int i = 0; i < info.size(); i++){
				s = info.get(i).toString();
				try {
					if(infoObjects.get(i) instanceof TextField){
						((TextField) infoObjects.get(i)).setText(s);
					} else if(infoObjects.get(i) instanceof TextArea){
						((TextArea) infoObjects.get(i)).setText(s.replace("<br>", "\n"));
					} else if(infoObjects.get(i) instanceof ComboBox){
						((ComboBox) infoObjects.get(i)).setValue(s);
					} else if(infoObjects.get(i) instanceof ChoiceBox){
						ChoiceBox<String> cb = (ChoiceBox<String>) infoObjects.get(i);
						if (!s.equals("-1") && cb.getId().equals("race")) {
							OverviewTab.firstTime = false;
						}
						cb.getSelectionModel().select(Integer.parseInt(s));
					} else if(infoObjects.get(i) instanceof CheckBox){
						((CheckBox) infoObjects.get(i)).setSelected(Boolean.valueOf(s));
					} else if(infoObjects.get(i) instanceof Text){
						((Text) infoObjects.get(i)).setText(s);
					} else if (infoObjects.get(i) instanceof ListView) {
						ObservableList<String> list = FXCollections.observableArrayList();
						list.clear();
						String[] substring = s.split("; ");
						for(int j = 0; j < substring.length; j++){
							list.add(substring[j]);
						}
					//Doesn't add the first entry if it's blank so that the file loads correctly
						if(!list.get(0).equals("") ){
							((ListView) infoObjects.get(i)).setItems(list);
						} 
					}	
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}//END OF FOR LOOP
		 }
		new OverviewTab();
		new SkillTab();
		new ClassesTab();
		new FeatsTab();
		new EquipmentTab();
		new SpellsTab();
		new ModifiersTab();
		new Dialog();
		loadValues();
	} */
	
	/*Loads objects into an ArrayList to be called by loadFile()*/
	public static void loadFile2(Node node){
		infoObjects.clear();
		
		if(node instanceof TabPane){
			TabPane tabPane = (TabPane) node;
			for (Tab tab : tabPane.getTabs()) {
				loadFile2(tab.getContent());
			}
		} else if (node instanceof Pane) {
			Pane pane = (Pane) node;
			for (Node n : pane.getChildren()) {
				loadFile2(n);
			}
		} else if (node instanceof TitledPane) {
			TitledPane titledPane = (TitledPane) node;
			loadFile2(titledPane.getContent());
		} else if (node instanceof ScrollPane) {
			ScrollPane scrollPane = (ScrollPane) node;
			loadFile2(scrollPane.getContent());
		} else if (node instanceof TextField) {
			TextField textField = (TextField) node;
			infoObjects.add(textField);
		} else if (node instanceof TextArea) {
			TextArea textArea = (TextArea) node;
			infoObjects.add(textArea);
		} else if (node instanceof ChoiceBox) {
			ChoiceBox choiceBox = (ChoiceBox) node;
			infoObjects.add(choiceBox);
		} else if (node instanceof ComboBox) {
			ComboBox comboBox = (ComboBox) node;
			infoObjects.add(comboBox);
		} else if (node instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) node;
			infoObjects.add(checkBox);
		} else if (node instanceof Text) {
			try {
				if (!node.getId().equals(null)) {
					Text text = (Text) node;
					infoObjects.add(text);
				}
			} catch (NullPointerException e) {
				//Do nothing if getId() is null
			}
		} else if (node instanceof ListView) {
			ListView listView = (ListView) node;
			infoObjects.add(listView);
		} else if (node instanceof Label || node instanceof Button ||
				node instanceof RadioButton || node instanceof TableView) {
			//Do nothing
		} else {
			//System.out.println(node.getClass().getSimpleName() + " " + node.getId());
		}
	}
	
	static void loadValues() {
		OverviewTab.STR.loadValues();
		OverviewTab.DEX.loadValues();
		OverviewTab.CON.loadValues();
		OverviewTab.INT.loadValues();
		OverviewTab.WIS.loadValues();
		OverviewTab.CHA.loadValues();
		OverviewTab.totalHP = Integer.parseInt(OverviewTab.hpTotal.getText());
		OverviewTab.currentHP = Integer.parseInt(OverviewTab.hpCurrent.getText());
		OverviewTab.tempHP = Integer.parseInt(OverviewTab.hpTemp.getText());
		OverviewTab.nlDmg = Integer.parseInt(OverviewTab.nonlethal.getText());
		ClassesTab.level = Integer.parseInt(ClassesTab.totalLevel.getText());
		SkillTab.skillPoints = Integer.parseInt(SkillTab.unspentRanks.getText());
		for (Skill skill : SkillTab.allSkills) {
			skill.loadValues();
		}
		FeatsTab.featPoints = Integer.parseInt(FeatsTab.featsAvailable.getText());
		EquipmentTab.totalPP = Integer.parseInt(EquipmentTab.pp.getText());
		EquipmentTab.totalGP = Integer.parseInt(EquipmentTab.gp.getText());
		EquipmentTab.totalSP = Integer.parseInt(EquipmentTab.sp.getText());
		EquipmentTab.totalCP = Integer.parseInt(EquipmentTab.cp.getText());
		EquipmentTab.totalWeight = EquipmentTab.wtTotal.getText();
		EquipmentTab.totalWeightDouble = Double.parseDouble(EquipmentTab.totalWeight);
		if (!OverviewTab.STR.abScore.getText().equals("0") && !OverviewTab.DEX.abScore.getText().equals("0")
				&& !OverviewTab.CON.abScore.getText().equals("0") && !OverviewTab.INT.abScore.getText().equals("0")
				&& !OverviewTab.WIS.abScore.getText().equals("0") && !OverviewTab.CHA.abScore.getText().equals("0")) {
			Dialog.afterAbility();
		}
		if (!OverviewTab.race.getSelectionModel().isEmpty()) {
			ClassesTab.addLevels.setDisable(false);
		}
		SpellsTab.populateSpellChoice();
	}

	public static ResultSet queryDB(Connection db, String query) throws SQLException {
		Statement s = db.createStatement();
		ResultSet rs = s.executeQuery(query);
		
		return rs;
	}
	
}//END OF MAIN CLASS
