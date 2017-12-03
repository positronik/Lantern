package positronix.lantern.tabs;

//import positronix.lantern.*;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShortWeapon {
	StringProperty name;
	StringProperty bonus;
	StringProperty critical;
	StringProperty damage;
	StringProperty ammo;
	StringProperty range;
	StringProperty type;
	StringProperty category;
	
	public ShortWeapon(String n, String b, String c, String d, String a, String r, String t, String cat) {
		setName(n);
		setBonus(b);
		setCritical(c);
		setDamage(d);
		setAmmo(a);
		setRange(r);
		setType(t);
		setCategory(cat);
	}
	
	public void setName(String s) {
		nameProperty().set(s);
	}
	
	public void setBonus(String s) {
		bonusProperty().set(s);
	}
	
	public void setCritical(String s) {
		criticalProperty().set(s);
	}
	
	public void setDamage(String s) {
		damageProperty().set(s);
	}
	
	public void setAmmo(String s) {
		ammoProperty().set(s);
	}
	
	public void setRange(String s) {
		rangeProperty().set(s);
	}
	
	public void setType(String s) {
		typeProperty().set(s);
	}
	
	public void setCategory(String s) {
		categoryProperty().set(s);
	}
	
	public String getName() {
		return nameProperty().get();
	}
	
	public String getBonus() {
		return bonusProperty().get();
	}
	
	public String getCritical() {
		return criticalProperty().get();
	}
	
	public String getDamage() {
		return damageProperty().get();
	}
	
	public String getAmmo() {
		return ammoProperty().get();
	}
	
	public String getRange() {
		return rangeProperty().get();
	}
	
	public String getType() {
		return typeProperty().get();
	}
	
	public String getCategory() {
		return categoryProperty().get();
	}
	
	public StringProperty nameProperty() {
		if (name == null) {
			name = new SimpleStringProperty(this, "name");
		}
		return name;
	}
	
	public StringProperty bonusProperty() {
		if (bonus == null) {
			bonus = new SimpleStringProperty(this, "bonus");
		}
		return bonus;
	}
	
	public StringProperty criticalProperty() {
		if (critical == null) {
			critical = new SimpleStringProperty(this, "critical");
		}
		return critical;
	}
	
	public StringProperty damageProperty() {
		if (damage == null) {
			damage = new SimpleStringProperty(this, "damage");
		}
		return damage;
	}
	
	public StringProperty ammoProperty() {
		if (ammo == null) {
			ammo = new SimpleStringProperty(this, "ammo");
		}
		return ammo;
	}
	
	public StringProperty rangeProperty() {
		if (range == null) {
			range = new SimpleStringProperty(this, "range");
		}
		return range;
	}
	
	public StringProperty typeProperty() {
		if (type == null) {
			type = new SimpleStringProperty(this, "type");
		}
		return type;
	}
	
	public StringProperty categoryProperty() {
		if (category == null) {
			category = new SimpleStringProperty(this, "category");
		}
		return category;
	}
	
	public String toString() {
		return getName();
	}
}
