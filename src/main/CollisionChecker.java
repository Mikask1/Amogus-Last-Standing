package main;

import java.awt.Rectangle;
import bullet.Bullet;
import character.Character;
import character.Player;
import character.monster.MonFireBat;
import character.monster.MonIceBat;
import character.monster.Monster;
import main.GamePanel;

public class CollisionChecker {
	GamePanel gp;
	long bodyHitTimer = 0;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void insideMap(Character character) {
		
		int worldXNext = character.gp.screenX + character.worldX + character.footArea.x;
		int width = character.footArea.width;
		int worldYNext = character.gp.screenY + character.worldY + character.footArea.y;
		int height = character.footArea.height;

		if (character.direction.contains("up")) {
			if (!gp.map.inside(worldXNext, worldYNext - character.getSpeed(), width, height)) {
				character.collisionOn = true;
			}
		}
		if (character.direction.contains("down")) {
			if (!gp.map.inside(worldXNext, worldYNext + character.getSpeed(), width, height)) {
				character.collisionOn = true;
			}
		}
		if (character.direction.contains("left")) {
			if (!gp.map.inside(worldXNext - character.getSpeed(), worldYNext, width, height)) {
				character.collisionOn = true;
			}
		}
		if (character.direction.contains("right")) {
			if (!gp.map.inside(worldXNext + character.getSpeed(), worldYNext, width, height)) {
				character.collisionOn = true;
			}
		}
	}

	public void checkBulletHitsMonster(Monster monster) {

		Rectangle monsterSolidArea = new Rectangle(gp.screenX + monster.worldX + monster.solidArea.x,
				gp.screenY + monster.worldY + monster.solidArea.y, monster.solidArea.width, monster.solidArea.height);

		for (int i = 0; i < this.gp.player.bullets.size(); i++) {
			Bullet bullet = this.gp.player.bullets.get(i);
			Rectangle bulletSolidArea = new Rectangle(gp.screenX + bullet.worldX + bullet.solidArea.x,
					gp.screenY + bullet.worldY + bullet.solidArea.y, bullet.solidArea.width, bullet.solidArea.height);
			boolean hits = bulletSolidArea.intersects(monsterSolidArea);

			if (hits) {
				monster.hurt = true;
				this.gp.player.bullets.remove(i);
				monster.damageHealth(bullet.getDamage());
			}
			
			bullet = null;
		}

		monsterSolidArea = null;
	}
	
	public void checkBulletHitsPlayer(Monster monster) {
		Rectangle playerSolidArea = new Rectangle(gp.player.screenX + gp.player.solidArea.x,
				gp.player.screenY + gp.player.solidArea.y, gp.player.solidArea.width, gp.player.solidArea.height);
		
		for (int i = 0; i < monster.monBullets.size(); i++) {
			Bullet bullet = monster.monBullets.get(i);
			Rectangle bulletSolidArea = new Rectangle(gp.screenX + bullet.worldX + bullet.solidArea.x,
					gp.screenY + bullet.worldY + bullet.solidArea.y, bullet.solidArea.width, bullet.solidArea.height);
			boolean hits = bulletSolidArea.intersects(playerSolidArea);

			if (hits) {
				gp.player.hurt = true;
				gp.playSE(3);
				gp.player.damageHealth(bullet.getDamage());
				if (bullet.character instanceof MonFireBat) {
					gp.player.onFire = true;
					gp.player.fireCounter = 0;
					gp.player.onFireDuration = MonFireBat.fireDuration;
				}
				else if (bullet.character instanceof MonIceBat) {
					gp.player.freeze = true;
					gp.player.freezeCounter = 0;
					gp.player.freezeDuration = MonIceBat.freezeDuration;
				}
				monster.monBullets.remove(i);
			}
			
			bullet = null;
		}
		
		playerSolidArea = null;
	}

	public void monsterBodyHitPlayer(Player player, Monster monster) {

		Rectangle monsterSolidArea = new Rectangle(gp.screenX + monster.worldX + monster.solidArea.x,
				gp.screenY + monster.worldY + monster.solidArea.y, monster.solidArea.width, monster.solidArea.height);

		Rectangle playerSolidArea = new Rectangle(player.screenX + player.solidArea.x,
				player.screenY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

		if (gp.stopwatch - bodyHitTimer >= 500000000) {
			if (playerSolidArea.intersects(monsterSolidArea)) {
				gp.playSE(3);
				player.damageHealth(monster.getBodyDamage());
				player.hurt = true;
				bodyHitTimer = gp.stopwatch;
			}
		}
	}

//	public void monsterCollideMonster(Monster enemy) {
//		enemy.collisionDown = false;
//		enemy.collisionUp = false;
//		enemy.collisionLeft = false;
//		enemy.collisionRight = false;
//
//		int worldXNext = enemy.gp.screenX + enemy.worldX + enemy.footArea.x;
//		int width = enemy.footArea.width;
//		int worldYNext = enemy.gp.screenY + enemy.worldY + enemy.footArea.y;
//		int height = enemy.footArea.height;
//
//		for (Monster enemy2 : gp.monsters) {
//			if (enemy2 == enemy) {
//				continue;
//			}
//			
//			Rectangle enemy2FootArea = new Rectangle(gp.screenX + enemy2.worldX + enemy2.footArea.x,
//					gp.screenY + enemy2.worldY + enemy2.footArea.y, enemy2.footArea.width, enemy2.footArea.height);
//
//			if (enemy.direction.contains("up")) {
//				if (enemy2FootArea.intersects(worldXNext, worldYNext - enemy.getSpeed() - 1, width, height)) {
//					enemy.collisionUp = true;
//				}
//			}
//			if (enemy.direction.contains("down")) {
//				if (enemy2FootArea.intersects(worldXNext, worldYNext + enemy.getSpeed() + 1, width, height)) {
//					enemy.collisionDown = true;
//				}
//			}
//			if (enemy.direction.contains("left")) {
//				if (enemy2FootArea.intersects(worldXNext - enemy.getSpeed() - 1, worldYNext, width, height)) {
//					enemy.collisionLeft = true;
//				}
//			}
//			if (enemy.direction.contains("right")) {
//				if (enemy2FootArea.intersects(worldXNext + enemy.getSpeed() + 1, worldYNext, width, height)) {
//					enemy.collisionRight = true;
//				}
//			}
//		}
//	}
}
