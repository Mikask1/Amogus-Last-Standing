package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	public static Font arial_40, arial_80B;
	public static Font OEM8514, maruMonica;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int commandNum = 0;
	public int mapNum = 1;
	public int powNum = 1;
	public int titleSubState = 0; // 0 : main menu, 1 : map menu, 2 : game over
	public int pauseSubState = 0; // 0 : main menu, 1 : map menu, 2 : game over
	public int highScore = 0;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/OEM8514.ttf");
			OEM8514 = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.BLACK);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			if(titleSubState == 2) {
				drawGameOver();
			}
			drawMainMenu();
		}
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {

		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		// GAME OVER STATE
		if(gp.player.getHealth() <= 0) {
			titleSubState = 2;
			gp.gameState = gp.titleState;
		}
		
	}
	
	public void drawMainMenu() {
		
		// Main Menu
		if(titleSubState == 0) {
			g2.setColor(new Color (54, 31, 2));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			// Title Name
			g2.setFont(maruMonica.deriveFont(Font.BOLD, 96F));
			String text = "Amogus : Last Standing";
			int x = getXforCenteredText(text);
			int y = 200;
			
//			g2.setColor(Color.white);
//			g2.drawString(text, x, y);
			
			// Title Logo
			x = gp.screenWidth / 2 - (gp.tileSize * 6) / 2 + 5;
			y -= gp.tileSize * 3;
			try {
				BufferedImage logo = ImageIO.read(getClass().getResourceAsStream("/player/Alsus.png"));
				g2.drawImage(logo, x, y, gp.tileSize * 6, gp.tileSize * 6, null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Menu Option
			g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));

			text = "START GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize * 8;
			int boxX = x - 30;
			int boxY = y - 40;
			g2.setColor(Color.BLACK);
			g2.fillRoundRect(boxX-3, boxY-3, 257, 76, 25, 25);
			g2.setColor(new Color(123, 75, 6));
			g2.fillRoundRect(boxX, boxY, 251, 70, 20, 20);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.setColor(new Color(166, 101, 6));
				g2.fillRoundRect(boxX, boxY, 251, 70, 20, 20);
				g2.setColor(Color.white);
				g2.drawString(text, x, y);
			}
			
			text = "SETTINGS";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			boxY = y - 40;
			g2.setColor(Color.BLACK);
			g2.fillRoundRect(boxX-3, boxY-3, 257, 76, 25, 25);
			g2.setColor(new Color(123, 75, 6));
			g2.fillRoundRect(boxX, boxY, 251, 70, 20, 20);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.setColor(new Color(166, 101, 6));
				g2.fillRoundRect(boxX, boxY, 251, 70, 20, 20);
				g2.setColor(Color.white);
				g2.drawString(text, x, y);
			}
			
			text = "EXIT";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			boxY = y - 40;
			g2.setColor(Color.BLACK);
			g2.fillRoundRect(boxX-3, boxY-3, 257, 76, 25, 25);
			g2.setColor(new Color(123, 75, 6));
			g2.fillRoundRect(boxX, boxY, 251, 70, 20, 20);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.setColor(new Color(166, 101, 6));
				g2.fillRoundRect(boxX, boxY, 251, 70, 20, 20);
				g2.setColor(Color.white);
				g2.drawString(text, x, y);
			}
			
			g2.setFont(maruMonica.deriveFont(Font.PLAIN, 30F));
			text = "HIGH SCORE: WAVE " + highScore;
			x = getXforCenteredText(text);
			y += gp.tileSize * 1.7;
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
		}
		
		// Map menu
		else if(titleSubState == 1) {
			g2.setColor(new Color (54, 31, 2));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			// Map Name
			g2.setFont(maruMonica.deriveFont(Font.BOLD, 96F));
			String text = "Choose Map Size :";
			int x = getXforCenteredText(text);
			int y = 200;
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);

			
			x = gp.screenWidth / 2 - (gp.tileSize * 5) / 2;
			y += gp.tileSize * 3;
			
			// map
			BufferedImage map;
			try {
				map = ImageIO.read(getClass().getResourceAsStream("/tiles/map.png"));
					g2.drawImage(map, x-gp.tileSize*3, y+gp.tileSize*3, gp.tileSize, gp.tileSize, null);
					g2.drawImage(map, x, y+gp.tileSize, gp.tileSize * 3, gp.tileSize * 3, null);
					g2.drawImage(map, x+gp.tileSize*5, y-gp.tileSize, gp.tileSize * 5, gp.tileSize * 5, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			g2.setFont(maruMonica.deriveFont(Font.PLAIN, 30F));
			text = "Small";
			g2.setColor(Color.white);
			g2.drawString(text, x-gp.tileSize*3-5, y + gp.tileSize * 5);
			
			g2.setFont(maruMonica.deriveFont(Font.PLAIN, 30F));
			text = "Medium";
			g2.setColor(Color.white);
			g2.drawString(text, x+gp.tileSize-15, y + gp.tileSize * 5);
			
			g2.setFont(maruMonica.deriveFont(Font.PLAIN, 30F));
			text = "Big";
			g2.setColor(Color.white);
			g2.drawString(text, x+gp.tileSize*7+10, y + gp.tileSize * 5);
			
				try {
					map = ImageIO.read(getClass().getResourceAsStream("/tiles/mapselect.png"));
					if(mapNum == 0) {
						g2.drawImage(map, x-gp.tileSize*3, y+gp.tileSize*3, gp.tileSize, gp.tileSize, null);
					}
					if(mapNum == 1) {
						g2.drawImage(map, x, y+gp.tileSize, gp.tileSize * 3, gp.tileSize * 3, null);
					}
					if(mapNum == 2) {
						g2.drawImage(map, x+gp.tileSize*5, y-gp.tileSize, gp.tileSize * 5, gp.tileSize * 5, null);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			// confirm button
			g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
			text = "CONFIRM";
			x = getXforCenteredText(text);
			y += gp.tileSize * 7;
			int boxX = x - 34;
			int boxY = y - 40;
			g2.setColor(Color.BLACK);
			g2.fillRoundRect(boxX-3, boxY-3, 207, 56, 25, 25);
			g2.setColor(new Color(123, 75, 6));
			g2.fillRoundRect(boxX, boxY, 201, 50, 20, 20);
			g2.setColor(Color.white);
			g2.drawString(text, x, y-5);
			if(commandNum == 1) {
				g2.setColor(new Color(166, 101, 6));
				g2.fillRoundRect(boxX, boxY, 201, 50, 20, 20);
				g2.setColor(Color.white);
				g2.drawString(text, x, y-5);
			}
			
		}


	}
	
	public void drawPauseScreen() {

		if(pauseSubState == 0) {
			// PAUSED
			g2.setColor(Color.BLACK);
			g2.setFont(arial_40.deriveFont(Font.ITALIC, 60F));
			
			String text = "PAUSED";
			int x = getXforCenteredText(text);
			int y = gp.screenHeight / 3;
			
			g2.drawString(text, x, y);
			
			g2.setFont(arial_40.deriveFont(Font.ITALIC, 60F));
			g2.setColor(Color.LIGHT_GRAY);
			g2.drawString(text, x-2, y-2);
			
			// RESUME
			g2.setColor(Color.BLACK);
			g2.fillRoundRect(x-gp.tileSize*2-3, y+17, 206, 56, 10, 10);
			
			g2.setColor(new Color(123, 75, 6));
			g2.fillRoundRect(x-gp.tileSize*2, y+20, 200, 50, 10, 10);
			
			g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
			text = "RESUME";
			g2.setColor(Color.WHITE);
			g2.drawString(text, x-gp.tileSize*2+40, y+gp.tileSize+5);
			if(commandNum == 0) {
				g2.setColor(new Color(166, 101, 6));
				g2.fillRoundRect(x-gp.tileSize*2, y+20, 200, 50, 10, 10);
				
				g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
				text = "RESUME";
				g2.setColor(Color.WHITE);
				g2.drawString(text, x-gp.tileSize*2+40, y+gp.tileSize+5);
			}
			
			// MAIN MENU
			g2.setColor(Color.BLACK);
			g2.fillRoundRect(x+gp.tileSize*3-3, y+17, 206, 56, 10, 10);
			
			g2.setColor(new Color(123, 75, 6));
			g2.fillRoundRect(x+gp.tileSize*3, y+20, 200, 50, 10, 10);
			
			g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
			text = "MAIN MENU";
			g2.setColor(Color.WHITE);
			g2.drawString(text, x+gp.tileSize*3+15, y+gp.tileSize+5);
			if(commandNum == 1) {
				g2.setColor(new Color(166, 101, 6));
				g2.fillRoundRect(x+gp.tileSize*3, y+20, 200, 50, 10, 10);
				
				g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
				text = "MAIN MENU";
				g2.setColor(Color.WHITE);
				g2.drawString(text, x+gp.tileSize*3+15, y+gp.tileSize+5);
			}
		}

		if(pauseSubState == 1) {
			
			g2.setColor(new Color(150, 100, 40));
			g2.fillRoundRect(gp.tileSize*3, gp.tileSize*2, gp.tileSize*20, gp.tileSize*10, 50, 50);
			
			try {
				BufferedImage health = ImageIO.read(getClass().getResourceAsStream("/objects/health.png"));
				BufferedImage attack = ImageIO.read(getClass().getResourceAsStream("/objects/attack.png"));
				BufferedImage attSpeed = ImageIO.read(getClass().getResourceAsStream("/objects/attspeed.png"));
				BufferedImage movSpeed = ImageIO.read(getClass().getResourceAsStream("/objects/speed.png"));
				g2.drawImage(health, gp.tileSize*4, gp.screenHeight/4, gp.tileSize * 3, gp.tileSize * 3, null);
				g2.drawImage(attack, gp.tileSize*9, gp.screenHeight/4, gp.tileSize * 3, gp.tileSize * 3, null);
				g2.drawImage(attSpeed, gp.tileSize*14, gp.screenHeight/4, gp.tileSize * 3, gp.tileSize * 3, null);
				g2.drawImage(movSpeed, gp.tileSize*19, gp.screenHeight/4, gp.tileSize * 3, gp.tileSize * 3, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String text = "Choose a Power-Up";
			g2.setFont(maruMonica.deriveFont(Font.BOLD, 50F));
			g2.setColor(Color.WHITE);
			g2.drawString(text, getXforCenteredText(text), gp.screenHeight/4 - 25);
			
			g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
			g2.drawString("Health", gp.tileSize*4 + 15, gp.screenHeight/2);
			g2.drawString("Damage", gp.tileSize*9 + 15, gp.screenHeight/2);
			g2.drawString("Attack", gp.tileSize*14 + 15, gp.screenHeight/2);
			g2.drawString("Speed", gp.tileSize*14 + 25, gp.screenHeight/2 + 35);
			g2.drawString("Movement", gp.tileSize*19, gp.screenHeight/2);
			g2.drawString("Speed", gp.tileSize*19 + 25, gp.screenHeight/2 + 35);
			
			g2.setFont(maruMonica.deriveFont(Font.PLAIN, 30F));
			g2.drawString("Increase player's", gp.tileSize*4 - 25, gp.screenHeight/2 + gp.tileSize);
			g2.drawString("health by 10", gp.tileSize*4, gp.screenHeight/2 + gp.tileSize+35);
			g2.drawString("Increase player's", gp.tileSize*9 - 15, gp.screenHeight/2 + gp.tileSize);
			g2.drawString("damage by 1", gp.tileSize*9 + 5, gp.screenHeight/2 + gp.tileSize+35);
			g2.drawString("Increase player's", gp.tileSize*14 - 20, gp.screenHeight/2 + gp.tileSize+35);
			g2.drawString("shooting speed", gp.tileSize*14 - 10, gp.screenHeight/2 + gp.tileSize+70);
			g2.drawString("Increase player's", gp.tileSize*19 - 15, gp.screenHeight/2 + gp.tileSize+35);
			g2.drawString("walking speed", gp.tileSize*19, gp.screenHeight/2 + gp.tileSize+70);
			
			if(powNum == 0) {
				g2.setFont(OEM8514.deriveFont(Font.BOLD, 100F));
				g2.drawString("^", gp.tileSize*5 - 15, gp.screenHeight/2 + gp.tileSize*5);
			}
			
			if(powNum == 1) {
				g2.setFont(OEM8514.deriveFont(Font.BOLD, 100F));
				g2.drawString("^", gp.tileSize*10 - 10, gp.screenHeight/2 + gp.tileSize*5);
			}
			
			if(powNum == 2) {
				g2.setFont(OEM8514.deriveFont(Font.BOLD, 100F));
				g2.drawString("^", gp.tileSize*15 - 5, gp.screenHeight/2 + gp.tileSize*5);
			}
			
			if(powNum == 3) {
				g2.setFont(OEM8514.deriveFont(Font.BOLD, 100F));
				g2.drawString("^", gp.tileSize*20 - 5, gp.screenHeight/2 + gp.tileSize*5);
			}

		}
		
	}
	
	public void drawGameOver() {
		g2.setColor(new Color (54, 31, 2));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x = gp.screenWidth / 2 - (gp.tileSize * 6) / 2;
		int y = gp.tileSize;
		try {
			BufferedImage logo = ImageIO.read(getClass().getResourceAsStream("/player/dead.png"));
			g2.drawImage(logo, x, y, gp.tileSize * 6, gp.tileSize * 6, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String text = "Game Over";
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 50F));
		g2.setColor(Color.WHITE);
		g2.drawString(text, getXforCenteredText(text), gp.screenHeight/2);
		
		g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
		text = "Back to Main Menu";
		x = getXforCenteredText(text);
		y += gp.tileSize * 9;
		int boxX = x - 30;
		int boxY = y - 40;
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(boxX-3, boxY-3, 387, 76, 25, 25);
		g2.setColor(new Color(123, 75, 6));
		g2.fillRoundRect(boxX, boxY, 381, 70, 20, 20);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.setColor(new Color(166, 101, 6));
			g2.fillRoundRect(boxX, boxY, 381, 70, 20, 20);
			
			g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
			text = "Back to Main Menu";
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);
		}
		
		text = "Exit";
		x = getXforCenteredText(text);
		y += gp.tileSize * 2;
		boxY = y - 40;
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(boxX-3, boxY-3, 387, 76, 25, 25);
		g2.setColor(new Color(123, 75, 6));
		g2.fillRoundRect(boxX, boxY, 381, 70, 20, 20);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.setColor(new Color(166, 101, 6));
			g2.fillRoundRect(boxX, boxY, 381, 70, 20, 20);
			
			g2.setFont(OEM8514.deriveFont(Font.PLAIN, 30F));
			text = "Exit";
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);
		}
		
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
		
	}
	
}
