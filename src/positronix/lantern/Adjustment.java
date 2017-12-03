package positronix.lantern;

import positronix.lantern.tabs.ShortWeapon;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

public class Adjustment {
	StringProperty target;
	ObjectProperty<Ability> ability;
	ObjectProperty<TextField> field;
	ObjectProperty<ShortWeapon> weapon;
	ListProperty<ShortWeapon> weapons;
	IntegerProperty amount;
	StringProperty reason;
	BooleanProperty enabled;
	
	public Adjustment(String s, Ability ab, int a, String r, boolean e) {
		setTarget(s);
		setAbility(ab);
		setAmount(a);
		setReason(r);
		setEnabled(e);
		
		apply();
	}
	
	public Adjustment(String s, TextField f, int a, String r, boolean e) {
		setTarget(s);
		setField(f);
		setAmount(a);
		setReason(r);
		setEnabled(e);
		
		apply();
	}
	
	public Adjustment(String s, ShortWeapon w, int a, String r, boolean e) {
		setTarget(s);
		setWeapon(w);
		setAmount(a);
		setReason(r);
		setEnabled(e);
		
		apply();
	}
	
	public Adjustment(String s, ObservableList<ShortWeapon> ws, int a, String r, boolean e) {
		setTarget(s);
		setWeapons(ws);
		setAmount(a);
		setReason(r);
		setEnabled(e);
		
		apply();
	}
	
	public void setTarget(String s) {
		targetProperty().set(s);
	}
	
	public void setAbility(Ability ab) {
		abilityProperty().set(ab);
	}
	
	public void setField(TextField f) {
		fieldProperty().set(f);
	}
	
	public void setWeapon(ShortWeapon w) {
		weaponProperty().set(w);
	}
	
	public void setWeapons(ObservableList<ShortWeapon> ws) {
		weaponsProperty().set(ws);
	}
	
	public void setAmount(int a) {
		amountProperty().set(a);
	}
	
	public void setReason(String r) {
		reasonProperty().set(r);
	}
	
	public void setEnabled(boolean e) {
		enabledProperty().set(e);
	}
	
	public String getTarget() {
		return targetProperty().get();
	}
	
	public Ability getAbility() {
		return abilityProperty().get();
	}
	
	public TextField getField() {
		return fieldProperty().get();
	}
	
	public ShortWeapon getWeapon() {
		return weaponProperty().get();
	}
	
	public ObservableList<ShortWeapon> getWeapons() {
		return weaponsProperty().get();
	}
	
	public int getAmount() {
		return amountProperty().get();
	}
	
	public String getReason() {
		return reasonProperty().get();
	}
	
	public boolean isEnabled() {
		return enabledProperty().get();
	}
	
	public StringProperty targetProperty() {
		if (target == null) {
			target = new SimpleStringProperty(this, "target");
		}
		return target;
	}
	
	public ObjectProperty<Ability> abilityProperty() {
		if (ability == null) {
			ability = new SimpleObjectProperty<Ability>(this, "ability");
		}
		return ability;
	}
	
	public ObjectProperty<TextField> fieldProperty() {
		if (field == null) {
			field = new SimpleObjectProperty<TextField>(this, "field");
		}
		return field;
	}
	
	public ObjectProperty<ShortWeapon> weaponProperty() {
		if (weapon == null) {
			weapon = new SimpleObjectProperty<ShortWeapon>(this, "weapon");
		}
		return weapon;
	}
	
	public ListProperty<ShortWeapon> weaponsProperty() {
		if (weapons == null) {
			weapons = new SimpleListProperty<ShortWeapon>(this, "weapons");
		}
		return weapons;
	}
	
	public IntegerProperty amountProperty() {
		if (amount == null) {
			amount = new SimpleIntegerProperty(this, "amount");
		}
		return amount;
	}
	
	public StringProperty reasonProperty() {
		if (reason == null) {
			reason = new SimpleStringProperty(this, "reason");
		}
		return reason;
	}
	
	public BooleanProperty enabledProperty() {
		if (enabled == null) {
			enabled = new SimpleBooleanProperty(this, "enabled");
		}
		return enabled;
	}
	
	public void toggleEnabled() {
		if (isEnabled()) {
			remove();
		} else {
			apply();
		}
	}
	
