package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import MapGenerator.Voronoi;
import character.Player;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	Player player;
	public boolean upPressed, downPressed, leftPressed, rightPressed, shoot;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		// TITLE STATE
		if (gp.gameState == gp.titleState) {

			if (gp.ui.titleSubState == 0) {
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 2;
					}
				}

				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum = 0;
					}
				}

				if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
//						gp.gameState = gp.playState;
						gp.ui.titleSubState = 1;
					}
					if (gp.ui.commandNum == 1) {

					}
					if (gp.ui.commandNum == 2) {
						System.exit(0);
					}
				}
			}

			if (gp.ui.titleSubState == 1) {
				if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
					gp.ui.mapNum--;
					gp.ui.commandNum = 0;
					if (gp.ui.mapNum < 0) {
						gp.ui.mapNum = 2;
					}

				}
				if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
					gp.ui.mapNum++;
					gp.ui.commandNum = 0;
					if (gp.ui.mapNum > 2) {
						gp.ui.mapNum = 0;
					}
				}
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.ui.commandNum = 1;

				}

				if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 1) {
						gp.worldWidth = gp.worldWidth / gp.sizeMultiplier;
						gp.worldHeight = gp.worldHeight / gp.sizeMultiplier;
						
						if (gp.ui.mapNum == 0) {
							gp.sizeMultiplier = 2;
							gp.worldWidth = gp.worldWidth * gp.sizeMultiplier;
							gp.worldHeight = gp.worldHeight * gp.sizeMultiplier;
							gp.player.worldX = gp.worldWidth / 3;
							gp.player.worldY = gp.worldHeight / 3;

							gp.map = new Voronoi(gp.worldWidth, gp.worldHeight, gp);
							gp.gameState = gp.playState;
						}
						if (gp.ui.mapNum == 1) {
							gp.sizeMultiplier = 3;
							gp.worldWidth = gp.worldWidth * gp.sizeMultiplier;
							gp.worldHeight = gp.worldHeight * gp.sizeMultiplier;
							gp.player.worldX = gp.worldWidth / 3;
							gp.player.worldY = gp.worldHeight / 3;

							gp.map = new Voronoi(gp.worldWidth, gp.worldHeight, gp);
							gp.gameState = gp.playState;
						}
						if (gp.ui.mapNum == 2) {
							gp.sizeMultiplier = 4;
							gp.worldWidth = gp.worldWidth * gp.sizeMultiplier;
							gp.worldHeight = gp.worldHeight * gp.sizeMultiplier;
							gp.player.worldX = gp.worldWidth / 3;
							gp.player.worldY = gp.worldHeight / 3;

							gp.map = new Voronoi(gp.worldWidth, gp.worldHeight, gp);
							gp.gameState = gp.playState;
							
							
							
						}
						gp.ui.titleSubState = 0;
					}

				}

			}

		}

		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {

			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 1;
				}

			}

			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}

			}

			if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
				}
				if (gp.ui.commandNum == 1) {
					gp.gameState = gp.titleState;
				}
			}
		}

		// PLAY STATE
		if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = true;
			}

			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}

			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}

			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}

			if (code == KeyEvent.VK_SPACE) {
				shoot = true;
			}
			
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.pauseState;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}

		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}

		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		
		if (code == KeyEvent.VK_SPACE) {
			shoot = false;
		}

	}

}
