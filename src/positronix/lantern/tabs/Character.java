package positronix.lantern.tabs;

import positronix.lantern.*;

import java.util.*;

public abstract class Character { 
	/*
    public enum CharacterClass { 
        Barbarian, Bard, Cleric, Druid, Fighter, Monk, Paladin, Ranger, Rogue, Sorcerer, Wizard,    //Core classes
        Alchemist, Cavalier, Gunslinger, Inquisitor, Magus, Oracle, Summoner, Witch,                //Base classes
        Antipaladin, Ninja, Samurai,                                                                //Alternate classes
        Arcanist, Bloodrager, Brawler, Hunter, Investigator, Shaman, Skald, Slayer, Swashbuckler, Warpriest, //Hybrid classes [not yet implemented]
        OtherClass
    } */
    
    //int level;
    //CharacterRace chRace;
    //ArrayList<CharacterClass> chClass = new ArrayList<>();
	
	public static HashMap<String, CharacterRace> raceMap = new HashMap<>();
	public static HashMap<String, CharacterClass> classMap = new HashMap<>();

	static {
    	raceMap.put("Dwarf", new Dwarf());
        raceMap.put("Elf", new Elf());
        raceMap.put("Gnome", new Gnome());
        raceMap.put("Half-elf", new HalfElf());
        raceMap.put("Halfling", new Halfling());
        raceMap.put("Half-orc", new HalfOrc());
        raceMap.put("Human", new Human());
        raceMap.put("OtherRace", new OtherRace());
        
        classMap.put("Alchemist", new Alchemist());
        classMap.put("Antipaladin", new Antipaladin());
        classMap.put("Barbarian", new Barbarian());
        classMap.put("Bard", new Bard());
        classMap.put("Cavalier", new Cavalier());
        classMap.put("Cleric", new Cleric());
        classMap.put("Druid", new Druid());
        classMap.put("Fighter", new Fighter());
        classMap.put("Gunslinger", new Gunslinger());
        classMap.put("Inquisitor", new Inquisitor());
        classMap.put("Magus", new Magus());
        classMap.put("Monk", new Monk());
        classMap.put("Ninja", new Ninja());
        classMap.put("Oracle", new Oracle());
        classMap.put("Paladin", new Paladin());
        classMap.put("Ranger", new Ranger());
        classMap.put("Rogue", new Rogue());
        classMap.put("Samurai", new Samurai());
        classMap.put("Sorcerer", new Sorcerer());
        classMap.put("Summoner", new Summoner());
        classMap.put("Wizard", new Wizard());
        classMap.put("Witch", new Witch());
        //classMap.put("MultiClass", new MultiClass());
        classMap.put("OtherClass", new OtherClass());
        
        //level = 1;
        //chRace = raceMap.get("OtherRace");
        //chClass.add(classMap.get("OtherClass"));
    }
    /*
    public Character(String raceString, String classString) {
        this();
        
        if (raceMap.get(raceString) == null) {			//Checks that race/class entry exists before assigning
            System.out.println("Invalid race");			//(Hashmap get() method returns null if no matching key is found)
        }
        else {
        	chRace = raceMap.get(raceString);
        }
        
        if (classMap.get(classString) == null) {
        	System.out.println("Invalid class");
        }
        else {
        	chClass.remove(0);
        	chClass.add(classMap.get(classString));
        }
        
        System.out.println("Level " + level + " " + chRace + " " + chClass);
    }
    
    void addLevel() {
    	level++;
    	
    	chClass.add(classMap.get("OtherClass"));
    	
    	System.out.println("Level " + level + " " + chRace + " " + chClass);
    }
    
    void addLevel(String classString) {
    	level++;
    	
    	if (classMap.get(classString) == null) {
    		System.out.println("Invalid class");
    	}
    	else {
    		chClass.add(classMap.get(classString));
    	}
    	
    	System.out.println("Level " + level + " " + chRace + " " + chClass);
    }
    
	
    int getClassLevel(String classString) {
    	int classLevel = 0;
    	
    	for (CharacterClass c : chClass) {
    		if (c.toString().equals(classString)) {
    			classLevel++;
    		}
    	}
    	
    	return classLevel;
    }
    
 
    ArrayList<CharacterClass> getClasses() {
    	ArrayList<CharacterClass> classes = new ArrayList<>();
    	
    	for (CharacterClass c : chClass) {
    		if (!classes.contains(c)) {
    			classes.add(c);;
    		}
    	}
    	
    	return classes;
    }
    
    int getClassIndex(String classString) {
    	int index = 0;
    	ArrayList<CharacterClass> classes = getClasses();
    	
    	LOOP: for (int i = 0; i < classes.size(); i++) {
    		if (classes.get(i).toString().equals(classString)) {
    			index = i;
    			break LOOP;
    		}
    	}
    	
    	return index;
    }
    
    ArrayList<Integer> getLevels() {
    	ArrayList<CharacterClass> classes = new ArrayList<>();
    	ArrayList<Integer> levels = new ArrayList<>();
    	
    	for (CharacterClass c : chClass) {
    		if (!classes.contains(c)) {
    			classes.add(c);
    			levels.add(getClassLevel(c.toString()));
    		}
    	}
    	
    	return levels;
    }
    
    public String toString() {
    	ArrayList<CharacterClass> classes = getClasses();
    	ArrayList<Integer> levels = getLevels();
    	
    	String s = chRace.toString() + " ";
    	
    	for (int i = 0; i < classes.size(); i++) {
    		if (i == classes.size() - 1) {
    			s += classes.get(i) + " ";
        		s += levels.get(i);
    		}
    		else {
	    		s += classes.get(i) + " ";
	    		s += levels.get(i) + ", ";
    		}
    	}
    	
    	s += " (Character level " + level + ")";
    	
    	return s;
    } */
}

