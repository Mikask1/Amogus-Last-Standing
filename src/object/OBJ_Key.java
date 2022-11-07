package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject{
	
	public OBJ_Key() {
		
		name = "baby";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/baby-amog.png"));
			
		}catch(IOException e) {
			e.printStackTrace();		}
		
	}

}
