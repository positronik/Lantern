package positronix.lantern;

import positronix.lantern.tabs.OverviewTab;
import positronix.lantern.tabs.ShortSkill;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TypeSkill extends Skill {
	TextField skillType;
	String nameWithType;
	
	public TypeSkill(String skillName, int skillMod) {
		super(skillName, skillMod);
		
		skillType = (TextField) scene.lookup("#" + skillName + "Type");

        nameWithType = overviewName + " (" + skillType.getText() + ")"; 
        skillInfo = new ShortSkill(nameWithType, Integer.toString(total));
        
        skillType.focusedProperty().addListener(new ChangeListener<Boolean>() {
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean wasFocused, Boolean isFocused) {
        		nameWithType = overviewName + " (" + skillType.getText() + ")"; 
                skillInfo = new ShortSkill(nameWithType, Integer.toString(total));
        	}
        });
	}
	
	public String toString() {
		return nameWithType;
	}
}