abstract class CharacterRace {
    // Racial ability score bonuses
    int strb;
    int dexb;
    int conb;
    int intb;
    int wisb;
    int chab;
    boolean choiceOfBonus;      //Half-orcs, half-elves, and humans get +2 to a single score of their choice
    
    String size;
    int speed;
    
    ArrayList<String> languagesKnown = new ArrayList<>();
    
    CharacterRace() {
        strb = 0;
        dexb = 0;
        conb = 0;
        intb = 0;
        wisb = 0;
        chab = 0;
        choiceOfBonus = false;
        
        size = "Medium";
        speed = 30;
        
        languagesKnown.add("Common");
    }
    
    public String toString() {
    	return this.getClass().getSimpleName();
    }
}

class Dwarf extends CharacterRace {
    Dwarf() {
        conb = 2;
        wisb = 2;
        chab = -2;
        
        speed = 20;
        
        languagesKnown.add("Dwarven");
    }
}

class Elf extends CharacterRace {
    Elf() {
        dexb = 2;
        intb = 2;
        conb = -2;
        
        languagesKnown.add("Elven");
    }
}

class Gnome extends CharacterRace {
    Gnome() {
        conb = 2;
        chab = 2;
        strb = -2;
        
        size = "Small";
        speed = 20;
        
        languagesKnown.add("Gnome");
        languagesKnown.add("Sylvan");
    }
}

class HalfElf extends CharacterRace {
    HalfElf() {
        choiceOfBonus = true;
        
        languagesKnown.add("Elven");
    }
}

class Halfling extends CharacterRace {
    Halfling() {
        dexb = 2;
        chab = 2;
        strb = -2;
        
        size = "Small";
        speed = 20;
        
        languagesKnown.add("Halfling");
    }
}

class HalfOrc extends CharacterRace {
    HalfOrc () {
        choiceOfBonus = true;
        
        languagesKnown.add("Orc");
    }
}

class Human extends CharacterRace {
    Human() {
        choiceOfBonus = true;
    }
}

class OtherRace extends CharacterRace {
    /*
    Implementation details uncertain. Player will probably just be able to enter any details desired
    Possible support for saving/loading custom races and classes?
    */
}

abstract class CharacterClass {
	boolean castsSpells;
	Ability spellAbility;
	HashMap<Integer, Integer> spellLevels = new HashMap<>();
	 //Maps spell levels to the class level at which they become available
	//A 0 for the class level indicates the class does not get that spell level
	
