package character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Character{
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		x = 100;
		y = 100;
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
			
			if(keyH.upPressed == true) {
				direction = "up";
				y -= speed;
			}
			
			if(keyH.leftPressed == true) {
				direction = "left";
				x -= speed;
			}
			
			if(keyH.downPressed == true) {
				direction = "down";
				y += speed;
			}
			
			if(keyH.rightPressed == true) {
				direction = "right";
				x += speed;
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
		
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null); 
		
	}

	@Override
	public void shoot() {
	}
	
}
