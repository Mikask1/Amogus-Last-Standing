package character;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public abstract class Character {
	GamePanel gp;
	
	public int worldX, worldY;
	public int x,y;
	public int speed;
	
	public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea;
	public boolean collisionOn = false;
	
	public abstract void shoot();
	public abstract void update();
}
