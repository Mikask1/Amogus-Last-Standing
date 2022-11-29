package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import MapGenerator.Voronoi;
import character.Player;;

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
	public int sizeMultiplier = 2; // minimal 2
	public int maxWorldCol = sizeMultiplier * maxScreenCol;
	public int maxWorldRow = sizeMultiplier * maxScreenRow;
	public int worldWidth = tileSize * maxWorldCol;
	public int worldHeight = tileSize * maxWorldRow;
	public int oriWorldWidth = worldWidth;
	public int oriWorldHeight = worldHeight;

	public int screenX = 0;
	public int screenY = 0;

	// FPS
	int FPS = 120;

	// SYSTEM
	public CollisionChecker cChecker = new CollisionChecker(this);
	KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	Thread gameThread;

	// ENTITY & OBJECT
	public Player player = new Player(this, keyH);
	
	public Voronoi map = new Voronoi(worldWidth, worldHeight, this);

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	
	public GamePanel() {
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
			// Map
			map.drawCellColors(g2);
				
			// Player
			player.draw(g2);
					
			// UI
			ui.draw(g2);
		}		
		
//		g2.setColor(Color.blue);
//		g2.drawRect(player.screenX + player.solidArea.x, player.screenY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

		g2.dispose();
	}
}
