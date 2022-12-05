package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.desktop.ScreenSleepEvent;
import java.util.Vector;

import bullet.Bullet;
import character.Character;
import character.Player;

public class CollisionChecker {
	GamePanel gp;
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void insideMap(Character character) {
		int worldXNext = character.gp.screenX + character.worldX + character.footArea.x;
		int width = character.footArea.width;
		int worldYNext = character.gp.screenY + character.worldY + character.footArea.y;
		int height = character.footArea.height;
		
		switch (character.direction) {
		case "up":
			if (!gp.map.inside(worldXNext, worldYNext - character.getSpeed() - 1, width, height)) {
				character.collisionOn = true;
			}
			break;
		case "down":
			if (!gp.map.inside(worldXNext, worldYNext + character.getSpeed() + 1, width, height)) {
				character.collisionOn = true;
			}
			break;
		case "left":
			if (!gp.map.inside(worldXNext - character.getSpeed() - 1, worldYNext, width, height)) {
				character.collisionOn = true;
			}
			break;
		case "right":
			if (!gp.map.inside(worldXNext + character.getSpeed() + 1, worldYNext, width, height)) {
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
