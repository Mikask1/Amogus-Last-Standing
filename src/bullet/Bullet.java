package bullet;

import java.awt.Rectangle;

import character.Character;
import character.monster.Monster;
import main.GamePanel;

public class Bullet {
	public Character character;
	
	private int damage;
	private int bulletSpeed = 7;
	
	public int width;
	public int height;
	public int worldX;
	public int worldY;
	
	private String direction;
	public Rectangle solidArea;
	protected GamePanel gp;

	public Bullet(GamePanel gp, Character character, String direction, int width, int height, int worldX, int worldY, int damage) {
		this.gp = gp;
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.character = character;

		this.width = width;
		this.height = height;
		
		this.damage = damage;
		solidArea = new Rectangle(0, 0, this.width, this.height);
	}

	public void update() {
		switch (direction) {
		case "up":
			worldY -= bulletSpeed;
			break;
		case "down":
			worldY += bulletSpeed;
			break;
		case "left":
		case "left up":
		case "left down":
			worldX -= bulletSpeed;
			break;
		case "right":
		case "right up":
		case "right down":
			worldX += bulletSpeed;
			break;
		}
	}

	public String getDirection() {
		return direction;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
