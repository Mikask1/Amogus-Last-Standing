package main;

import java.awt.Rectangle;

import bullet.Bullet;
import character.Character;
import character.Player;
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
		
		if (character.direction == "up") {
			if (!gp.map.inside(worldXNext, worldYNext - character.getSpeed(), width, height)) {
				character.collisionOn = true;
			}
		}
		
		if (character.direction == "down") {
			if (!gp.map.inside(worldXNext, worldYNext + character.getSpeed(), width, height)) {
				character.collisionOn = true;
			}
		}
		if (character.direction == "left") {
			if (!gp.map.inside(worldXNext - character.getSpeed(), worldYNext, width, height)) {
				character.collisionOn = true;
			}
		}
		if (character.direction == "right") {
			if (!gp.map.inside(worldXNext + character.getSpeed(), worldYNext, width, height)) {
				character.collisionOn = true;
			}
		}
	}

	public void checkBulletHitsMonster(Character monster) {

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
				monster.damageHealth(bullet.damage);
			}
			
			bullet = null;
		}
		
		monsterSolidArea = null;
	}
	
	public void monsterBodyHitPlayer(Player player, Monster monster) {
		
		Rectangle monsterSolidArea = new Rectangle(gp.screenX + monster.worldX + monster.solidArea.x,
				gp.screenY + monster.worldY + monster.solidArea.y, monster.solidArea.width, monster.solidArea.height);
		
		Rectangle playerSolidArea = new Rectangle(player.screenX + player.solidArea.x, player.screenY + player.solidArea.y,
				player.solidArea.width, player.solidArea.height);
		
		if (gp.stopwatch - bodyHitTimer >= 500000000) {
			if (playerSolidArea.intersects(monsterSolidArea)) {
				player.damageHealth(monster.getBodyDamage());
				player.hurt = true;
				bodyHitTimer = gp.stopwatch;
				System.out.println("Player health: " + player.getHealth());
			}		
		}
	}
}
