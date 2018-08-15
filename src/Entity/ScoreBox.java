package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ScoreBox {
	
	private Player player;
	
	private BufferedImage image;
	private Font font, font2, font3;

	
	public ScoreBox(Player p) {
		player = p;
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(
					"/HUD/hud_final.png"
				)
			);
			font = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 12);
			font3 = new Font("Arial", Font.PLAIN, 10);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 0, null);
		g.setFont(font3);
		g.setColor(Color.WHITE);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 35, 38);
                
                
		g.drawString(player.getLives() + "/" + player.getMaxLives(),35,48);
                
                
		g.setFont(font3);
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100, 30, 57);
                
                
		g.setColor(Color.BLACK);
		g.setFont(font2);
		g.drawString("Level " +   player.getLevel(), 20, 67);
		
	}
	
}













