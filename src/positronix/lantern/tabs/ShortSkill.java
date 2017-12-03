package positronix.lantern.tabs;

//import positronix.lantern.*;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShortSkill {
	StringProperty name;
	StringProperty total;
	
	public ShortSkill(String n, String t) {
		setName(n);
		setTotal(t);
	}
	
	public void setName(String s) {
		nameProperty().set(s);
	}
	
	public String getName() {
		return nameProperty().get();
	}
	
	public StringProperty nameProperty() {
		if (name == null) {
			name = new SimpleStringProperty(this, "name");
		}
		return name;
	}
	
	public void setTotal(String s) {
		totalProperty().set(s);
	}
	
	public String getTotal() {
		return totalProperty().get();
	}
	
	public StringProperty totalProperty() {
		if (total == null) {
			total = new SimpleStringProperty(this, "total");
		}
		return total;
	}
	public String capitalize(String line) {
		   return java.lang.Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
	
	public String toString() {
		return getName();
	}
}
