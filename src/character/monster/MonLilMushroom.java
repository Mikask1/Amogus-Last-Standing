package character.monster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import bullet.Bullet;
import main.GamePanel;

public class MonLilMushroom extends Monster {

	Random rand = new Random();
	String tempDir = "left";
	
	int playerBulletDimension1 = 4;
	int playerBulletDimension2 = 13;

	public MonLilMushroom(GamePanel gp) {
		super(gp);
		size = 90;
		setDefaultValues();
		getImage();
		setAction();
	}
	
	private void setDefaultValues() {
		setSpeed(1);
		setShootSpeed(1);
		setHealth(50);
		setBodyDamage(0);
		direction = "left";

		worldX = rand.nextInt(-200, 200) + gp.player.screenX;
		worldY = rand.nextInt(-200, 200) + gp.player.screenY;

		solidArea = new Rectangle();
		solidArea.x = 20;
		solidArea.y = 15;
		solidArea.width = 40;
		solidArea.height = 58;

		footArea = new Rectangle();
		footArea.x = 28;
		footArea.y = 60;
		footArea.width = 30;
		footArea.height = 15;
	}

	public void getImage() {

		try {
			monLeft = ImageIO.read(getClass().getResourceAsStream("/monster/lilleft.png"));
			monLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/lilleft1.png"));
			monLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/lilleft2.png"));
			monLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/lilleft3.png"));
			monRight = ImageIO.read(getClass().getResourceAsStream("/monster/lilright.png"));
			monRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/lilright1.png"));
			monRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/lilright2.png"));
			monRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/lilright3.png"));
			hurtLeft = ImageIO.read(getClass().getResourceAsStream("/monster/hlilleft.png"));
			hurtLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/hlilleft1.png"));
			hurtLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/hlilleft2.png"));
			hurtLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/hlilleft3.png"));
			hurtRight = ImageIO.read(getClass().getResourceAsStream("/monster/hlilright.png"));
			hurtRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/hlilright1.png"));
			hurtRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/hlilright2.png"));
			hurtRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/hlilright3.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAction() {
		if (worldX < gp.player.worldX - gp.tileSize)
			direction = "right";
		if (worldX > gp.player.worldX)
			direction = "left";

	}

	public void update() {
		collisionOn = false;
		gp.cChecker.insideMap(this);

		if (collisionOn == false) {
			switch (direction) {

			case "left":
				if (worldX != gp.player.worldX) {
					worldX -= getSpeed();
				}
				if (worldY < gp.player.worldY - 25) {
					worldY += getSpeed();
				} else {
					worldY -= getSpeed();
				}
				break;

			case "right":
				if (worldX != gp.player.worldX - gp.tileSize)
					worldX += getSpeed();
				if (worldY <= gp.player.worldY - 25) {
					worldY += getSpeed();
				} else {
					worldY -= getSpeed();
				}
				break;
			}
		}

		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 3;
			} else if (spriteNum == 3) {
				spriteNum = 4;
			} else if (spriteNum == 4) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}

		if (getHealth() <= 0) {
			alive = false;
		}

		if (hurt == true) {
			hurtCounter++;
			direction = "hurt";
			if (hurtCounter > 50) {
				hurt = false;
				hurtCounter = 0;
			}
		}
		
		if(Math.abs(worldX - gp.player.worldX) < 500) {
			if ((gp.stopwatch - shoot_timer) >= 1000000000 / getShootSpeed()) {
				switch (direction) {
				case "left":
					Bullet newBullet2 = new Bullet(gp, this, direction, playerBulletDimension2, playerBulletDimension1, worldX, worldY + gp.tileSize);
					monBullets.add(newBullet2);
					break;

				case "right":
					Bullet newBullet3 = new Bullet(gp, this, direction, playerBulletDimension2, playerBulletDimension1, worldX + gp.tileSize/2 + 4, worldY + gp.tileSize);
					monBullets.add(newBullet3);
					break;

				}
				shoot_timer = gp.stopwatch;
			}
		}
		

	}

	public void draw(Graphics2D g2) {
		Image image = null;

		switch (direction) {
		case "left":
			tempDir = "left";
			if (spriteNum == 1) {
				image = monLeft;
			}
			if (spriteNum == 2) {
				image = monLeft1;
			}
			if (spriteNum == 3) {
				image = monLeft2;
			}
			if (spriteNum == 4) {
				image = monLeft3;
			}
			if (spriteNum == 5) {
				image = monLeft4;
			}
			break;

		case "right":
			tempDir = "right";
			if (spriteNum == 1) {
				image = monRight;
			}
			if (spriteNum == 2) {
				image = monRight1;
			}
			if (spriteNum == 3) {
				image = monRight2;
			}
			if (spriteNum == 4) {
				image = monRight3;
			}
			if (spriteNum == 5) {
				image = monRight4;
			}
			break;

		case "hurt":
			if (tempDir == "left") {
				if (spriteNum == 1) {
					image = hurtLeft;
				}
				if (spriteNum == 2) {
					image = hurtLeft1;
				}
				if (spriteNum == 3) {
					image = hurtLeft2;
				}
				if (spriteNum == 4) {
					image = hurtLeft3;
				}
				direction = "left";
			}
			if (tempDir == "right") {
				if (spriteNum == 1) {
					image = hurtRight;
				}
				if (spriteNum == 2) {
					image = hurtRight1;
				}
				if (spriteNum == 3) {
					image = hurtRight2;
				}
				if (spriteNum == 4) {
					image = hurtRight3;
				}
				direction = "right";
			}
			break;

		}
		g2.drawImage(image, this.gp.screenX + worldX, this.gp.screenY + worldY, size, size, null);
	}
	
	public void drawBullets(Graphics2D g2) {
		for (Bullet bullet : monBullets) {

			if(this.worldY == gp.player.worldY){
				if(this.worldX < gp.player.worldX) {
					g2.drawImage(bullet_left, bullet.worldX+gp.tileSize, bullet.worldY+gp.tileSize, 20, 20, null);
				}
				
				if(this.worldX > gp.player.worldX) {
					g2.drawImage(bullet_right, bullet.worldX+gp.tileSize, bullet.worldY+gp.tileSize, 20, 20, null);
				}

			}
			
			

			g2.setColor(Color.white);
			g2.drawRect(gp.screenX + bullet.worldX + bullet.solidArea.x, gp.screenY + bullet.worldY + bullet.solidArea.y,
					bullet.solidArea.width, bullet.solidArea.height);
		}
	}
	
}
