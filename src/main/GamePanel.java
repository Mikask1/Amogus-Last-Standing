package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import MapGenerator.Voronoi;
import bullet.Bullet;
import character.Player;
import monster.MonMushroom;;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16 x 16 tile
	final int scale = 3;

	public int tileSize = originalTileSize * scale; // 48 x 48 tile
	public int maxScreenCol = 26;
	public int maxScreenRow = 15;
	public int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public int screenHeight = tileSize * maxScreenRow; // 576 pixels

	// WORLD SETTINGS
	public int sizeMultiplier = 1;
	public int maxWorldCol = sizeMultiplier * maxScreenCol;
	public int maxWorldRow = sizeMultiplier * maxScreenRow;
	public int worldWidth = tileSize * maxWorldCol;
	public int worldHeight = tileSize * maxWorldRow;

	public int screenX = 0;
	public int screenY = 0;
	
	Image backgroundImage;

	// FPS
	int FPS = 120;
	public long stopwatch = 0;

	// SYSTEM
	public CollisionChecker cChecker = new CollisionChecker(this);
	KeyHandler keyH = new KeyHandler(this);	
	public UI ui = new UI(this);
	Thread gameThread;

	// ENTITY & OBJECT
	public Player player = new Player(this, keyH);
	public MonMushroom mon = new MonMushroom(this);
	
	public Voronoi map = new Voronoi(worldWidth, worldHeight, this);

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	
	public GamePanel() {
		try {
			backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tiles/bigLava.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keyH);
	}
	
	public void setupGame() {
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.0166 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			stopwatch = currentTime;
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}

	}

	public void update() {
		if(gameState == playState) {
			screenX = player.screenX - player.worldX;
			screenY = player.screenY - player.worldY;
			player.update();
			mon.setAction();
			mon.update();
			for (Bullet bt : player.bullets) {
				bt.update();
			}
		}
		
		if(gameState == pauseState) {
			
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		// Title Screen
		if(gameState == titleState) {
			ui.draw(g2);
		}
				
		else {
			g2.drawImage(backgroundImage, screenX/4 - screenWidth/2, screenY/4 - 100 - screenHeight/2, map.mapWidth, map.mapHeight, null);

			// Map
			map.drawCellColors(g2);
				
			// Player
			player.draw(g2);
			// Monster
			mon.draw(g2);
			// Bounding Box
			g2.setColor(Color.blue);
			g2.drawRect(player.screenX + player.solidArea.x, player.screenY + player.solidArea.y, player.solidArea.width, player.solidArea.height);					
			player.drawBullets(g2);
			g2.setColor(Color.BLUE);
			g2.drawRect(screenX + mon.worldX + mon.footArea.x, screenY + mon.worldY + mon.footArea.y, mon.footArea.width, mon.footArea.height);
			g2.drawRect(screenX + mon.worldX + mon.solidArea.x, screenY + mon.worldY + mon.solidArea.y, mon.solidArea.width, mon.solidArea.height);
			// UI
			ui.draw(g2);
		}		
	

		g2.dispose();
	}
}
