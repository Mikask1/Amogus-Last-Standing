package bullet;
import java.awt.Rectangle;

import character.Character;

public class Bullet {
	Character character;
	public int damage = 1;
	public int bulletSpeed = 6;
	public int worldX;
	public int worldY;
	public int width;
	public int height;
	public String direction;
	public Rectangle solidArea;
	
	public Bullet(Character character, String direction, int width, int height) {
		this.direction = direction;
		this.character = character;
		
		solidArea = new Rectangle(worldX, worldY, width, height);
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
}
