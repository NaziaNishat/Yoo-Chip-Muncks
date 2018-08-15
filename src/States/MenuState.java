package States;

import Music.AudioPlayer;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	
	
	private Background bg;
	private AudioPlayer bgMusic;
	private static boolean mute;
	
	private int currentChoice = 0;
	
	private String[] options = {
		"Play",
		"Help",
		"Quit"
	};
	
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/menu_background.png", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 24);
			
			font = new Font("Arial", Font.PLAIN, 12);
			bgMusic = new AudioPlayer("/Music/Game-Menu.mp3");
			bgMusic.loop();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {
		
	}
	
	public void update() {
		bg.update();	
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
                Font font = new Font("Arial", Font.BOLD, 28);
                g.setFont(font);
		g.drawString("YOO Chipmunks", 30, 70);
		
		// draw menu options
                Font font1 = new Font("Arial", Font.BOLD, 16);
                g.setFont(font1);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.BLUE);
			}
			g.drawString(options[i], 40, 120 + i * 20);
		}
		
	}
	
	
	private void select() {
		
		//start
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVELMENU);
			bgMusic.stop();
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.HELPSTATE);
			bgMusic.stop();
		}
		if(currentChoice == 2) {
			System.exit(0);
			bgMusic.stop();
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
		
		if(k == KeyEvent.VK_M && !mute){mute = true; bgMusic.stop();System.out.println(mute);}
		else if(k == KeyEvent.VK_M && mute){mute = false; bgMusic.loop();System.out.println("false");}
		
	}
	public void keyReleased(int k) {}
	public static boolean getMute()
	{
		return mute;
	}
	
}








