package positronix.lantern;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Spell {
	StringProperty level;
	StringProperty name;
	StringProperty comp;
	StringProperty desc;
	
	public Spell(String l, String n, String c, String d) {
		setLevel(l);
		setName(n);
		setComp(c);
		setDesc(d);
	}
	
	public void setName(String s) {
		nameProperty().set(s);
	}
	
	public void setLevel(String s) {
		levelProperty().set(s);
	}
	
	public void setComp(String s) {
		compProperty().set(s);
	}
	
	public void setDesc(String s) {
		descProperty().set(s);
	}
	
	public String getName() {
		return nameProperty().get();
	}
	
	public String getLevel() {
		return levelProperty().get();
	}
	
	public String getComp() {
		return compProperty().get();
	}
	
	public String getDesc() {
		return descProperty().get();
	}
	
	public StringProperty nameProperty() {
		if (name == null) {
			name = new SimpleStringProperty(this, "name");
		}
		return name;
	}
	
	public StringProperty levelProperty() {
		if (level == null) {
			level = new SimpleStringProperty(this, "level");
		}
		return level;
	}
	
	public StringProperty compProperty() {
		if (comp == null) {
			comp = new SimpleStringProperty(this, "comp");
		}
		return comp;
	}
	
	public StringProperty descProperty() {
		if (desc == null) {
			desc = new SimpleStringProperty(this, "desc");
		}
		return desc;
	}
	
	public String toString() {
		return getLevel() + " " +getName();
	}
}