	List<Skill> classSkills = new ArrayList<>();
	
	int hd; //Hit die
	int sr; //Skill ranks per level
	//int rsw = 1; //Random starting wealth (Xd6 x 10)
	int asw; //Average starting wealth (in gp)
	
	CharacterClass () {

		hd = 8;
		sr = 0;
		asw = 0;
		
		castsSpells = false;
		
		spellLevels.put(0, 0);
		spellLevels.put(1, 0);
		spellLevels.put(2, 0);
		spellLevels.put(3, 0);
		spellLevels.put(4, 0);
		spellLevels.put(5, 0);
		spellLevels.put(6, 0);
		spellLevels.put(7, 0);
		spellLevels.put(8, 0);
		spellLevels.put(9, 0);
	}
	
	public String toString() {
    	return this.getClass().getSimpleName();
    }
	
}

/*
abstract class SpellCaster extends CharacterClass {
	//public static HashMap<Integer, Integer> spellLevels = new HashMap<>();
	//Maps spell levels to the class level at which they become available
	//A 0 for the class level indicates the class does not get that spell level
	
	SpellCaster() {
		spellLevels.put(0, 0);
		spellLevels.put(1, 0);
		spellLevels.put(2, 0);
		spellLevels.put(3, 0);
		spellLevels.put(4, 0);
		spellLevels.put(5, 0);
		spellLevels.put(6, 0);
		spellLevels.put(7, 0);
		spellLevels.put(8, 0);
		spellLevels.put(9, 0);
	}
	
	
	SpellCaster() {
		canCast.put("Zeroth", false);
    	canCast.put("First", false);
    	canCast.put("Second", false);
    	canCast.put("Third", false);
    	canCast.put("Fourth", false);
    	canCast.put("Fifth", false);
    	canCast.put("Sixth", false);
    	canCast.put("Seventh", false);
    	canCast.put("Eighth", false);
    	canCast.put("Ninth", false);
	}
	
	//abstract HashMap<String, Boolean> getSpellLevels(int classLevel);
} */

class Alchemist extends CharacterClass {
		
	Alchemist() {

		sr = 4;
		asw = 105;
		
		castsSpells = true;
		spellAbility = OverviewTab.INT;
		
		spellLevels.put(1, 1);
		spellLevels.put(2, 4);
		spellLevels.put(3, 7);
		spellLevels.put(4, 10);
		spellLevels.put(5, 13);
		spellLevels.put(6, 16);
		
		classSkills.add(SkillTab.appraise);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.disableDevice);
		classSkills.add(SkillTab.fly);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.sleightOfHand);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.survival);
		classSkills.add(SkillTab.useMagicDevice);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16: canCast.put("Sixth", true);
				case 15: 
				case 14:
				case 13: canCast.put("Fifth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Fourth", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Third", true);
				case 6: 
				case 5: 
				case 4: canCast.put("Second", true);
				case 3: 
				case 2: 
				case 1: canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Antipaladin extends CharacterClass {
	
	Antipaladin() {
		hd = 10;
		sr = 2;
		asw =  175;
		
		castsSpells = true;
		spellAbility = OverviewTab.CHA;
		
		spellLevels.put(1, 4);
		spellLevels.put(2, 7);
		spellLevels.put(3, 10);
		spellLevels.put(4, 13);
		
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.disguise);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.stealth);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16:
				case 15: 
				case 14:
				case 13: canCast.put("Fourth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Third", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Second", true);
				case 6: 
				case 5: 
				case 4: canCast.put("First", true);
				case 3: 
				case 2: 
				case 1:
					break;
			}
		}
		return canCast;
	} */
}

class Barbarian extends CharacterClass {
    
	Barbarian() {
    	hd = 12;
    	sr = 4;
    	asw = 105;
    	
    	classSkills.add(SkillTab.acrobatics);
    	classSkills.add(SkillTab.climb);
    	classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
    	classSkills.add(SkillTab.handleAnimal);
    	classSkills.add(SkillTab.intimidate);
    	classSkills.add(SkillTab.nature);
    	classSkills.add(SkillTab.perception);
    	classSkills.add(SkillTab.ride);
    	classSkills.add(SkillTab.survival);
    	classSkills.add(SkillTab.swim);
    }
}

