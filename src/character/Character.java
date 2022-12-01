package character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.imageio.ImageIO;

import bullet.Bullet;
import main.GamePanel;

public class Character {
	public GamePanel gp;

	public int worldX;
	public int worldY;
	public int x, y;
	public int speed;
	public int shoot_speed;
	public long shoot_timer;
	public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
	public BufferedImage up_shoot, up1_shoot, up2_shoot, down_shoot, down1_shoot, down2_shoot, left_shoot, left1_shoot, left2_shoot, right_shoot, right1_shoot, right2_shoot;
	public Image bullet_up, bullet_down ,bullet_left ,bullet_right;
	public BufferedImage monLeft, monLeft1, monLeft2, monLeft3, monLeft4, monRight, monRight1, monRight2, monRight3, monRight4;
	public String direction;
	public int actionLockCounter = 0;
	
	public Vector<Bullet> bullets = new Vector<Bullet>();
	
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
	
}
