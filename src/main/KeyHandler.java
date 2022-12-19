package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import MapGenerator.Voronoi;
import bullet.Bullet;
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
					gp.playSE(Sound.Select);
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 2;
					}
				}

				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.playSE(Sound.Select);
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum = 0;
					}
				}

				if (code == KeyEvent.VK_ENTER) {
					gp.playSE(Sound.Select);
					if (gp.ui.commandNum == 0) {
//						gp.gameState = gp.playState;
						gp.ui.titleSubState = 1;
					}
					if (gp.ui.commandNum == 1) {
						gp.ui.titleSubState = 3;
					}
					if (gp.ui.commandNum == 2) {
						System.exit(0);
					}
				}
			}

			if (gp.ui.titleSubState == 1) {
				if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
					gp.playSE(Sound.Select);
					gp.ui.mapNum--;
					gp.ui.commandNum = 0;
					if (gp.ui.mapNum < 0) {
						gp.ui.mapNum = 2;
					}

				}
				if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
					gp.playSE(Sound.Select);
					gp.ui.mapNum++;
					gp.ui.commandNum = 0;
					if (gp.ui.mapNum > 2) {
						gp.ui.mapNum = 0;
					}
				}
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.playSE(Sound.Select);
					gp.ui.commandNum = 1;

				}

				if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
					gp.playSE(Sound.Select);
					if (gp.ui.commandNum == 1) {		
						if (gp.ui.mapNum == 0) {
							gp.setupGame(2);
						}
						if (gp.ui.mapNum == 1) {
							gp.setupGame(3);
						}
						if (gp.ui.mapNum == 2) {
							gp.setupGame(4);
						}
						gp.ui.titleSubState = 0;
						gp.playSE(4);
					}

				}
				
				if (code == KeyEvent.VK_ESCAPE) {
					gp.playSE(Sound.Select);
					gp.ui.titleSubState = 0;
				}

			}
			
			// GAME OVER
			if (gp.ui.titleSubState == 2) {
				
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gp.playSE(Sound.Select);
					gp.ui.commandNum--;
					if(gp.ui.commandNum < 0) {
						gp.ui.commandNum = 1;
					}
				}
				
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.playSE(Sound.Select);
					gp.ui.commandNum++;
					if(gp.ui.commandNum > 1) {
						gp.ui.commandNum = 0;
					}
				}
				
				if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
					gp.playSE(Sound.Select);
					if(gp.ui.commandNum == 0) {
						gp.ui.titleSubState = 0;
						gp.gameState = gp.titleState;
						gp.player.setHealth(10);
					}
					
					if(gp.ui.commandNum == 1) {
						System.exit(0);
					}
					
				}
			}
			
			// HELP
			if (gp.ui.titleSubState == 3) {
				
				if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
					gp.playSE(Sound.Select);
					gp.ui.wikiNum--;
					if(gp.ui.wikiNum < 0) {
						gp.ui.wikiNum = 6;
					}
				}
				
				if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
					gp.playSE(Sound.Select);
					gp.ui.wikiNum++;
					if(gp.ui.wikiNum > 6) {
						gp.ui.wikiNum = 0;
					}
				}
				
				if (code == KeyEvent.VK_ESCAPE) {
					gp.playSE(Sound.Select);
					gp.ui.titleSubState = 0;
				}
				
			}

		}
		
		

		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			
			if(gp.ui.pauseSubState == 0) {
				if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
					gp.playSE(Sound.Select);
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 1;
					}

				}

				if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
					gp.playSE(Sound.Select);
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 1) {
						gp.ui.commandNum = 0;
					}

				}

				if (code == KeyEvent.VK_ENTER) {
					gp.playSE(Sound.Select);
					if (gp.ui.commandNum == 0) {
						gp.gameState = gp.playState;
					}
					if (gp.ui.commandNum == 1) {
						gp.gameState = gp.titleState;
					}
				}
				
				if (code == KeyEvent.VK_ESCAPE) {
					gp.playSE(Sound.Select);
					gp.gameState = gp.playState;
				}
			}

			if(gp.ui.pauseSubState == 1) {
				if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
					gp.playSE(Sound.Select);
					gp.ui.powNum--;
					if(gp.ui.powNum < 0) {
						gp.ui.powNum = 3;
					}
				}
				
				if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
					gp.playSE(Sound.Select);
					gp.ui.powNum++;
					if (gp.ui.powNum > 3) {
						gp.ui.powNum = 0;
					}

				}
				
				if (code == KeyEvent.VK_ENTER) {
					gp.playSE(Sound.Select);
					if (gp.ui.powNum == 0) {
						gp.player.setHealth(gp.player.getHealth() + 10);
						gp.ui.pauseSubState = 0;
						gp.gameState = gp.playState;
					}
					if (gp.ui.powNum == 1) {
						gp.player.setBulletDamage(gp.player.getBulletDamage() + 1);
						for (Bullet bullet: gp.player.bullets) {
							bullet.setDamage(gp.player.getBulletDamage() + 1);							
						}
						gp.ui.pauseSubState = 0;
						gp.gameState = gp.playState;
					}
					if (gp.ui.powNum == 2) {
						gp.player.setShootSpeed(gp.player.getShootSpeed() + 1);
						gp.ui.pauseSubState = 0;
						gp.gameState = gp.playState;
					}
					if (gp.ui.powNum == 3) {
						gp.player.setSpeed(gp.player.getSpeed() + 1);
						gp.ui.pauseSubState = 0;
						gp.gameState = gp.playState;
					}
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
