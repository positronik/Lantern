package positronix.lantern;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Armor {
	StringProperty name;
	StringProperty cost;
	StringProperty bonus;
	StringProperty maxdex;
	StringProperty penalty;
	StringProperty failure;
	StringProperty speed30;
	StringProperty speed20;
	StringProperty weight;
	StringProperty category;
	
	public Armor(String n, String c, String b, String md, String p, String f, String s30, String s20, String w, String cat) {
		setName(n);
		setCost(c);
		setBonus(b);
		setMaxDex(md);
		setPenalty(p);
		setFailure(f);
		setSpeed30(s30);
		setSpeed20(s20);
		setWeight(w);
		setCategory(cat);
	}
	
	public void setName(String s) {
		nameProperty().set(s);
	}
	
	public void setCost(String s) {
		costProperty().set(s);
	}
	
	public void setBonus(String s) {
		bonusProperty().set(s);
	}
	
	public void setMaxDex(String s) {
		maxdexProperty().set(s);
	}
	
	public void setPenalty(String s) {
		penaltyProperty().set(s);
	}
	
	public void setFailure(String s) {
		failureProperty().set(s);
	}
	
	public void setSpeed30(String s) {
		speed30Property().set(s);
	}
	
	public void setSpeed20(String s) {
		speed20Property().set(s);
	}
	
	public void setWeight(String s) {
		weightProperty().set(s);
	}
	
	public void setCategory(String cat) {
		categoryProperty().set(cat);
	}
	
	public String getName() {
		return nameProperty().get();
	}
	
	public String getCost() {
		return costProperty().get();
	}
	
	public String getBonus() {
		return bonusProperty().get();
	}
	
	public String getMaxDex() {
		return maxdexProperty().get();
	}
	
	public String getPenalty() {
		return penaltyProperty().get();
	}
	
	public String getFailure() {
		return failureProperty().get();
	}
	
	public String getSpeed30() {
		return speed30Property().get();
	}
	
	public String getSpeed20() {
		return speed20Property().get();
	}
	
	public String getWeight() {
		return weightProperty().get();
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
	
	public StringProperty bonusProperty() {
		if (bonus == null) {
			bonus = new SimpleStringProperty(this, "bonus");
		}
		return bonus;
	}
	
	public StringProperty maxdexProperty() {
		if (maxdex == null) {
			maxdex = new SimpleStringProperty(this, "maxdex");
		}
		return maxdex;
	}
	
	public StringProperty penaltyProperty() {
		if (penalty == null) {
			penalty = new SimpleStringProperty(this, "penalty");
		}
		return penalty;
	}
	
	public StringProperty failureProperty() {
		if (failure == null) {
			failure = new SimpleStringProperty(this, "failure");
		}
		return failure;
	}
	
	public StringProperty speed30Property() {
		if (speed30 == null) {
			speed30 = new SimpleStringProperty(this, "speed30");
		}
		return speed30;
	}

	public StringProperty speed20Property() {
		if (speed20 == null) {
			speed20 = new SimpleStringProperty(this, "speed20");
		}
		return speed20;
	}
	
	public StringProperty weightProperty() {
		if (weight == null) {
			weight = new SimpleStringProperty(this, "weight");
		}
		return weight;
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
