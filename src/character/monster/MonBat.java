package character.monster;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;

public class MonBat extends Monster {
	Random rand = new Random();
	String tempDir = "left";

	public MonBat(GamePanel gp) {
		super(gp);
		size = 64;
		setDefaultValues();
		getImage();
		setAction();
	}

	private void setDefaultValues() {
		setSpeed(1);
		setHealth(30);
		setBodyDamage(4);
		direction = "left";

		worldX = rand.nextInt(-200, 200) + gp.player.screenX;
		worldY = rand.nextInt(-200, 200) + gp.player.screenY;

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
	}
  
	public void getImage() {

		try {
			monLeft = ImageIO.read(getClass().getResourceAsStream("/monster/batleft.png"));
			monLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/batleft1.png")); 
			monLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/batleft2.png"));
			monLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/batleft3.png"));
			monRight = ImageIO.read(getClass().getResourceAsStream("/monster/batright.png"));
			monRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/batright1.png"));
			monRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/batright2.png"));
			monRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/batright3.png"));
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
		actionLockCounter++;

		if (actionLockCounter == 200) {

			int i = rand.nextInt(100) + 1;

			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}

			actionLockCounter = 0;
		}
	}

	public void update() {
		collisionOn = false;
		gp.cChecker.insideMap(this);

		if (collisionOn == false) {
			switch (direction) {

			case "up":
				if (!collisionUp) {
					worldY -= getSpeed();
				}
				break;
			case "down":
				if (!collisionDown) {
					worldY += getSpeed();
				}
				break;

			case "left":
				if (!collisionLeft) {
					worldX -= getSpeed();
				}
				break;

			case "right":
				if (!collisionRight) {
					worldX += getSpeed();
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
	}

	public void draw(Graphics2D g2) {

		Image image = null;
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
			}
			if (tempDir == "right") {
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
			}
			break;
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
			}
			if (tempDir == "right") {
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
			}
			break;
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
		g2.drawImage(image, this.gp.screenX + worldX, this.gp.screenY + worldY, size, size, null);
	}
}
