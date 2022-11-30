package main;

import character.Character;

public class CollisionChecker {
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Character character) {
		int leftWorldX = character.worldX + character.footArea.x;
		int rightWorldX = character.worldX + character.footArea.x + character.footArea.width;
		int topWorldY = character.worldY + character.footArea.y;
		int bottomWorldY = character.worldY + character.footArea.y + character.footArea.height;

		int leftNextX = leftWorldX;
		int rightNextX = rightWorldX;

		int topNextY = topWorldY;
		int bottomNextY = topWorldY;

		switch (character.direction) {
		case "up":
			topNextY = topWorldY - character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		case "down":
			bottomNextY = bottomWorldY + character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		case "left":
			leftNextX = leftWorldX - character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		case "right":
			rightNextX = rightWorldX + character.speed;
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		}
	}
}

