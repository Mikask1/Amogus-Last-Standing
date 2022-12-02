package main;

import java.awt.Rectangle;
import java.util.Vector;

import bullet.Bullet;
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
			topNextY = topWorldY - character.getSpeed();
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		case "down":
			bottomNextY = bottomWorldY + character.getSpeed();
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		case "left":
			leftNextX = leftWorldX - character.getSpeed();
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		case "right":
			rightNextX = rightWorldX + character.getSpeed();
			if (!gp.map.inside(topNextY, bottomNextY, leftNextX, rightNextX)) {
				character.collisionOn = true;
			}
			break;
		}
	}

	public void checkBulletHitsEnemy(Character enemy) {

		Rectangle enemySolidArea = new Rectangle(gp.screenX + enemy.worldX + enemy.solidArea.x,
				gp.screenY + enemy.worldY + enemy.solidArea.y, enemy.solidArea.width, enemy.solidArea.height);

		for (int i = 0; i < this.gp.player.bullets.size(); i++) {
			Bullet bullet = this.gp.player.bullets.get(i);
			Rectangle bulletSolidArea = new Rectangle(gp.screenX + bullet.worldX + bullet.solidArea.x,
					gp.screenY + bullet.worldY + bullet.solidArea.y, bullet.solidArea.width, bullet.solidArea.height);
			boolean hits = bulletSolidArea.intersects(enemySolidArea);
			
			if (hits) {
				enemy.hurt = true;
				this.gp.player.bullets.remove(i);
				enemy.damageHealth(bullet.damage);
				System.out.print("Enemy health: ");
				System.out.println(enemy.getHealth());
			}
			
			bullet = null;
		}
		
		enemySolidArea = null;
	}
}
