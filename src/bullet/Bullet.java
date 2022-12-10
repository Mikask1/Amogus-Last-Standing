package bullet;

import java.awt.Rectangle;

import character.Character;
import character.monster.Monster;
import main.GamePanel;

public class Bullet {
	Character character;
	
	public int damage = 5;
	private int bulletSpeed = 6;
	
	public int width;
	public int height;
	public int worldX;
	public int worldY;
	
	private String direction;
	public Rectangle solidArea;
	protected GamePanel gp;

	public Bullet(GamePanel gp, Character character, String direction, int width, int height, int worldX, int worldY) {
		this.gp = gp;
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.character = character;

		this.width = width;
		this.height = height;

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
			worldX -= bulletSpeed;
			break;

		case "right":
			worldX += bulletSpeed;
			break;
		}
	}

	public String getDirection() {
		return direction;
	}
}
