package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Character{

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - gp.tileSize / 2;
		screenY = gp.screenHeight / 2 - gp.tileSize / 2;
		
		//collision
		solidArea = new Rectangle();
		solidArea.x = 5;
		solidArea.y = 30;
		solidArea.width = 36;
		solidArea.height = 15;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * gp.maxWorldCol/2;
		worldY = gp.tileSize * gp.maxWorldRow/2;
		speed = 4;
		direction = "down";
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
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.leftPressed == true || keyH.downPressed == true || keyH.rightPressed == true) {
			
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
			
			if(keyH.upPressed == true) {
				direction = "up";
			}
			
			if(keyH.downPressed == true) {
				direction = "down";
			}
			
			if(keyH.leftPressed == true) {
				direction = "left";
			}
			
			if(keyH.rightPressed == true) {
				direction = "right";				
			}
			
			// check collision
			collisionOn = false;
			
			gp.cChecker.checkTile(this);
			
			//if collision = false, player can move
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
					
				case "down":
					worldY += speed;
					break;
					
				case "left":
					worldX -= speed;
					break;
					
				case "right":
					worldX += speed;
					break;
				}
			}
			
			
			spriteCounter++;
			if(spriteCounter > 6) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 3;
				}
				else if(spriteNum == 3) {
					spriteNum = 4;
				}
				else if(spriteNum == 4) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
		}
		

	}
	
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);	
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up;
			}
			if(spriteNum == 2) {
				image = up1;
			}
			if(spriteNum == 3) {
				image = up;
			}
			if(spriteNum == 4) {
				image = up2;
			}
			if(keyH.upPressed == false) {
				image = up;
			}
			break;
		
		case "down":
			if(spriteNum == 1) {
				image = down;
			}
			if(spriteNum == 2) {
				image = down1;
			}
			if(spriteNum == 3) {
				image = down;
			}
			if(spriteNum == 4) {
				image = down2;
			}
			if(keyH.downPressed == false) {
				image = down;
			}
			break;
		
		case "left":
			if(spriteNum == 1) {
				image = left;
			}
			if(spriteNum == 2) {
				image = left1;
			}
			if(spriteNum == 3) {
				image = left;
			}
			if(spriteNum == 4) {
				image = left2;
			}
			if(keyH.leftPressed == false) {
				image = left;
			}
			break;
		
		case "right":
			if(spriteNum == 1) {
				image = right;
			}
			if(spriteNum == 2) {
				image = right1;
			}
			if(spriteNum == 3) {
				image = right;
			}
			if(spriteNum == 4) {
				image = right2;
			}
			if(keyH.rightPressed == false) {
				image = right;
			}
			break;
		
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); 
	}
	
}
