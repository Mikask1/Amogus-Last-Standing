package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;

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
	
	public Image up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
	public Image up_shoot, up1_shoot, up2_shoot, down_shoot, down1_shoot, down2_shoot, left_shoot, left1_shoot,
			left2_shoot, right_shoot, right1_shoot, right2_shoot;
	
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
		setShootSpeed(4);
		setSpeed(4);
		direction = "down";
		setHealth(10);
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

			bullet_up = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_up.png")).getScaledInstance(4, 13,
					Image.SCALE_DEFAULT);
			bullet_down = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_down.png")).getScaledInstance(4,
					13, Image.SCALE_DEFAULT);
			bullet_left = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_left.png")).getScaledInstance(13,
					4, Image.SCALE_DEFAULT);
			bullet_right = ImageIO.read(getClass().getResourceAsStream("/bullets/bullet_right.png"))
					.getScaledInstance(13, 4, Image.SCALE_DEFAULT);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (keyH.upPressed == true || keyH.leftPressed == true || keyH.downPressed == true
				|| keyH.rightPressed == true) {

//			//CAN MOVE DIAGONALLY
//			
//			collisionOn = false;
//			gp.cChecker.checkTile(this);
//			
//			if(keyH.upPressed == true) {
//				direction = "up";
//				if(collisionOn == false)
//				worldY -= speed;
//			}
//			
//			if(keyH.downPressed == true) {
//				direction = "down";
//				if(collisionOn == false)
//				worldY += speed;
//			}
//			
//			if(keyH.leftPressed == true) {
//				direction = "left";
//				if(collisionOn == false)
//				worldX -= speed;
//				
//			}
//			
//			if(keyH.rightPressed == true) {
//				direction = "right";	
//				if(collisionOn == false)
//				worldX += speed;
//							
//			}

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

			// if collision = false, player can move
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= getSpeed();
					break;

				case "down":
					worldY += getSpeed();
					break;

				case "left":
					worldX -= getSpeed();
					break;

				case "right":
					worldX += getSpeed();
					break;
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
				switch (direction) {
				case "up":
					Bullet newBullet = new Bullet(gp, this, direction, playerBulletDimension1, playerBulletDimension2, worldX + gp.tileSize/2 - 3, worldY);
					bullets.add(newBullet);
					break;

				case "down":
					Bullet newBullet1 = new Bullet(gp, this, direction, playerBulletDimension1, playerBulletDimension2, worldX + gp.tileSize/2 - 3, worldY + gp.tileSize/2 + 4);
					bullets.add(newBullet1);
					break;

				case "left":
					Bullet newBullet2 = new Bullet(gp, this, direction, playerBulletDimension2, playerBulletDimension1, worldX, worldY + gp.tileSize/2 - 4);
					bullets.add(newBullet2);
					break;

				case "right":
					Bullet newBullet3 = new Bullet(gp, this, direction, playerBulletDimension2, playerBulletDimension1, worldX + gp.tileSize/2 + 4, worldY + gp.tileSize/2 - 4);
					bullets.add(newBullet3);
					break;
				}
				shoot_timer = gp.stopwatch;
			}
		}
	}

	public void draw(Graphics2D g2) {

		Image image = null;

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

		g2.drawImage(image, screenX, screenY, size, size, null);
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
			g2.drawRect(gp.screenX + bullet.worldX + bullet.solidArea.x, gp.screenY + bullet.worldY + bullet.solidArea.y,
					bullet.solidArea.width, bullet.solidArea.height);
		}
	}

}
