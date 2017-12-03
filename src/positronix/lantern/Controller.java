package positronix.lantern;

import javafx.fxml.FXML;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class Controller {
	Scene scene = Main.scene;
	
	TextField initTotal = (TextField) scene.lookup("#initTotal");
	TextField initDex = (TextField) scene.lookup("#initDex");
	TextField initMisc = (TextField) scene.lookup("#initMisc");
	
	@FXML
	void refreshInit(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getCode() == KeyCode.ENTER) {
				int dex, misc, total;
				dex = Integer.parseInt(initDex.getText());
				misc = Integer.parseInt(initMisc.getText());
				total = dex + misc;
				initTotal.setText(Integer.toString(total));
			}
		}
	}
}
