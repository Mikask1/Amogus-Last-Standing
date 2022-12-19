package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import bullet.Bullet;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Character {

	GamePanel gp;
	KeyHandler keyH;

	public int screenX;
	public int screenY;

	int playerBulletDimension1 = 4;
	int playerBulletDimension2 = 13;
	
	public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
	public BufferedImage up_shoot, up1_shoot, up2_shoot, down_shoot, down1_shoot, down2_shoot, left_shoot, left1_shoot,
			left2_shoot, right_shoot, right1_shoot, right2_shoot;
	
	int diagonalSpeed;
	private int bulletDamage;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;
		
		size = 48;
		screenX = gp.worldWidth / 2 - gp.tileSize / 2;
		screenY = gp.worldHeight / 2 - gp.tileSize / 2;

		// collision
		footArea = new Rectangle();
		footArea.x = 13;
		footArea.y = 35;
		footArea.width = 20;
		footArea.height = 10;

		solidArea = new Rectangle();
		solidArea.x = 10;
		solidArea.y = 10;
		solidArea.width = 28;
		solidArea.height = 30;

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		setShootSpeed(10);
		setSpeed(5);
		setBulletDamage(1000);
		direction = "down";
		setHealth(1000);
		fireTimer = gp.stopwatch;
		freezeTimer = gp.stopwatch;
	}
	
	public void getPlayerImage() {
		try {
			up = ImageIO.read(getClass().getResourceAsStream("/player/amog-back.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/amog-back1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/amog-back2.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/player/amog-down.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/amog-down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/amog-down2.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/player/amog-left.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/amog-left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/amog-left2.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/player/amog-right.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/amog-right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/amog-right2.png"));

			up_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-back-shoot.png"));
			up1_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-back1-shoot.png"));
			up2_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-back2-shoot.png"));
			down_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-down-shoot.png"));
			down1_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-down1-shoot.png"));
			down2_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-down2-shoot.png"));
			left_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-left-shoot.png"));
			left1_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-left1-shoot.png"));
			left2_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-left2-shoot.png"));
			right_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-right-shoot.png"));
			right1_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-right1-shoot.png"));
			right2_shoot = ImageIO.read(getClass().getResourceAsStream("/player/amog-right2-shoot.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (keyH.upPressed == true || keyH.leftPressed == true || keyH.downPressed == true
				|| keyH.rightPressed == true) {
			
			// CAN'T MOVE DIAGONALLY

			if (keyH.upPressed == true) {
				direction = "up";
			}

			if (keyH.downPressed == true) {
				direction = "down";
			}

			if (keyH.leftPressed == true) {
				direction = "left";
			}

			if (keyH.rightPressed == true) {
				direction = "right";
			}

			// Collision Check
			collisionOn = false;
			gp.cChecker.insideMap(this);
			
			if(keyH.upPressed == true) {
				direction = "up";
				if(collisionOn == false) {		
					if (keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
						worldY -= getDiagonalSpeed();
					}
					else {
						worldY -= getSpeed();
					}
				}
			}
			
			if(keyH.downPressed == true) {
				direction = "down";
				if(collisionOn == false) {
					if (keyH.upPressed || keyH.leftPressed || keyH.rightPressed) {
						worldY += getDiagonalSpeed();
					}
					else {
						worldY += getSpeed();
					}				
				}
			}
			
			if(keyH.leftPressed == true) {
				direction = "left";
				if(collisionOn == false) {
					if (keyH.upPressed || keyH.rightPressed || keyH.downPressed) {
						worldX -= getDiagonalSpeed();
					}
					else {
						worldX -= getSpeed();
					}					
				}	
			}
			
			if(keyH.rightPressed == true) {
				direction = "right";	
				if(collisionOn == false) {
					if (keyH.upPressed || keyH.leftPressed || keyH.downPressed) {
						worldX += getDiagonalSpeed();
					}
					else {
						worldX += getSpeed();
					}		
				}					
			}


			spriteCounter++;
			if (spriteCounter > 6) {
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

		}

		if (keyH.shoot) {
			if ((gp.stopwatch - shoot_timer) >= 1000000000 / getShootSpeed()) {
				gp.playSE(1);
				switch (direction) {
				case "up":
					Bullet newBullet = new Bullet(gp, this, direction, playerBulletDimension1, playerBulletDimension2, worldX + gp.tileSize/2 - 3, worldY, bulletDamage);
					bullets.add(newBullet);
					break;

				case "down":
					Bullet newBullet1 = new Bullet(gp, this, direction, playerBulletDimension1, playerBulletDimension2, worldX + gp.tileSize/2 - 3, worldY + gp.tileSize/2 + 4, bulletDamage);
					bullets.add(newBullet1);
					break;

				case "left":
					Bullet newBullet2 = new Bullet(gp, this, direction, playerBulletDimension2, playerBulletDimension1, worldX, worldY + gp.tileSize/2 - 4, bulletDamage);
					bullets.add(newBullet2);
					break;

				case "right":
					Bullet newBullet3 = new Bullet(gp, this, direction, playerBulletDimension2, playerBulletDimension1, worldX + gp.tileSize/2 + 4, worldY + gp.tileSize/2 - 4, bulletDamage);
					bullets.add(newBullet3);
					break;
				}
				shoot_timer = gp.stopwatch;
			}
		}
		
		if (onFire && (gp.stopwatch - fireTimer >= fireInterval * gp.NANO_TO_MILI)) {
			hurt = true;
			damageHealth(1);
			fireTimer = gp.stopwatch;
		}

		if (onFire && fireCounter >= onFireDuration / fireInterval) {
			onFire = false;
			onFireDuration = 0;
			fireCounter = 0;
			hurtCounter = 0;
			fireTimer = gp.stopwatch;
		}
		
		if (freeze && (gp.stopwatch - freezeTimer >= freezeInterval * gp.NANO_TO_MILI)) {
			setSpeed(2);
			freezeTimer = gp.stopwatch;
		}
		
		if (freeze && freezeCounter >= freezeDuration) {
			freeze = false;
			setSpeed(5);
			freezeDuration = 0;
			freezeCounter = 0;
			freezeTimer = gp.stopwatch;
		}
		
		if (hurt == true) {
			hurtCounter++;
			if (hurtCounter > 30) {
				if (onFire) {
					fireCounter += 1;
					gp.playSE(3);
				}
				hurt = false;
				hurtCounter = 0;
			}
		}
		
		if (freeze) {
			freezeCounter += 1;
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up;
				if (keyH.shoot) {
					image = up_shoot;
				}
			}
			if (spriteNum == 2) {
				image = up1;
				if (keyH.shoot) {
					image = up1_shoot;
				}
			}
			if (spriteNum == 3) {
				image = up;
				if (keyH.shoot) {
					image = up_shoot;
				}
			}
			if (spriteNum == 4) {
				image = up2;
				if (keyH.shoot) {
					image = up2_shoot;
				}
			}
			if (keyH.upPressed == false) {
				image = up;
				if (keyH.shoot) {
					image = up_shoot;
				}
			}
			break;

		case "down":
			if (spriteNum == 1) {
				image = down;
				if (keyH.shoot) {
					image = down_shoot;
				}
			}
			if (spriteNum == 2) {
				image = down1;
				if (keyH.shoot) {
					image = down1_shoot;
				}
			}
			if (spriteNum == 3) {
				image = down;
				if (keyH.shoot) {
					image = down_shoot;
				}
			}
			if (spriteNum == 4) {
				image = down2;
				if (keyH.shoot) {
					image = down2_shoot;
				}
			}
			if (keyH.downPressed == false) {
				image = down;
				if (keyH.shoot) {
					image = down_shoot;
				}
			}
			break;

		case "left":
			if (spriteNum == 1) {
				image = left;
				if (keyH.shoot) {
					image = left_shoot;
				}
			}
			if (spriteNum == 2) {
				image = left1;
				if (keyH.shoot) {
					image = left1_shoot;
				}
			}
			if (spriteNum == 3) {
				image = left;
				if (keyH.shoot) {
					image = left_shoot;
				}
			}
			if (spriteNum == 4) {
				image = left2;
				if (keyH.shoot) {
					image = left2_shoot;
				}
			}
			if (keyH.leftPressed == false) {
				image = left;
				if (keyH.shoot) {
					image = left_shoot;
				}
			}
			break;

		case "right":
			if (spriteNum == 1) {
				image = right;
				if (keyH.shoot) {
					image = right_shoot;
				}
			}
			if (spriteNum == 2) {
				image = right1;
				if (keyH.shoot) {
					image = right1_shoot;
				}
			}
			if (spriteNum == 3) {
				image = right;
				if (keyH.shoot) {
					image = right_shoot;
				}
			}
			if (spriteNum == 4) {
				image = right2;
				if (keyH.shoot) {
					image = right2_shoot;
				}
			}
			if (keyH.rightPressed == false) {
				image = right;
				if (keyH.shoot) {
					image = right_shoot;
				}
			}
			break;

		}

		if (hurt) {
			g2.drawImage(tint(image, 1, 0.3, 0.3, 1), screenX, screenY, size, size, null);		
		}
		else if (freeze) {
			g2.drawImage(tint(image, 0.2, 1, 1.9, 1), screenX, screenY, size, size, null);
		}
		else {
			g2.drawImage(image, screenX, screenY, size, size, null);		
		}
	}

	public void drawBullets(Graphics2D g2) {
		for (Bullet bullet : bullets) {
			switch (bullet.getDirection()) {
			case "up":
				g2.drawImage(bullet_up, gp.screenX + bullet.worldX, gp.screenY + bullet.worldY, playerBulletDimension1,
						playerBulletDimension2, null);
				break;

			case "down":
				g2.drawImage(bullet_down, gp.screenX + bullet.worldX, gp.screenY + bullet.worldY,
						playerBulletDimension1, playerBulletDimension2, null);
				break;

			case "left":
				g2.drawImage(bullet_left, gp.screenX + bullet.worldX, gp.screenY + bullet.worldY,
						playerBulletDimension2, playerBulletDimension1, null);
				break;

			case "right":
				g2.drawImage(bullet_right, gp.screenX + bullet.worldX, gp.screenY + bullet.worldY,
						playerBulletDimension2, playerBulletDimension1, null);
				break;
			}

			g2.setColor(Color.white);
		}
	}
	
	@Override
	public void setSpeed(int speed) {
		super.setSpeed(speed);
		this.setDiagonalSpeed(speed);
	}
	
	void setDiagonalSpeed(int speed) {
		this.diagonalSpeed = (int) Math.round(getSpeed() / Math.sqrt(2));
		System.out.println(getSpeed());
		System.out.println(diagonalSpeed);
	}
	
	int getDiagonalSpeed() {
		return this.diagonalSpeed;
	}
	
	protected BufferedImage tint(BufferedImage sprite, double d, double e, double f, double g) {
		BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(),
				BufferedImage.TRANSLUCENT);
		Graphics2D graphics = tintedSprite.createGraphics();
		graphics.drawImage(sprite, 0, 0, null);
		graphics.dispose();

		for (int i = 0; i < tintedSprite.getWidth(); i++) {
			for (int j = 0; j < tintedSprite.getHeight(); j++) {
				int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
				int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
				int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
				int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
				rx *= d;
				gx *= e;
				bx *= f;
				ax *= g;
				tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
			}
		}
		return tintedSprite;
	}

	public int getBulletDamage() {
		return bulletDamage;
	}

	public void setBulletDamage(int bulletDamage) {
		this.bulletDamage = bulletDamage;
	}
}
