package monster;

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
	Player player;
	public int screenX;
	public int screenY;
	Random rand = new Random();
	String tempDir = "left";

//	int moveToX = gp.player.screenX;
//	int moveToY = gp.player.screenY;
//	int distX = moveToX - worldX;
//	int distY = moveToX - worldY;
//	float angle = (float) Math.atan2(distY, distX);

	public MonMushroom(GamePanel gp) {
		super(gp);

		speed = 1;
		direction = "left";

		worldX = 500;
		worldY = 488;

		worldX = gp.player.screenX;
		worldY = gp.player.screenY;
		
		solidArea = new Rectangle();
		solidArea.x = 25;
		solidArea.y = 20;
		solidArea.width = 45;
		solidArea.height = 70;

		footArea = new Rectangle();
		footArea.x = 17;
		footArea.y = 80;
		footArea.width = 65;
		footArea.height = 18;

//		System.out.println(moveToX);

		getImage();
		setAction();
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAction() {

		int moveToX = gp.player.screenX;
		System.out.println(moveToX);

		actionLockCounter++;

		if (actionLockCounter == 120) {

			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "up";
			}
			if (i > 50 && i <= 75) {
				direction = "down";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}

			actionLockCounter = 0;
		}

	}

	public void update() {

		collisionOn = false;
		gp.cChecker.checkTile(gp.mon);
		if (collisionOn == false) {
		switch (direction) {
		case "up":
			worldY -= speed;
//				worldX += speed * Math.cos(angle);
//				worldY += speed * Math.sin(angle);
			break;

		case "down":
			worldY += speed;
//				worldX += speed * Math.cos(angle);
//				worldY += speed * Math.sin(angle);
			break;

		case "left":
			worldX -= speed;
//				worldX += speed * Math.cos(angle);
//				worldY += speed * Math.sin(angle);
			break;

		case "right":
			worldX += speed;
//				worldX += speed * Math.cos(angle);
//				worldY += speed * Math.sin(angle);
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
		
	}
		

	public void draw(Graphics2D g2) {
		BufferedImage image = null;
//		int screenX = worldX - gp.player.worldX + gp.player.screenX;
//		int screenY = worldY - gp.player.worldY + gp.player.screenY;

//		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
//			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
//			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
//			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

		
		switch (direction) {
		case "up":
			if (tempDir == "left") {
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
			}
			if (tempDir == "right") {
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
			}

		case "down":
			if (tempDir == "left") {
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
			}
			if (tempDir == "right") {
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
			}

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

		}

		System.out.println();
		g2.drawImage(image, this.gp.screenX + worldX, this.gp.screenY + worldY, 100, 100, null);
//		}
	}

}
