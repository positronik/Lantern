package positronix.lantern;

import positronix.lantern.tabs.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AbilityGenerator {
	Scene scene = Main.generatorScene;
	//public static TextField pointsLeft;
	public TextField roll, points;
	public Button assign, minus, plus;
	public int currentPoints, pointsUsed;
	public static int startingPoints;

	public AbilityGenerator(String abName) {
		assign = (Button) scene.lookup("#assign" + abName);
		roll = (TextField) scene.lookup("#" + abName + "Roll");
		
		points = (TextField) scene.lookup("#" + abName + "Points");
		minus = (Button) scene.lookup("#" + abName + "PointsMinus");
		plus = (Button) scene.lookup("#" + abName + "PointsPlus");
		
		currentPoints = startingPoints;

		assign.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	if (!Dialog.abRollList.getSelectionModel().isEmpty()) {
		            if (!roll.getText().equals("")) {
		            	Dialog.abRollList.getItems().add(roll.getText());
		            }
		            roll.setText(Dialog.abRollList.getSelectionModel().getSelectedItem());
		            Dialog.abRollList.getItems().remove(Dialog.abRollList.getSelectionModel().getSelectedIndex());
	        	}
	        }
	    });
		
		minus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            int pts = Integer.parseInt(points.getText());
	            if (pts > 7) {
	            	pts--;
	            	currentPoints = startingPoints - Calculations.PointBuy(pts);
	            	pointsUsed = Calculations.PointBuy(pts);
	            	Dialog.getTotalPoints();
	            	//Dialog.pointsLeft.setText(Integer.toString(currentPoints));
	            	points.setText(Integer.toString(pts));
	            }
	        }
	    });
		
		plus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	        	int pts = Integer.parseInt(points.getText());
	            if (pts < 18) {
	            	pts++;
	            	currentPoints = startingPoints - Calculations.PointBuy(pts);
	            	pointsUsed = Calculations.PointBuy(pts);
	            	Dialog.getTotalPoints();
	            	//Dialog.pointsLeft.setText(Integer.toString(currentPoints));
	            	points.setText(Integer.toString(pts));
	            }
	        }
	    });
			
	}
	

	public int getPointsUsed(){
		return pointsUsed;
		
	}
	
	public void setPointsUsed(int pointsUsed){
		this.pointsUsed = pointsUsed;
	}
	public void resetPoints(){
		currentPoints = startingPoints;
		pointsUsed = 0;
		points.setText(Integer.toString(10));
	}
}
