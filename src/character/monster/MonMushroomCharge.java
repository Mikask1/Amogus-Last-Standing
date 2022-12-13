package character.monster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class MonMushroomCharge extends Monster {
	final int NANO_TO_MILI = 1000000;

	Random rand = new Random();
	String tempDir = "left";

	private int normalSize;

	// Miliseconds
	private long chargeCooldown = 5000;
	private long chargePowerup = 2000;
	private long chargeDuration = 500;
	private int chargeSpeed = 4;
	private boolean charge = false;
	private boolean release = false;

	private long chargeTimer;

	public MonMushroomCharge(GamePanel gp) {
		super(gp);
		setDefaultValues();
		getImage();
		setAction();
	}

	private void setDefaultValues() {
		setSpeed(2);
		setHealth(30);
		setBodyDamage(5);
		size = 96;
		normalSize = size;
		chargeTimer = gp.stopwatch;

		direction = "left";

		worldX = rand.nextInt(-200, 200) + gp.player.screenX;
		worldY = rand.nextInt(-200, 200) + gp.player.screenY;

		solidArea = new Rectangle();
		solidArea.x = 15;
		solidArea.y = 10;
		solidArea.width = 60;
		solidArea.height = 86;

		footArea = new Rectangle();
		footArea.x = 20;
		footArea.y = 80;
		footArea.width = 52;
		footArea.height = 15;
	}

	public void getImage() {
		try {
			monLeft = ImageIO.read(getClass().getResourceAsStream("/monster/cleft.png"));
			monLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/cleft1.png"));
			monLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/cleft2.png"));
			monLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/cleft3.png"));
			monLeft4 = ImageIO.read(getClass().getResourceAsStream("/monster/cleft4.png"));
			monRight = ImageIO.read(getClass().getResourceAsStream("/monster/cright.png"));
			monRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/cright1.png"));
			monRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/cright2.png"));
			monRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/cright3.png"));
			monRight4 = ImageIO.read(getClass().getResourceAsStream("/monster/cright4.png"));
			hurtLeft = ImageIO.read(getClass().getResourceAsStream("/monster/hcleft.png"));
			hurtLeft1 = ImageIO.read(getClass().getResourceAsStream("/monster/hcleft1.png"));
			hurtLeft2 = ImageIO.read(getClass().getResourceAsStream("/monster/hcleft2.png"));
			hurtLeft3 = ImageIO.read(getClass().getResourceAsStream("/monster/hcleft3.png"));
			hurtLeft4 = ImageIO.read(getClass().getResourceAsStream("/monster/hcleft4.png"));
			hurtRight = ImageIO.read(getClass().getResourceAsStream("/monster/hcright.png"));
			hurtRight1 = ImageIO.read(getClass().getResourceAsStream("/monster/hcright1.png"));
			hurtRight2 = ImageIO.read(getClass().getResourceAsStream("/monster/hcright2.png"));
			hurtRight3 = ImageIO.read(getClass().getResourceAsStream("/monster/hcright3.png"));
			hurtRight4 = ImageIO.read(getClass().getResourceAsStream("/monster/hcright4.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAction() {
		if (worldX < gp.player.worldX - gp.tileSize) {
			direction = "right";
			if (worldY < gp.player.worldY - gp.tileSize) {
				direction = "right down";
			} else{
				direction = "right up";
			}
		}
		if (worldX > gp.player.worldX) {
			direction = "left";
			if (worldY < gp.player.worldY - gp.tileSize) {
				direction = "left down";
			} else{
				direction = "left up";
			}
		}
	}

	public void update() {
		collisionOn = false;
		gp.cChecker.insideMap(this);
		int speed = getSpeed();

		if (gp.stopwatch - chargeTimer >= chargeCooldown * NANO_TO_MILI) {
			charge = true;
			chargeTimer = gp.stopwatch;
		}

		if (charge) {
			if (gp.stopwatch - chargeTimer <= chargePowerup * NANO_TO_MILI) {
				speed = 0;
			} else {
				chargeTimer = gp.stopwatch;
				release = true;
			}
		}

		if (charge && release) {
			if (gp.stopwatch - chargeTimer <= chargeDuration * NANO_TO_MILI) {
				speed += chargeSpeed;
			} else {
				charge = false;
				release = false;
				size = normalSize;
				chargeTimer = gp.stopwatch;
			}
		}

		if (collisionOn == false) {
			switch (direction) {
			case "left":
			case "left up":
			case "left down":
				if ((worldX != gp.player.worldX) && !collisionLeft) {
					worldX -= speed;
				}
				if (worldY < gp.player.worldY - gp.tileSize && !collisionDown) {
					worldY += speed;
				} else if (!collisionUp){
					worldY -= speed;
				}
				break;
			case "right":
			case "right up":
			case "right down":
				if ((worldX != gp.player.worldX - gp.tileSize) && !collisionRight)
					worldX += speed;
				if (worldY <= gp.player.worldY - gp.tileSize && !collisionDown) {
					worldY += speed;
				} else if (!collisionUp){
					worldY -= speed;
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
		case "left up":
		case "left down":
			tempDir = "left";
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
	
	public static void tint(BufferedImage image, Color color) {
	    for (int x = 0; x < image.getWidth(); x++) {
	        for (int y = 0; y < image.getHeight(); y++) {
	            Color pixelColor = new Color(image.getRGB(x, y), true);
	            int r = (pixelColor.getRed() + color.getRed()) / 2;
	            int g = (pixelColor.getGreen() + color.getGreen()) / 2;
	            int b = (pixelColor.getBlue() + color.getBlue()) / 2;
	            int a = pixelColor.getAlpha();
	            int rgba = (a << 24) | (r << 16) | (g << 8) | b;
	            image.setRGB(x, y, rgba);
	        }
	    }
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
