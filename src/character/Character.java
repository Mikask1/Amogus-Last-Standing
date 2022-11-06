package character;

import java.awt.image.BufferedImage;

import main.GamePanel;

public abstract class Character {
	GamePanel gp;
	
	public int x,y;
	public int speed;
	
	public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Character(GamePanel gp) {
		this.gp = gp;
	}
	
	public abstract void shoot();
	public abstract void update();
}
