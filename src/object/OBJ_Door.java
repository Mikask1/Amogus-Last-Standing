package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{
	
	public OBJ_Door() {
		
		name = "chest";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			
		}catch(IOException e) {
			e.printStackTrace();		}
		
	}

}