class Bard extends CharacterClass {
	
    Bard() {
    	sr = 6;
    	asw = 105;
    	
    	castsSpells = true;
    	spellAbility = OverviewTab.CHA;
    	
    	spellLevels.put(0, 1);
    	spellLevels.put(1, 1);
		spellLevels.put(2, 4);
		spellLevels.put(3, 7);
		spellLevels.put(4, 10);
		spellLevels.put(5, 13);
		spellLevels.put(6, 16);
		
		classSkills.add(SkillTab.acrobatics);
		classSkills.add(SkillTab.appraise);
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.disguise);
		classSkills.add(SkillTab.escapeArtist);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.engineering);
		classSkills.add(SkillTab.geography);
		classSkills.add(SkillTab.history);
		classSkills.add(SkillTab.local);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.nobility);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.linguistics);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.perform1);
		classSkills.add(SkillTab.perform2);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.sleightOfHand);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.stealth);
		classSkills.add(SkillTab.useMagicDevice);
    }
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16: canCast.put("Sixth", true);
				case 15: 
				case 14:
				case 13: canCast.put("Fifth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Fourth", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Third", true);
				case 6: 
				case 5: 
				case 4: canCast.put("Second", true);
				case 3: 
				case 2: 
				case 1: canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Cavalier extends CharacterClass {
	
	Cavalier() {
		hd = 10;
		sr = 4;
		asw = 175;
		
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.swim);
	}
}

class Cleric extends CharacterClass {
	
	Cleric() {
		sr = 2;
		asw = 140;
		
		castsSpells = true;
		spellAbility = OverviewTab.WIS;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 3);
		spellLevels.put(3, 5);
		spellLevels.put(4, 7);
		spellLevels.put(5, 9);
		spellLevels.put(6, 11);
		spellLevels.put(7, 13);
		spellLevels.put(8, 15);
		spellLevels.put(9, 17);
		
		classSkills.add(SkillTab.appraise);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.history);
		classSkills.add(SkillTab.nobility);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.linguistics);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.spellcraft);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: canCast.put("Ninth", true);
				case 16: 
				case 15: canCast.put("Eighth", true);
				case 14:
				case 13: canCast.put("Seventh", true);
				case 12: 
				case 11: canCast.put("Sixth", true);
				case 10: 
				case 9: canCast.put("Fifth", true);
				case 8: 
				case 7: canCast.put("Fourth", true);
				case 6: 
				case 5: canCast.put("Third", true);
				case 4: 
				case 3: canCast.put("Second", true);
				case 2: 
				case 1: canCast.put("Zeroth", true); canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Druid extends CharacterClass {
	
	Druid() {
		sr = 4;
		asw = 70;
		
		castsSpells = true;
		spellAbility = OverviewTab.WIS;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 3);
		spellLevels.put(3, 5);
		spellLevels.put(4, 7);
		spellLevels.put(5, 9);
		spellLevels.put(6, 11);
		spellLevels.put(7, 13);
		spellLevels.put(8, 15);
		spellLevels.put(9, 17);
		
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.fly);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.geography);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.survival);
		classSkills.add(SkillTab.swim);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: canCast.put("Ninth", true);
				case 16: 
				case 15: canCast.put("Eighth", true);
				case 14:
				case 13: canCast.put("Seventh", true);
				case 12: 
				case 11: canCast.put("Sixth", true);
				case 10: 
				case 9: canCast.put("Fifth", true);
				case 8: 
				case 7: canCast.put("Fourth", true);
				case 6: 
				case 5: canCast.put("Third", true);
				case 4: 
				case 3: canCast.put("Second", true);
				case 2: 
				case 1: canCast.put("Zeroth", true); canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Fighter extends CharacterClass {
    
	Fighter() {
		hd = 10;
		sr = 2;
		asw = 175;
		
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.engineering);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.survival);
		classSkills.add(SkillTab.swim);
	}
}

