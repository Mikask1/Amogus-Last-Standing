package main;

import character.Character;

public class CollisionChecker {
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Character character) {
		int leftWorldX = character.worldX + character.solidArea.x;
		int rightWorldX = character.worldX + character.solidArea.x + character.solidArea.width;
		int topWorldY = character.worldY + character.solidArea.y;
		int bottomWorldY = character.worldY + character.solidArea.y + character.solidArea.height;

		int leftNextX = leftWorldX;
		int rightNextX = rightWorldX;

		int topNextY = topWorldY;
		int bottomNextY = topWorldY;

		switch (character.direction) {
		case "up":
			topNextY = topWorldY - character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
				System.out.println("top");
			}
			break;
		case "down":
			bottomNextY = bottomWorldY + character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
				System.out.println("bottom");
			}
			break;
		case "left":
			leftNextX = leftWorldX - character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
				System.out.println("left");
			}
			break;
		case "right":
			rightNextX = rightWorldX + character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
				System.out.println("right");
			}
			break;
		}
	}
}
