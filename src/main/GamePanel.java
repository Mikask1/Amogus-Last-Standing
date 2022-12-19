package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.xml.crypto.dsig.keyinfo.PGPData;

import MapGenerator.Voronoi;
import bullet.Bullet;
import character.Player;
import character.monster.MonBat;
import character.monster.MonFireBat;
import character.monster.MonIceBat;
import character.monster.MonLilMushroom;
import character.monster.MonMushroom;
import character.monster.MonMushroomCharge;
import character.monster.Monster;;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// CONSTANTS
	public static final long NANO_TO_MILI = 1000000;
	
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

	// FPS
	int FPS = 120;
	public long stopwatch = 0;

	// ENTITY & OBJECT
	BufferedImage backgroundImage, onFireStatusEffect, freezeStatusEffect;
	public Player player = new Player(this, keyH);
	public Vector<Monster> monsters = new Vector<Monster>();
	public Voronoi map = new Voronoi(worldWidth, worldHeight, this);
	Random rnd = new Random();
	Sound sound = new Sound();
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;

	public int wave = 0;	
	boolean animateWave = false;
	long animationTimer = stopwatch;
	long animationDuration = 1000; // in milisecond

	public GamePanel() {
		try {
			backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tiles/bigGrass.png"));
			onFireStatusEffect = ImageIO.read(getClass().getResourceAsStream("/status/onFire.png"));
			freezeStatusEffect = ImageIO.read(getClass().getResourceAsStream("/status/freeze.png"));
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
		player.setDefaultValues();
		
		map = new Voronoi(worldWidth, worldHeight, this);
		gameState = playState;
	}

	public void startGameThread() {
		playMusic(0);
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

			for (int i = 0; i < monsters.size(); i++) {
				Monster monster = monsters.get(i);
				monster.setAction();
				monster.update();

				for (int j = 0; j < monster.monBullets.size(); j++) {
					Bullet bullet = monster.monBullets.get(j);
					bullet.update();
					int bulletX = screenX + bullet.worldX + bullet.solidArea.x;
					int bulletY = screenY + bullet.worldY + bullet.solidArea.y;
					if (bulletX < screenX - screenX - (int) (1.5 * screenWidth)
							|| bulletX > map.mapWidth + (int) (2 * screenWidth)
							|| bulletY < screenY - (int) (1.5 * screenHeight)
							|| bulletY > map.mapHeight + (int) (2 * screenHeight)) {
						monster.monBullets.remove(j);
					}
				}

				cChecker.checkBulletHitsMonster(monster);
				cChecker.checkBulletHitsPlayer(monster);
				cChecker.monsterBodyHitPlayer(player, monster);

				if (!monster.alive) {
					monsters.remove(i);
				}
			}

			for (Bullet playerBt : player.bullets) {
				playerBt.update();
			}

			for (int i = 0; i < player.bullets.size(); i++) {
				Bullet bullet = player.bullets.get(i);
				int bulletX = screenX + bullet.worldX + bullet.solidArea.x;
				int bulletY = screenY + bullet.worldY + bullet.solidArea.y;
				if (bulletX < screenX - screenX - (int) (1.5 * screenWidth)
						|| bulletX > map.mapWidth + (int) (2 * screenWidth)
						|| bulletY < screenY - (int) (1.5 * screenHeight)
						|| bulletY > map.mapHeight + (int) (2 * screenHeight)) {
					player.bullets.remove(i);
				}
			}

			wave();
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

			// Monsters

			for (Monster monster : monsters) {
				monster.draw(g2);
				
				monster.drawBullets(g2);
			}

			// Bullets
			player.drawBullets(g2);

			// UI
			ui.draw(g2);

			// Overlay
			g2.setColor(Color.white);
			g2.setFont(UI.OEM8514.deriveFont(Font.PLAIN, 20F));
			g2.drawString("Wave: " + wave, screenWidth - 130, 25);

			if (stopwatch - animationTimer < animationDuration * 1000000) {
				int alpha = (int) ((double) ((stopwatch - animationTimer) / 1000000) / animationDuration * 255);
				g2.setColor(new Color(255, 255, 255, alpha));
				g2.setFont(UI.OEM8514.deriveFont(Font.PLAIN, 50F));
				g2.drawString("Wave:" + wave, screenWidth / 2 - 100, screenHeight / 2 - 25);
			} else if (stopwatch - animationTimer < 2 * animationDuration * 1000000) {
				int alpha = (int) (255
						- ((double) ((stopwatch - animationTimer) / 1000000) / animationDuration * 255) / 2);
				g2.setColor(new Color(255, 255, 255, alpha));
				g2.setFont(UI.OEM8514.deriveFont(Font.PLAIN, 50F));
				g2.drawString("Wave:" + wave, screenWidth / 2 - 100, screenHeight / 2 - 25);
			}
			
			// Player Stats
			g2.setColor(Color.white);
			g2.setFont(UI.OEM8514.deriveFont(Font.PLAIN, 20F));
			g2.drawString("HP:" + player.getHealth(), 25, 25);
			g2.drawString("Damage:" + player.getBulletDamage(), 25, 50);
			g2.drawString("Attack Speed:" + player.getShootSpeed(), 25, 75);
			g2.drawString("Walk Speed:" + player.getSpeed(), 25, 100);

			// Status Effects
			if (player.onFire) {
				g2.setColor(Color.black);
				g2.fillRect(23, 108, 24, 24); // stroke
				g2.drawImage(onFireStatusEffect, 25, 110, 20, 20, null);
			}
			
			if (player.freeze) {
				g2.setColor(Color.BLACK);
				g2.fillRect(55, 108, 24, 24);
				g2.drawImage(freezeStatusEffect, 57, 110, 20, 20, null);
			}

		}

		g2.dispose();
	}
	
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSE(int i) {
		sound.setFile(i);
		sound.play();
	}

	public void wave() {
		if (monsters.isEmpty()) {
			animateWave = true;
			animationTimer = stopwatch;
			if (wave > 0) {
				ui.pauseSubState = 1;
				gameState = pauseState;
			}
			wave++;
			playSE(Sound.StartGame);

			if (wave >= 1 && wave <= 3) {
				int funcLower = (int) (1 * Math.sqrt(wave));
				int funcUpper = (int) (3 * Math.sqrt(wave));
				
				if (funcLower == funcUpper) {
					funcUpper += 1;
				}
				
				for (int i = 0; i < rnd.nextInt(funcLower, funcUpper); i++) {				
					monsters.add(new MonBat(this));
				}
			}

			if (wave == 2 && wave >= 5) {
				int funcLower = (int) (2 * Math.sqrt(wave));
				int funcUpper = (int) (3 * Math.sqrt(wave));
				
				if (funcLower == funcUpper) {
					funcUpper += 1;
				}
				
				for (int i = 0; i < rnd.nextInt(funcLower, funcUpper); i++) {
					monsters.add(new MonMushroom(this));					
				}
			}
			
			if (wave >= 3 && wave != 4 && wave != 5 && wave != 7 && wave != 8) {
				int funcLower = (int) (Math.pow(wave, 1.1));
				int funcUpper = (int) (Math.pow(wave, 1.15));
				
				if (funcLower == funcUpper) {
					funcUpper += 1;
				}
				for (int i = 0; i < rnd.nextInt(funcLower, funcUpper); i++) {
					monsters.add(new MonLilMushroom(this));				
				}		
			}
			
			if (wave >= 4 && wave != 5 && wave != 6 && wave != 8) {
				int funcLower = (int) (1 * Math.sqrt(wave));
				int funcUpper = (int) (2 * Math.sqrt(wave));
				
				if (funcLower == funcUpper) {
					funcUpper += 1;
				}
				
				for (int i = 0; i < rnd.nextInt(funcLower, funcUpper); i++) {
					monsters.add(new MonMushroomCharge(this));			
				}
			}
			
			if (wave >= 5 && wave != 6 && wave!= 7) {
				int funcLower = (int) (Math.pow(wave, 1.1));
				int funcUpper = (int) (Math.pow(wave, 1.15));

				if (funcLower == funcUpper) {
					funcUpper += 1;
				}
				for (int i = 0; i < rnd.nextInt(funcLower, funcUpper); i++) {
					monsters.add(new MonIceBat(this));
				}		
			}
			
			if (wave >= 6) {
				int funcLower = (int) (Math.pow(wave, 1.1));
				int funcUpper = (int) (Math.pow(wave, 1.15));

				if (funcLower == funcUpper) {
					funcUpper += 1;
				}
				for (int i = 0; i < rnd.nextInt(funcLower, funcUpper); i++) {
					monsters.add(new MonFireBat(this));
				}		
			}

		}
	}
}
