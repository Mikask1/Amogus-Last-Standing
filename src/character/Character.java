package character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

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
	
	public boolean onFire = false;
	public long onFireDuration = 0;
	protected long fireTimer;
	public int fireCounter = 0;
	protected final long fireInterval = 1000;
	
	public Character(GamePanel gp) {
		this.gp = gp;
		
		// Default bullet image
		try {
			bullet_up = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_up.png")).getScaledInstance(4, 13,
					Image.SCALE_DEFAULT);
			bullet_down = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_down.png")).getScaledInstance(4,
					13, Image.SCALE_DEFAULT);
			bullet_left = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_left.png")).getScaledInstance(13,
					4, Image.SCALE_DEFAULT);
			bullet_right = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_right.png"))
					.getScaledInstance(13, 4, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAction() {

	}

	public void update() {
//		setAction();
	}

	public void draw(Graphics2D g2) {

	}

	public void drawBullets(Graphics2D g2) {

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
}