	public void apply() {
		setEnabled(true);
		switch (getType()) {
			case "Ability":
				getAbility().bonus += getAmount();
				getAbility().abTemp.setText(Integer.toString(getAbility().bonus));
				break;
			case "TextField":
				int oldVal = Integer.parseInt(getField().getText());
				oldVal += getAmount();
				getField().setText(Integer.toString(oldVal));
				break;
			case "Weapon":
				if (getTarget().contains("Attack")) {
					int oldBonus = Integer.parseInt(getWeapon().getBonus());
					oldBonus += getAmount();
					getWeapon().setBonus(Integer.toString(oldBonus));
				}
				if (getTarget().contains("Damage")) {
					String damage = getWeapon().getDamage();
					String[] splitDamage = new String[2];
					int mod;
					if (damage.contains("+")) {
						splitDamage = damage.split("\\+");
						mod = Integer.parseInt(splitDamage[1]);
						mod += getAmount();
					} else if (damage.contains("-")) {
						splitDamage = damage.split("\\-");
						mod = -Integer.parseInt(splitDamage[1]);
						mod += getAmount();
					} else {
						splitDamage[0] = damage;
						mod = 0;
						mod += getAmount();
					}
					
					if (mod > 0) {
						getWeapon().setDamage(splitDamage[0] + "+" + mod);
					} else if (mod < 0) {
						getWeapon().setDamage(splitDamage[0] + mod);
					} else {
						getWeapon().setDamage(splitDamage[0]);
					}
				}
				break;
			case "Weapons":
				if (getTarget().contains("Attack")) {
					int oldBonus;
					for (ShortWeapon weapon : getWeapons()) {
						oldBonus = Integer.parseInt(weapon.getBonus());
						oldBonus += getAmount();
						weapon.setBonus(Integer.toString(oldBonus));
					}
				}
				if (getTarget().contains("Damage")) {
					String damage;
					String[] splitDamage;
					int mod;
					for (ShortWeapon weapon : getWeapons()) {
						damage = weapon.getDamage();
						splitDamage = new String[2];
						if (damage.contains("+")) {
							splitDamage = damage.split("\\+");
							mod = Integer.parseInt(splitDamage[1]);
							mod += getAmount();
						} else if (damage.contains("-")) {
							splitDamage = damage.split("\\-");
							mod = -Integer.parseInt(splitDamage[1]);
							mod += getAmount();
						} else {
							splitDamage[0] = damage;
							mod = 0;
							mod += getAmount();
						}
						
						if (mod > 0) {
							weapon.setDamage(splitDamage[0] + "+" + mod);
						} else if (mod < 0) {
							weapon.setDamage(splitDamage[0] + mod);
						} else {
							weapon.setDamage(splitDamage[0]);
						}
					}
				}
				break;
			default:
				
		}
	}
	
	public void remove() {
		setEnabled(false);
		switch (getType()) {
			case "Ability":
				getAbility().bonus -= getAmount();
				getAbility().abTemp.setText(Integer.toString(getAbility().bonus));
				break;
			case "TextField":
				int oldVal = Integer.parseInt(getField().getText());
				oldVal -= getAmount();
				getField().setText(Integer.toString(oldVal));
				break;
			case "Weapon":
				if (getTarget().contains("Attack")) {
					int oldBonus = Integer.parseInt(getWeapon().getBonus());
					oldBonus -= getAmount();
					getWeapon().setBonus(Integer.toString(oldBonus));
				}
				if (getTarget().contains("Damage")) {
					String damage = getWeapon().getDamage();
					String[] splitDamage = new String[2];
					int mod;
					if (damage.contains("+")) {
						splitDamage = damage.split("\\+");
						mod = Integer.parseInt(splitDamage[1]);
						mod -= getAmount();
					} else if (damage.contains("-")) {
						splitDamage = damage.split("\\-");
						mod = -Integer.parseInt(splitDamage[1]);
						mod -= getAmount();
					} else {
						splitDamage[0] = damage;
						mod = 0;
						mod -= getAmount();
					}
					
					if (mod > 0) {
						getWeapon().setDamage(splitDamage[0] + "+" + mod);
					} else if (mod < 0) {
						getWeapon().setDamage(splitDamage[0] + mod);
					} else {
						getWeapon().setDamage(splitDamage[0]);
					}
				}
				break;
			case "Weapons":
				if (getTarget().contains("Attack")) {
					int oldBonus;
					for (ShortWeapon weapon : getWeapons()) {
						oldBonus = Integer.parseInt(weapon.getBonus());
						oldBonus -= getAmount();
						weapon.setBonus(Integer.toString(oldBonus));
					}
				}
				if (getTarget().contains("Damage")) {
					String damage;
					String[] splitDamage;
					int mod;
					for (ShortWeapon weapon : getWeapons()) {
						damage = weapon.getDamage();
						splitDamage = new String[2];
						if (damage.contains("+")) {
							splitDamage = damage.split("\\+");
							mod = Integer.parseInt(splitDamage[1]);
							mod -= getAmount();
						} else if (damage.contains("-")) {
							splitDamage = damage.split("\\-");
							mod = -Integer.parseInt(splitDamage[1]);
							mod -= getAmount();
						} else {
							splitDamage[0] = damage;
							mod = 0;
							mod -= getAmount();
						}
						
						if (mod > 0) {
							weapon.setDamage(splitDamage[0] + "+" + mod);
						} else if (mod < 0) {
							weapon.setDamage(splitDamage[0] + mod);
						} else {
							weapon.setDamage(splitDamage[0]);
						}
					}
				}
				break;
			default:
				
		}
	}
	
	public String getType() {
		String s;
		
		if (ability != null) {
			s = "Ability";
		} else if (field != null) {
			s = "TextField";
		} else if (weapon != null) {
			s = "Weapon";
		} else if (weapons != null) {
			s = "Weapons";
		} else {
			s = "None";
		}
		
		return s;
	}
	
	public String toString() {
		return "Adjusting " + getTarget() + " by " + getAmount() + " for reason: \"" + getReason() + "\"";
	}
}
