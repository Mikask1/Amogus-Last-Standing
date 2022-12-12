package character.monster;

import java.awt.Image;

import character.Character;
import main.GamePanel;

public abstract class Monster extends Character{
	public Image monLeft, monLeft1, monLeft2, monLeft3, monLeft4, monRight, monRight1, monRight2, monRight3,
	monRight4;
	public Image hurtLeft, hurtLeft1, hurtLeft2, hurtLeft3, hurtLeft4, hurtRight, hurtRight1, hurtRight2, hurtRight3,
	hurtRight4;
	
	public Monster(GamePanel gp) {
		super(gp);
	}
}