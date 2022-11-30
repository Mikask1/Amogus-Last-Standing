package character;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.imageio.ImageIO;

import bullet.Bullet;
import main.GamePanel;

public class Character {
	GamePanel gp;

	public int worldX, worldY;
	public int x, y;
	public int speed;
	public int shoot_speed;
	public long shoot_timer;
	public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
	public BufferedImage up_shoot, up1_shoot, up2_shoot, down_shoot, down1_shoot, down2_shoot, left_shoot, left1_shoot, left2_shoot, right_shoot, right1_shoot, right2_shoot;
	public Image bullet_up, bullet_down ,bullet_left ,bullet_right;
	public String direction;
	
	public Vector<Bullet> bullets = new Vector<Bullet>();
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle footArea;
	public Rectangle solidArea;
	public boolean collisionOn = false;
}
