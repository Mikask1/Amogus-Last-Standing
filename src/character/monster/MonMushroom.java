package character.monster;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import main.GamePanel;

public class MonMushroom extends Monster {
	Random rand = new Random();
	String tempDir = "left";
	
	private final int playerDetectionOffset = 30;
	
	public MonMushroom(GamePanel gp) {
		super(gp);
		size = 112;
		setDefaultValues();
		getImage();
		setAction();
	}

	private void setDefaultValues() {
		setSpeed(1);
		setHealth(50);
		setBodyDamage(3);
		direction = "left";

		worldX = rand.nextInt(-200, 200) + gp.player.screenX;
		worldY = rand.nextInt(-200, 200) + gp.player.screenY;

		solidArea = new Rectangle();
		solidArea.x = 25;
		solidArea.y = 20;
		solidArea.width = 61;
		solidArea.height = 92;

		footArea = new Rectangle();
		footArea.x = 30;
		footArea.y = 96;
		footArea.width = 52;
		footArea.height = 15;
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
			e.printStackTrace();
		}
	}

	public void setAction() {
		if (worldX < gp.player.worldX - gp.tileSize) {
			direction = "right";
			if (worldY < gp.player.worldY - (int) (gp.player.solidArea.height/2) - playerDetectionOffset) {
				direction = "right down";
			} else{
				direction = "right up";
			}
		}
		if (worldX > gp.player.worldX) {
			direction = "left";
			if (worldY < gp.player.worldY - (int) (gp.player.solidArea.height/2) - playerDetectionOffset) {
				direction = "left down";
			} else{
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
				if (worldY < gp.player.worldY - (int) (gp.player.solidArea.height/2) - playerDetectionOffset && !collisionDown) {
					worldY += getSpeed();
				} else if (!collisionUp){
					worldY -= getSpeed();
				}
				break;
			case "right":
			case "right up":
			case "right down":
				if ((worldX != gp.player.worldX - gp.tileSize) && !collisionRight)
					worldX += getSpeed();
				if (worldY <= gp.player.worldY - (int) (gp.player.solidArea.height/2) - playerDetectionOffset && !collisionDown) {
					worldY += getSpeed();
				} else if (!collisionUp){
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
			switch (tempDir) {
			case "left":
			case "left up":
			case "left down":
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
				break;
			case "right":
			case "right up":
			case "right down":
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
				break;
			}
			break;
		}
		g2.drawImage(image, this.gp.screenX + worldX, this.gp.screenY + worldY, size, size, null);
	}
}
