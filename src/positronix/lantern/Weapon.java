package positronix.lantern;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Weapon {
	StringProperty name;
	StringProperty cost;
	StringProperty sdamage;
	StringProperty mdamage;
	StringProperty critical;
	StringProperty range;
	StringProperty weight;
	StringProperty type;
	StringProperty special;
	StringProperty category;
	
	public Weapon(String n, String cost, String sd, String md, String crit, String r, String w, String t, String s, String cat) {
		setName(n);
		setCost(cost);
		setSDamage(sd);
		setMDamage(md);
		setCritical(crit);
		setRange(r);
		setWeight(w);
		setType(t);
		setSpecial(s);
		setCategory(cat);
	}
	
	public void setName(String s) {
		nameProperty().set(s);
	}
	
	public void setCost(String s) {
		costProperty().set(s);
	}
	
	public void setSDamage(String s) {
		sdamageProperty().set(s);
	}
	
	public void setMDamage(String s) {
		mdamageProperty().set(s);
	}
	
	public void setCritical(String s) {
		criticalProperty().set(s);
	}
	
	public void setRange(String s) {
		rangeProperty().set(s);
	}
	
	public void setWeight(String s) {
		weightProperty().set(s);
	}
	
	public void setType(String s) {
		typeProperty().set(s);
	}
	
	public void setSpecial(String s) {
		specialProperty().set(s);
	}
	
	public void setCategory(String s) {
		categoryProperty().set(s);
	}
	
	public String getName() {
		return nameProperty().get();
	}
	
	public String getCost() {
		return costProperty().get();
	}
	
	public String getSDamage() {
		return sdamageProperty().get();
	}
	
	public String getMDamage() {
		return mdamageProperty().get();
	}
	
	public String getCritical() {
		return criticalProperty().get();
	}
	
	public String getRange() {
		return rangeProperty().get();
	}
	
	public String getWeight() {
		return weightProperty().get();
	}
	
	public String getType() {
		return typeProperty().get();
	}
	
	public String getSpecial() {
		return specialProperty().get();
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
	
	public StringProperty costProperty() {
		if (cost == null) {
			cost = new SimpleStringProperty(this, "cost");
		}
		return cost;
	}
	
	public StringProperty sdamageProperty() {
		if (sdamage == null) {
			sdamage = new SimpleStringProperty(this, "sdamage");
		}
		return sdamage;
	}
	
	public StringProperty mdamageProperty() {
		if (mdamage == null) {
			mdamage = new SimpleStringProperty(this, "mdamage");
		}
		return mdamage;
	}
	
	public StringProperty criticalProperty() {
		if (critical == null) {
			critical = new SimpleStringProperty(this, "critical");
		}
		return critical;
	}
	
	public StringProperty rangeProperty() {
		if (range == null) {
			range = new SimpleStringProperty(this, "range");
		}
		return range;
	}
	
	public StringProperty weightProperty() {
		if (weight == null) {
			weight = new SimpleStringProperty(this, "weight");
		}
		return weight;
	}
	
	public StringProperty typeProperty() {
		if (type == null) {
			type = new SimpleStringProperty(this, "type");
		}
		return type;
	}
	
	public StringProperty specialProperty() {
		if (special == null) {
			special = new SimpleStringProperty(this, "special");
		}
		return special;
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
