package character.monster;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import character.Character;
import character.Player;
import main.GamePanel;

public class MonMushroom extends Character {

	Random rand = new Random();
	String tempDir = "left";

//	int moveToX = gp.player.screenX;
//	int moveToY = gp.player.screenY;
//	int distX = moveToX - worldX;
//	int distY = moveToX - worldY;
//	float angle = (float) Math.atan2(distY, distX);

	public MonMushroom(GamePanel gp) {
		super(gp);

		setDefaultValues();
		getImage();
		setAction();
	}

	private void setDefaultValues() {
		setSpeed(1);
		setHealth(50);
		direction = "left";

		worldX = rand.nextInt(-200, 200) + gp.player.screenX;
		worldY = rand.nextInt(-200, 200) + gp.player.screenY;

		solidArea = new Rectangle();
		solidArea.x = 25;
		solidArea.y = 20;
		solidArea.width = 45;
		solidArea.height = 70;

		footArea = new Rectangle();
		footArea.x = 30;
		footArea.y = 80;
		footArea.width = 40;
		footArea.height = 13;
	}

	public void getImage() {

		try {
			monLeft = ImageIO.read(getClass().getResourceAsStream("/monster/left.png"));
			monLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/left1.png"));
			monLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/left2.png"));
			monLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/left3.png"));
			monLeft4 = ImageIO.read(getClass().getResourceAsStream("/monster/left4.png"));
			monRight = ImageIO.read(getClass().getResourceAsStream("/monster/right.png"));
			monRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/right1.png"));
			monRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/right2.png"));
			monRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/right3.png"));
			monRight4 = ImageIO.read(getClass().getResourceAsStream("/monster/right4.png"));
			hurtLeft = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-left.png"));
			hurtLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-left1.png"));
			hurtLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-left2.png"));
			hurtLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-left3.png"));
			hurtLeft4 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-left4.png"));
			hurtRight = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-right.png"));
			hurtRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-right1.png"));
			hurtRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-right2.png"));
			hurtRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-right3.png"));
			hurtRight4 = ImageIO.read(getClass().getResourceAsStream("/monster/hurt-right4.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAction() {
//		actionLockCounter++;
//
//		if (actionLockCounter == 120) {
//
//			Random random = new Random();
//			int i = random.nextInt(100) + 1;
//
//			if (i <= 25) {
//				direction = "up";
//			}
//			if (i > 25 && i <= 50) {
//				direction = "up";
//			}
//			if (i > 50 && i <= 75) {
//				direction = "down";
//			}
//			if (i > 75 && i <= 100) {
//				direction = "right";
//			}
//
//			actionLockCounter = 0;
//		}

		if (worldX < gp.player.worldX - gp.tileSize)
			direction = "right";
		if (worldX > gp.player.worldX)
			direction = "left";

	}

	public void update() {
		collisionOn = false;
		gp.cChecker.insideMap(this);
		gp.cChecker.checkBulletHitsEnemy(this);

		if (collisionOn == false) {
			switch (direction) {

			case "left":
				if (worldX != gp.player.worldX) {
					worldX -= getSpeed();
				}
				if (worldY < gp.player.worldY - gp.tileSize) {
					worldY += getSpeed();
				} else {
					worldY -= getSpeed();
				}
				break;

			case "right":
				if (worldX != gp.player.worldX - gp.tileSize)
					worldX += getSpeed();
				if (worldY <= gp.player.worldY - gp.tileSize) {
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
				spriteNum = 5;
			} else if (spriteNum == 5) {
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
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

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
				if (spriteNum == 5) {
					image = hurtLeft4;
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
				if (spriteNum == 5) {
					image = hurtRight4;
				}
				direction = "right";
			}
			break;

		}

		g2.drawImage(image, this.gp.screenX + worldX, this.gp.screenY + worldY, 100, 100, null);
	}

}
