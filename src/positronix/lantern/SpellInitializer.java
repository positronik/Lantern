package positronix.lantern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class SpellInitializer {
	Scene scene = Main.scene;
	
	String level;
	
	public TextField known, saveDC, perDay, bonus, track;
	public Button trackMinus, trackPlus;
	
	public SpellInitializer(String levelIn) {
		level = levelIn;
		
		known = (TextField) scene.lookup("#known" + level);
		saveDC = (TextField) scene.lookup("#saveDC" + level);
		perDay = (TextField) scene.lookup("#perDay" + level);
		if (!level.equals("0"))
			bonus = (TextField) scene.lookup("#bonus" + level);
		track = (TextField) scene.lookup("#track" + level);
		
		trackMinus = (Button) scene.lookup("#track" + level + "Minus");
		trackPlus = (Button) scene.lookup("#track" + level + "Plus");
		
		known.setText("0");
		saveDC.setText("0");
		perDay.setText("0");
		if (!level.equals("0"))
			bonus.setText("0");
		track.setText("0");
		
		track.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				int current = Integer.parseInt(track.getText());
				if (e.getCode() == KeyCode.UP) {
					int curPerDay = Integer.parseInt(perDay.getText());
		            int curBonus = 0;
		            if (!level.equals("0"))
		            	curBonus = Integer.parseInt(bonus.getText());
		            if (current < curPerDay + curBonus) {
		            	current++;
		            	track.setText(Integer.toString(current));
		            }
				}
				if (e.getCode() == KeyCode.DOWN) {
					if (current > 0) {
						current--;
						track.setText(Integer.toString(current));
					}
				}
			}
		});
		
		trackMinus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            int i = Integer.parseInt(track.getText());
	            if (i > 0) {
	            	i--;
	            	track.setText(Integer.toString(i));
	            }
	        }
	    });
		
		trackPlus.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            int i = Integer.parseInt(track.getText());
	            int curPerDay = Integer.parseInt(perDay.getText());
	            int curBonus = 0;
	            if (!level.equals("0"))
	            	curBonus = Integer.parseInt(bonus.getText());
	            if (i < curPerDay + curBonus) {
	            	i++;
	            	track.setText(Integer.toString(i));
	            }
	        }
	    });
	}
	
	public void reset() {
		known.setText("0");
		saveDC.setText("0");
		perDay.setText("0");
		if (!level.equals("0"))
			bonus.setText("0");
	}
	
	public void resetTracker() {
		int pd = Integer.parseInt(perDay.getText());
		
		if (!level.equals("0")) {
			pd += Integer.parseInt(bonus.getText());
		}
		
		track.setText(Integer.toString(pd));
	}
	
	public void disable() {
		known.setDisable(true);
		saveDC.setDisable(true);
		perDay.setDisable(true);
		if (!level.equals("0"))
			bonus.setDisable(true);
		track.setDisable(true);
		trackMinus.setDisable(true);
		trackPlus.setDisable(true);
	}
	
	public void enable() {
		known.setDisable(false);
		saveDC.setDisable(false);
		perDay.setDisable(false);
		if (!level.equals("0"))
			bonus.setDisable(false);
		track.setDisable(false);
		trackMinus.setDisable(false);
		trackPlus.setDisable(false);
	}
}
