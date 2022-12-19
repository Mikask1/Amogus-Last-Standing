package character.monster;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import bullet.Bullet;
import main.GamePanel;
import main.Sound;

public class MonIceBat extends Monster{

	Random rand = new Random();
	String tempDir = "left";
	private final int playerDetectionOffset = 0;

	int bulletDimension = 10;
	public static long freezeDuration = 100;
	
	public static int DefaultHealth = 20;
	public static int DefaultBodyDamage = 2;
	public static int DefaultBulletDamage = 1;
	public static int DefaultSpeed = 1;

	public MonIceBat(GamePanel gp) {
		super(gp);
		size = 64;
		setDefaultValues();
		getImage();
		setAction();
	}

	private void setDefaultValues() {
		setSpeed(DefaultSpeed);
		setShootSpeed(1);
		setHealth(DefaultHealth);
		setBodyDamage(DefaultBodyDamage);
		setBulletDamage(DefaultBulletDamage);
		direction = "left";

		worldX = rand.nextInt(-Monster.randomSpawnRadius, Monster.randomSpawnRadius) + gp.player.screenX;
		worldY = rand.nextInt(-Monster.randomSpawnRadius, Monster.randomSpawnRadius) + gp.player.screenY;

		solidArea = new Rectangle();
		solidArea.x = 10;
		solidArea.y = 15;
		solidArea.width = 50;
		solidArea.height = 30;

		footArea = new Rectangle();
		footArea.x = 20;
		footArea.y = 45;
		footArea.width = 30;
		footArea.height = 15;

		try {
			bullet_left = ImageIO.read(getClass().getResourceAsStream("/bullets/IceBall.png")).getScaledInstance(16,
					16, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getImage() {

		try {
			monLeft = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatleft.png"));
			monLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatleft1.png"));
			monLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatleft2.png"));
			monLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatleft3.png"));
			monRight = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatright.png"));
			monRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatright1.png"));
			monRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatright2.png"));
			monRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/iceBatright3.png"));
			hurtLeft = ImageIO.read(getClass().getResourceAsStream("/monster/hbatleft.png"));
			hurtLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/hbatleft1.png"));
			hurtLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/hbatleft2.png"));
			hurtLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/hbatleft3.png"));
			hurtRight = ImageIO.read(getClass().getResourceAsStream("/monster/hbatright.png"));
			hurtRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/hbatright1.png"));
			hurtRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/hbatright2.png"));
			hurtRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/hbatright3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAction() {
		if (worldX < gp.player.worldX - gp.tileSize) {
			direction = "right";
			if (worldY < gp.player.worldY - (int) (gp.player.solidArea.height / 2) - playerDetectionOffset) {
				direction = "right down";
			} else {
				direction = "right up";
			}
		}
		if (worldX > gp.player.worldX) {
			direction = "left";
			if (worldY < gp.player.worldY - (int) (gp.player.solidArea.height / 2) - playerDetectionOffset) {
				direction = "left down";
			} else {
				direction = "left up";
			}
		}
	}

	public void update() {
		collisionOn = false;
		gp.cChecker.insideMap(this);

		if (collisionOn == false) {
			switch (direction) {
			case "left":
			case "left up":
			case "left down":
				if ((worldX != gp.player.worldX) && !collisionLeft) {
					worldX -= getSpeed();
				}
				if (worldY < gp.player.worldY - (int) (gp.player.solidArea.height / 2) - playerDetectionOffset
						&& !collisionDown) {
					worldY += getSpeed();
				} else if (!collisionUp) {
					worldY -= getSpeed();
				}
				break;
			case "right":
			case "right up":
			case "right down":
				if ((worldX != gp.player.worldX - gp.tileSize) && !collisionRight)
					worldX += getSpeed();
				if (worldY <= gp.player.worldY - (int) (gp.player.solidArea.height / 2) - playerDetectionOffset
						&& !collisionDown) {
					worldY += getSpeed();
				} else if (!collisionUp) {
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

		if (Math.abs(worldX - gp.player.worldX) < 500) {
			if ((gp.stopwatch - shoot_timer) >= 1000000000 / getShootSpeed()) {
				Bullet newBullet;

				if (gp.screenY + this.worldY <= gp.player.screenY + 50
						&& gp.screenY + this.worldY >= gp.player.screenY - 50) {

					if (gp.screenX + this.worldX <= gp.player.screenX) {
						newBullet = new Bullet(gp, this, "right", bulletDimension, bulletDimension,
								worldX + solidArea.x + solidArea.width,(int) (worldY + solidArea.y + solidArea.height/2), getBulletDamage());
					} else {
						newBullet = new Bullet(gp, this, "left", bulletDimension, bulletDimension, worldX,
								(int) (worldY + solidArea.y + solidArea.height/2), getBulletDamage());
					}
					gp.playSE(Sound.IceBullet);
					monBullets.add(newBullet);
				}
				shoot_timer = gp.stopwatch;
			}
		}

	}

	public void draw(Graphics2D g2) {
		Image image = null;

		switch (direction) {
		case "left":
		case "left up":
		case "left down":
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
		case "right up":
		case "right down":
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

//		g2.setColor(Color.white);
//		g2.drawRect(this.gp.screenX + worldX + solidArea.x, this.gp.screenY + worldY + solidArea.y,
//				solidArea.width, solidArea.height);
	}

	public void drawBullets(Graphics2D g2) {
		for (Bullet bullet : monBullets) {
			switch (bullet.getDirection()) {
			case "right":
				g2.drawImage(bullet_left, gp.screenX + bullet.worldX,
						gp.screenY + bullet.worldY, bullet.solidArea.width,
						bullet.solidArea.height, null);
				break;
			case "left":
				g2.drawImage(bullet_left, gp.screenX + bullet.worldX,
						gp.screenY + bullet.worldY, bullet.solidArea.width,
						bullet.solidArea.height, null);
				break;
			}

//			g2.setColor(Color.white);
//			g2.drawRect(gp.screenX + bullet.worldX + bullet.solidArea.x,
//					gp.screenY + bullet.worldY + bullet.solidArea.y, bullet.solidArea.width, bullet.solidArea.height);
		}
	}

}
