package character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

import bullet.Bullet;
import main.GamePanel;

public abstract class Character {
	public GamePanel gp;
	
	public int worldX;
	public int worldY;
	protected int size = 64;
	public int x, y;
	public String direction;
	public int actionLockCounter = 0;
	
	private int speed;
	private int shootSpeed;
	private int health;
	private int bodyDamage;
	
	public boolean alive = true;
	public boolean hurt = false;
	public int hurtCounter = 0;
	
	public Vector<Bullet> bullets = new Vector<Bullet>();

	public long shoot_timer;
	public Image bullet_up, bullet_down, bullet_left, bullet_right;

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle footArea;
	public Rectangle solidArea;
	public boolean collisionOn = false;

	public Character(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {

	}

	public void update() {
//		setAction();
	}

	public void draw(Graphics2D g2) {

	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getShootSpeed() {
		return shootSpeed;
	}

	public void setShootSpeed(int shootSpeed) {
		this.shootSpeed = shootSpeed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int damageHealth(int damage) {
		this.health -= damage;
		return this.health;
	}

	public int getBodyDamage() {
		return bodyDamage;
	}

	public void setBodyDamage(int bodyDamage) {
		this.bodyDamage = bodyDamage;
	}

}
