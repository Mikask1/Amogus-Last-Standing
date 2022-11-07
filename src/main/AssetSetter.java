package main;

import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Key();
		gp.obj[0].worldX = 20 * gp.tileSize;
		gp.obj[0].worldY = 20 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Key();
		gp.obj[1].worldX = 25 * gp.tileSize;
		gp.obj[1].worldY = 20 * gp.tileSize;
		
		//chest
		gp.obj[2] = new OBJ_Door();
		gp.obj[2].worldX = 23 * gp.tileSize;
		gp.obj[2].worldY = 15 * gp.tileSize;
		
	}
	
}
