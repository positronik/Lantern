package positronix.lantern.tabs;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import positronix.lantern.Main;
import positronix.lantern.Skill;
import positronix.lantern.TypeSkill;

//TODO: Consider passing objects of Ability directly into Skill constructor to keep itself updated

public class SkillTab {
	public int strMod , dexMod , 
			conMod, intMod, 
			wisMod, chaMod;
	static Scene scene = Main.scene;
	public static int skillPoints = 0;
	
	public static Skill acrobatics, appraise, bluff, climb, diplomacy, disableDevice,
					disguise, escapeArtist, fly, handleAnimal, heal, intimidate,
					arcana, dungeon, engineering, geography, history, local,
					nature, nobility, planes, religion, linguistics, perception,
					ride, senseMotive, sleightOfHand, spellcraft, stealth,
					survival, swim, useMagicDevice;
	
	public static TypeSkill craft1, craft2, craft3,
						perform1, perform2,
						profession1, profession2;
	
	public static ArrayList<Skill> allSkills;
	
	public static TextField unspentRanks = (TextField) scene.lookup("#unspentRanks");
	
	public SkillTab(){
		allSkills = new ArrayList<>();
		
		strMod = OverviewTab.STR.mod;
		dexMod = OverviewTab.DEX.mod;
		conMod = OverviewTab.CON.mod;
		intMod = OverviewTab.INT.mod;
		wisMod = OverviewTab.WIS.mod;
		chaMod = OverviewTab.CHA.mod;
		
		unspentRanks.setText("0");
		
		allSkills.add(acrobatics = new Skill("acrobatics", dexMod));
		allSkills.add(appraise = new Skill("appraise", intMod));
		allSkills.add(bluff = new Skill("bluff", chaMod));
		allSkills.add(climb = new Skill("climb", dexMod));
		allSkills.add(craft1 = new TypeSkill("craft1", intMod));
		allSkills.add(craft2 = new TypeSkill("craft2", intMod));
		allSkills.add(craft3 = new TypeSkill("craft3", intMod));
		allSkills.add(diplomacy = new Skill("diplomacy", chaMod));
		allSkills.add(disableDevice = new Skill("disable", dexMod));
		allSkills.add(disguise = new Skill("disguise", chaMod));
		allSkills.add(escapeArtist = new Skill("escape", dexMod));
		allSkills.add(fly = new Skill("fly", dexMod));
		allSkills.add(handleAnimal = new Skill("handle", chaMod));
		allSkills.add(heal = new Skill("heal", wisMod));
		allSkills.add(intimidate = new Skill("intimidate", chaMod));
		allSkills.add(arcana = new Skill("arcana", intMod));
		allSkills.add(dungeon = new Skill("dungeon", intMod));
		allSkills.add(engineering = new Skill("engineering", intMod));
		allSkills.add(geography = new Skill("geography", intMod));
		allSkills.add(history = new Skill("history", intMod));
		allSkills.add(local = new Skill("local", intMod));
		allSkills.add(nature = new Skill("nature", intMod));
		allSkills.add(nobility = new Skill("nobility", intMod));
		allSkills.add(planes = new Skill("planes", intMod));
		allSkills.add(religion = new Skill("religion", intMod));
		allSkills.add(linguistics = new Skill("linguistics", intMod));
		allSkills.add(perception = new Skill("perception", wisMod));
		allSkills.add(perform1 = new TypeSkill("perform1", chaMod));
		allSkills.add(perform2 = new TypeSkill("perform2", chaMod));
		allSkills.add(profession1 = new TypeSkill("profession1", wisMod));
		allSkills.add(profession2 = new TypeSkill("profession2", wisMod));
		allSkills.add(ride = new Skill("ride", dexMod));
		allSkills.add(senseMotive = new Skill("sense", wisMod));
		allSkills.add(sleightOfHand = new Skill("sleight", dexMod));
		allSkills.add(spellcraft = new Skill("spellcraft", intMod));
		allSkills.add(stealth = new Skill("stealth", dexMod));
		allSkills.add(survival = new Skill("survival", wisMod));
		allSkills.add(swim = new Skill("swim", strMod));
		allSkills.add(useMagicDevice = new Skill("umd", chaMod));
	}
	
	public static void refreshPoints(){
		 unspentRanks = (TextField) scene.lookup("#unspentRanks");
	}
	
}
