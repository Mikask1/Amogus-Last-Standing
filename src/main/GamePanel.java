package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import MapGenerator.Voronoi;
import bullet.Bullet;
import character.Character;
import character.Player;
import character.monster.MonBat;
import character.monster.MonMushroom;;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// SYSTEM
	public CollisionChecker cChecker = new CollisionChecker(this);
	KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	Thread gameThread;

	// SCREEN % WORLD SETTINGS
	final int originalTileSize = 16; // 16 x 16 tile
	final int scale = 3;

	public int tileSize = originalTileSize * scale; // 48 x 48 tile
	public int maxScreenCol = 26;
	public int maxScreenRow = 15;
	public int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public int screenHeight = tileSize * maxScreenRow; // 576 pixels

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

	// ENTITY & OBJECT
	public Player player = new Player(this, keyH);
	public Vector<Character> monsters = new Vector<Character>();

	public Voronoi map = new Voronoi(worldWidth, worldHeight, this);

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public int wave = 0;

	public GamePanel() {
		try {
			backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tiles/bigGrass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keyH);
	}

	public void setupGame(int multiplier) {
		// RESET
		worldWidth = worldWidth / sizeMultiplier;
		worldHeight = worldHeight / sizeMultiplier;

		monsters.clear();
		
		wave = 0;
		// SETUP
		sizeMultiplier = multiplier;
		worldWidth = worldWidth * sizeMultiplier;
		worldHeight = worldHeight * sizeMultiplier;
		player.worldX = worldWidth / 3;
		player.worldY = worldHeight / 3;

		map = new Voronoi(worldWidth, worldHeight, this);
		gameState = playState;
	}

	public void startGameThread() {
		gameState = titleState;

		gameThread = new Thread(this);
		gameThread.start();
	}

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
//				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}

	}

	public void update() {
		if (gameState == playState) {
			screenX = player.screenX - player.worldX;
			screenY = player.screenY - player.worldY;
			player.update();
			
			int idx = 0;

			for (int i = 0; i < monsters.size(); i++) {
				Character monster = monsters.get(i);
				monster.setAction();
				monster.update();

				if (!monster.alive) {
					monsters.remove(idx);
				}
				idx++;
			}

			for (Bullet playerBt : player.bullets) {
				playerBt.update();
			}

			for (int i = 0; i < player.bullets.size(); i++) {
				Bullet bullet = player.bullets.get(i);
				if (!map.inside(screenX + bullet.worldX + bullet.solidArea.x,
						screenY + bullet.worldY + bullet.solidArea.y, bullet.solidArea.width,
						bullet.solidArea.height)) {
					player.bullets.remove(i);

				}
			}
			
			if (monsters.isEmpty()) {
				wave++;
				
				switch(wave) {
				case 1:
					monsters.add(new MonBat(this));
					break;
				case 2:
					monsters.add(new MonMushroom(this));
					monsters.add(new MonBat(this));
					break;
				case 3:
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonBat(this));
					break;
				case 4:
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					break;
				case 5:
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					break;
				case 6:
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonMushroom(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					monsters.add(new MonBat(this));
					break;
				}					
			}
		}

		if (gameState == pauseState) {
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Title Screen
		if (gameState == titleState) {
			ui.draw(g2);
		} else {
			g2.drawImage(backgroundImage, screenX - (int) (1.5 * screenWidth), screenY - (int) (1.5 * screenHeight),
					map.mapWidth + (int) (2 * screenWidth), map.mapHeight + (int) (2 * screenHeight), null);

			// Map
			map.drawCellColors(g2);

			// Player
			player.draw(g2);
			g2.setColor(Color.blue);
			g2.drawRect(player.screenX + player.solidArea.x, player.screenY + player.solidArea.y,
					player.solidArea.width, player.solidArea.height);

			// Monsters

			for (Character monster : monsters) {
				monster.draw(g2);
				g2.setColor(Color.red);
				g2.drawRect(screenX + monster.worldX + monster.footArea.x,
						screenY + monster.worldY + monster.footArea.y, monster.footArea.width, monster.footArea.height);
				g2.setColor(Color.BLUE);
				g2.drawRect(screenX + monster.worldX + monster.solidArea.x,
						screenY + monster.worldY + monster.solidArea.y, monster.solidArea.width,
						monster.solidArea.height);
			}

			// Bullets
			player.drawBullets(g2);

			// UI
			ui.draw(g2);
			
			// Wave
			g2.setColor(Color.white);
			g2.setFont(UI.OEM8514.deriveFont(Font.PLAIN, 20F));
			g2.drawString("Wave: " + wave, screenWidth - 130, 25);
		}

		g2.dispose();
	}
}