class Gunslinger extends CharacterClass {
	
	Gunslinger() {
		hd = 10;
		sr = 4;
		asw = 175;
		
		classSkills.add(SkillTab.acrobatics);
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.engineering);
		classSkills.add(SkillTab.local);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.sleightOfHand);
		classSkills.add(SkillTab.survival);
		classSkills.add(SkillTab.swim);
	}
}

class Inquisitor extends CharacterClass {
	
	Inquisitor() {
		sr = 6;
		asw = 140;
		
		castsSpells = true;
		spellAbility = OverviewTab.WIS;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 4);
		spellLevels.put(3, 7);
		spellLevels.put(4, 10);
		spellLevels.put(5, 13);
		spellLevels.put(6, 16);
		
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.disguise);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.stealth);
		classSkills.add(SkillTab.survival);
		classSkills.add(SkillTab.swim);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16: canCast.put("Sixth", true);
				case 15: 
				case 14:
				case 13: canCast.put("Fifth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Fourth", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Third", true);
				case 6: 
				case 5: 
				case 4: canCast.put("Second", true);
				case 3: 
				case 2: 
				case 1: canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Magus extends CharacterClass {
	
	Magus() {
		sr = 2;
		asw = 140;
		
		castsSpells = true;
		spellAbility = OverviewTab.INT;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 4);
		spellLevels.put(3, 7);
		spellLevels.put(4, 10);
		spellLevels.put(5, 13);
		spellLevels.put(6, 16);
		
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.fly);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.swim);
		classSkills.add(SkillTab.useMagicDevice);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16: canCast.put("Sixth", true);
				case 15: 
				case 14:
				case 13: canCast.put("Fifth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Fourth", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Third", true);
				case 6: 
				case 5: 
				case 4: canCast.put("Second", true);
				case 3: 
				case 2: 
				case 1: canCast.put("Zeroth", true); canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Monk extends CharacterClass {
    
	Monk() {
		sr = 4;
		asw = 35;
		
		classSkills.add(SkillTab.acrobatics);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.escapeArtist);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.history);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.perform1);
		classSkills.add(SkillTab.perform2);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.stealth);
		classSkills.add(SkillTab.swim);
	}
}

class Ninja extends CharacterClass {
	Ninja() {
		sr = 8;
		asw = 140;
		
		classSkills.add(SkillTab.acrobatics);
		classSkills.add(SkillTab.appraise);
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.disableDevice);
		classSkills.add(SkillTab.disguise);
		classSkills.add(SkillTab.escapeArtist);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.local);
		classSkills.add(SkillTab.nobility);
		classSkills.add(SkillTab.linguistics);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.perform1);
		classSkills.add(SkillTab.perform2);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.sleightOfHand);
		classSkills.add(SkillTab.stealth);
		classSkills.add(SkillTab.swim);
		classSkills.add(SkillTab.useMagicDevice);
	}
}

class Oracle extends CharacterClass {
	
	Oracle() {
		sr = 4;
		asw = 105;
		
		castsSpells = true;
		spellAbility = OverviewTab.CHA;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 4);
		spellLevels.put(3, 6);
		spellLevels.put(4, 8);
		spellLevels.put(5, 10);
		spellLevels.put(6, 12);
		spellLevels.put(7, 14);
		spellLevels.put(8, 16);
		spellLevels.put(9, 18);
		
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.history);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.spellcraft);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: canCast.put("Ninth", true);
				case 17: 
				case 16: canCast.put("Eighth", true);
				case 15: 
				case 14: canCast.put("Seventh", true);
				case 13: 
				case 12: canCast.put("Sixth", true);
				case 11: 
				case 10: canCast.put("Fifth", true);
				case 9: 
				case 8: canCast.put("Fourth", true);
				case 7: 
				case 6: canCast.put("Third", true);
				case 5: 
				case 4: canCast.put("Second", true);
				case 3: 
				case 2: 
				case 1: canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Paladin extends CharacterClass {
	
	Paladin() {
		hd = 10;
		sr = 2;
		asw = 175;
		
		castsSpells = true;
		spellAbility = OverviewTab.CHA;
		
		spellLevels.put(1, 4);
		spellLevels.put(2, 7);
		spellLevels.put(3, 10);
		spellLevels.put(4, 13);
		
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.nobility);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.spellcraft);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16:
				case 15: 
				case 14:
				case 13: canCast.put("Fourth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Third", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Second", true);
				case 6: 
				case 5: 
				case 4: canCast.put("First", true);
				case 3: 
				case 2: 
				case 1:
					break;
			}
		}
		return canCast;
	} */
}

