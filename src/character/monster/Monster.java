package character.monster;

import java.awt.image.BufferedImage;
import java.util.Vector;

import bullet.Bullet;
import character.Character;
import main.GamePanel;

public abstract class Monster extends Character{
	public BufferedImage monLeft, monLeft1, monLeft2, monLeft3, monLeft4, monRight, monRight1, monRight2, monRight3,
	monRight4;
	public BufferedImage hurtLeft, hurtLeft1, hurtLeft2, hurtLeft3, hurtLeft4, hurtRight, hurtRight1, hurtRight2, hurtRight3,
	hurtRight4;
	
	private int bodyDamage;
	
	public Vector<Bullet> monBullets = new Vector<Bullet>();
	
	public boolean collisionLeft = false;
	public boolean collisionRight = false;
	public boolean collisionUp = false;
	public boolean collisionDown = false;
	
	public static int randomSpawnRadius = 200;

	public abstract void setAction();
	
	public Monster(GamePanel gp) {
		super(gp);
	}
	
	public int getBodyDamage() {
		return bodyDamage;
	}

	public void setBodyDamage(int bodyDamage) {
		this.bodyDamage = bodyDamage;
	}
}
