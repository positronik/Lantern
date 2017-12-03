package positronix.lantern;

//import positronix.lantern.tabs.*;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Feat {
	StringProperty name, prereq, benefit, levelinc;
	
	public Feat(String n, String p, String b) {
		setName(n);
		setPrereq(p);
		setBenefit(b);
	}
	
	public Feat(String n, String p, String b, String l) {
		setName(n);
		setPrereq(p);
		setBenefit(b);
		setLevelinc(l);
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
	
	public void setPrereq(String s) {
		prereqProperty().set(s);
	}
	
	public String getPrereq() {
		return prereqProperty().get();
	}
	
	public StringProperty prereqProperty() {
		if (prereq == null) {
			prereq = new SimpleStringProperty(this, "prereq");
		}
		return prereq;
	}
	
	public void setBenefit(String s) {
		benefitProperty().set(s);
	}
	
	public String getBenefit() {
		return benefitProperty().get();
	}
	
	public StringProperty benefitProperty() {
		if (benefit == null) {
			benefit = new SimpleStringProperty(this, "benefit");
		}
		return benefit;
	}
	
	public void setLevelinc(String s) {
		levelincProperty().set(s);
	}
	
	public String getLevelinc() {
		return levelincProperty().get();
	}
	
	public StringProperty levelincProperty() {
		if (levelinc == null) {
			levelinc = new SimpleStringProperty(this, "levelinc");
		}
		return levelinc;
	}
	
	public String toString() {
		return getName();
	}
}