class Ranger extends CharacterClass {
	
	Ranger() {
		hd = 10;
		sr = 6;
		asw = 175;
		
		castsSpells = true;
		spellAbility = OverviewTab.WIS;
		
		spellLevels.put(1, 4);
		spellLevels.put(2, 7);
		spellLevels.put(3, 10);
		spellLevels.put(4, 13);
		
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.geography);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.stealth);
		classSkills.add(SkillTab.survival);
		classSkills.add(SkillTab.swim);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16:
				case 15: 
				case 14:
				case 13: canCast.put("Fourth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Third", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Second", true);
				case 6: 
				case 5: 
				case 4: canCast.put("First", true);
				case 3: 
				case 2: 
				case 1:
					break;
			}
		}
		return canCast;
	} */
}

class Rogue extends CharacterClass {
    
	Rogue() {
		sr = 8;
		asw = 140;
		
		classSkills.add(SkillTab.acrobatics);
		classSkills.add(SkillTab.appraise);
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.disableDevice);
		classSkills.add(SkillTab.disguise);
		classSkills.add(SkillTab.escapeArtist);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.local);
		classSkills.add(SkillTab.linguistics);
		classSkills.add(SkillTab.perception);
		classSkills.add(SkillTab.perform1);
		classSkills.add(SkillTab.perform2);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.sleightOfHand);
		classSkills.add(SkillTab.stealth);
		classSkills.add(SkillTab.swim);
		classSkills.add(SkillTab.useMagicDevice);
	}
}

class Samurai extends CharacterClass {
	
	Samurai() {
		hd = 10;
		sr = 4;
		asw = 105;
		
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.climb);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.diplomacy);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.senseMotive);
		classSkills.add(SkillTab.swim);
	}
}

class Sorcerer extends CharacterClass {
	
	Sorcerer() {
		hd = 6;
		sr = 2;
		asw = 70;
		
		castsSpells = true;
		spellAbility = OverviewTab.CHA;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 4);
		spellLevels.put(3, 6);
		spellLevels.put(4, 8);
		spellLevels.put(5, 10);
		spellLevels.put(6, 12);
		spellLevels.put(7, 14);
		spellLevels.put(8, 16);
		spellLevels.put(9, 18);
		
		classSkills.add(SkillTab.appraise);
		classSkills.add(SkillTab.bluff);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.fly);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.useMagicDevice);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: canCast.put("Ninth", true);
				case 17: 
				case 16: canCast.put("Eighth", true);
				case 15: 
				case 14: canCast.put("Seventh", true);
				case 13: 
				case 12: canCast.put("Sixth", true);
				case 11: 
				case 10: canCast.put("Fifth", true);
				case 9: 
				case 8: canCast.put("Fourth", true);
				case 7: 
				case 6: canCast.put("Third", true);
				case 5: 
				case 4: canCast.put("Second", true);
				case 3: 
				case 2: 
				case 1: canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Summoner extends CharacterClass {
	
	Summoner() {
		sr = 2;
		asw = 70;
		
		castsSpells = true;
		spellAbility = OverviewTab.CHA;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 4);
		spellLevels.put(3, 7);
		spellLevels.put(4, 10);
		spellLevels.put(5, 13);
		spellLevels.put(6, 16);
		
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.fly);
		classSkills.add(SkillTab.handleAnimal);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.engineering);
		classSkills.add(SkillTab.geography);
		classSkills.add(SkillTab.history);
		classSkills.add(SkillTab.local);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.nobility);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.linguistics);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.ride);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.useMagicDevice);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: 
				case 16: canCast.put("Sixth", true);
				case 15: 
				case 14:
				case 13: canCast.put("Fifth", true);
				case 12: 
				case 11: 
				case 10: canCast.put("Fourth", true);
				case 9: 
				case 8: 
				case 7: canCast.put("Third", true);
				case 6: 
				case 5: 
				case 4: canCast.put("Second", true);
				case 3: 
				case 2: 
				case 1: canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Wizard extends CharacterClass {
	
	Wizard() {
		hd = 6;
		sr = 2;
		asw = 70;
		
		castsSpells = true;
		spellAbility = OverviewTab.INT;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 3);
		spellLevels.put(3, 5);
		spellLevels.put(4, 7);
		spellLevels.put(5, 9);
		spellLevels.put(6, 11);
		spellLevels.put(7, 13);
		spellLevels.put(8, 15);
		spellLevels.put(9, 17);
		
		classSkills.add(SkillTab.appraise);
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.fly);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.dungeon);
		classSkills.add(SkillTab.engineering);
		classSkills.add(SkillTab.geography);
		classSkills.add(SkillTab.history);
		classSkills.add(SkillTab.local);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.nobility);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.religion);
		classSkills.add(SkillTab.linguistics);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.spellcraft);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: canCast.put("Ninth", true);
				case 16: 
				case 15: canCast.put("Eighth", true);
				case 14:
				case 13: canCast.put("Seventh", true);
				case 12: 
				case 11: canCast.put("Sixth", true);
				case 10: 
				case 9: canCast.put("Fifth", true);
				case 8: 
				case 7: canCast.put("Fourth", true);
				case 6: 
				case 5: canCast.put("Third", true);
				case 4: 
				case 3: canCast.put("Second", true);
				case 2: 
				case 1: canCast.put("Zeroth", true); canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}

class Witch extends CharacterClass {
	
	Witch() {
		hd = 6;
		sr = 2;
		asw = 105;
		
		castsSpells = true;
		spellAbility = OverviewTab.INT;
		
		spellLevels.put(0, 1);
		spellLevels.put(1, 1);
		spellLevels.put(2, 3);
		spellLevels.put(3, 5);
		spellLevels.put(4, 7);
		spellLevels.put(5, 9);
		spellLevels.put(6, 11);
		spellLevels.put(7, 13);
		spellLevels.put(8, 15);
		spellLevels.put(9, 17);
		
		classSkills.add(SkillTab.craft1);
		classSkills.add(SkillTab.craft2);
		classSkills.add(SkillTab.craft3);
		classSkills.add(SkillTab.fly);
		classSkills.add(SkillTab.heal);
		classSkills.add(SkillTab.intimidate);
		classSkills.add(SkillTab.arcana);
		classSkills.add(SkillTab.history);
		classSkills.add(SkillTab.nature);
		classSkills.add(SkillTab.planes);
		classSkills.add(SkillTab.profession1);
		classSkills.add(SkillTab.profession2);
		classSkills.add(SkillTab.spellcraft);
		classSkills.add(SkillTab.useMagicDevice);
	}
	
	/*
	HashMap<String, Boolean> getSpellLevels(int classLevel) {
		if (classLevel < 1 || classLevel > 20) {
			System.out.println("Invalid level");
		}
		else {
			switch (classLevel) {
				case 20: 
				case 19: 
				case 18: 
				case 17: canCast.put("Ninth", true);
				case 16: 
				case 15: canCast.put("Eighth", true);
				case 14:
				case 13: canCast.put("Seventh", true);
				case 12: 
				case 11: canCast.put("Sixth", true);
				case 10: 
				case 9: canCast.put("Fifth", true);
				case 8: 
				case 7: canCast.put("Fourth", true);
				case 6: 
				case 5: canCast.put("Third", true);
				case 4: 
				case 3: canCast.put("Second", true);
				case 2: 
				case 1: canCast.put("Zeroth", true); canCast.put("First", true);
					break;
			}
		}
		return canCast;
	} */
}
/*
class MultiClass extends CharacterClass {
    
}
*/
class OtherClass extends CharacterClass {
    
} 
