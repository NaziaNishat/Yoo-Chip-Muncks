package States;

import Enemies.Jumboo;
import Enemies.Boss1;
import Enemies.Badur;
import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Object;
import ObjectsStuff.Coin;
import Music.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

public class Level2State extends GameState {
	
public BufferedImage img,img1;
public Explosion expo;
public static String sc= "Score: ";
    
    private AudioPlayer bgMusic;
	private AudioPlayer item;
	private TileMap tileMap;
	private Background bg;
	Random rand = new Random();
	
	private static Player player;
	private boolean deathScreen, gameOver, levelStart, messagePlayed;
	private long deathScreenTimer, levelStartTimerDiff, levelStartTimer = 0;
	private long deathScreenDelay = 2000;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private ArrayList<Object> objects;

	
	private ScoreBox hud;
	
	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		
	}
	
	public void init() {
		
		levelStart = true;
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/level2_map.png");
		tileMap.loadMap("/Maps/level2-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		try {
                img=ImageIO.read(getClass().getResourceAsStream("/Backgrounds/lose.jpg"));
                img1=ImageIO.read(getClass().getResourceAsStream("/Backgrounds/Over.jpg"));
            } catch (IOException ex) {
                Logger.getLogger(Level1State.class.getName()).log(Level.SEVERE, null, ex);
            }
		
		bg = new Background("/Backgrounds/Level2bgR.jpg", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(0, 180);
		player.setLevel(2);
		
		

		
	System.out.println(player.getHealth());
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		objects = new ArrayList<Object>();		
		hud = new ScoreBox(player);
		item = new AudioPlayer("/SFX/item.mp3");
		bgMusic = new AudioPlayer("/Music/Fantasy_Game_Background.mp3");
		if(!player.getMute())
		{
		bgMusic.loop();
		}
		
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		Jumboo s;
		Point[] points = new Point[] {
                    new Point(rand.nextInt(200)+1500, 180),
			new Point(rand.nextInt(200)+1500, 180),
			new Point(rand.nextInt(200)+1500, 180),
			new Point(rand.nextInt(200)+1500, 180),
			new Point(300, 180),
			new Point(500, 180),

				new Point(2023, 131),
				new Point(2115, 162),
				new Point(2201, 164),
				new Point(2323, 195),
				new Point(2417, 164),
				new Point(2530, 195),
				new Point(2626, 162),
				new Point(2682, 137),
				new Point(2770, 162),
				new Point(2865, 136),
				new Point(2953, 101),
				new Point(3040, 72),
				new Point(3130, 104),
				
		};
		for(int i = 0; i < points.length; i++) {
			s = new Jumboo(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		Badur spider;
		Point[] spiderpoints = new Point[] {
				new Point(350, 0),
				new Point(600, 0),
				new Point(1050, 0),
				new Point(1090, 0),
				new Point(2000, 0),
				new Point(1600, 0),
				new Point(1650, 0),
			
			
		};
		for(int i = 0; i < spiderpoints.length; i++) {
			spider = new Badur(tileMap, spiderpoints[i].x, 00);
			spider.setPosition(spiderpoints[i].x, spiderpoints[i].y);
			enemies.add(spider);
		}
		Boss1 boss;
		Point[] bosspoints = new Point[]
				{
					
					
				};
		for(int i = 0; i < bosspoints.length; i++) {
			boss = new Boss1(tileMap);
			boss.setPosition(bosspoints[i].x, bosspoints[i].y);
			enemies.add(boss);
		}
		

		
	
		
	}
	public void setDeathScreen(boolean b)
	{
		deathScreen = b;
	}
	public void update() {
		
		if(levelStartTimer == 0&& levelStart)
		{

			levelStartTimer = System.nanoTime();

		}
		else
		{

			levelStartTimerDiff = (System.nanoTime() - levelStartTimer)/1000000;
			if(levelStartTimerDiff > 5000)
			{

				
				levelStartTimerDiff = 0;
				levelStart = false;
				
				
				
				

				
			}
		}
		if(player.getx()<= 0)
		
		{
			player.setPosition(0, 200);
		}
		//level change
		if(player.getx()>tileMap.getWidth()-player.getWidth() && enemies.size() ==0)
		{
			gsm.setState(GameStateManager.LEVEL3STATE);
			bgMusic.stop();
		}
		if(player.getx()>tileMap.getWidth()-player.getWidth() && enemies.size() != 0 && !messagePlayed)
		{
			JOptionPane.showMessageDialog(null, "You must kill all of the enemies before advancing to the next level!");
			messagePlayed = true;
		}
		
		
		//Out Of map Player
		if(player.gety()>= tileMap.getHeight()-player.getHeight()){
			player.kill();
		}
		//map top
		if(player.gety() < 0)
		{
			player.setPosition(player.getx(), 0);
		}
		if(player.isDead())
		{
			player.setPosition(0, 180);
			deathScreen = true;
			deathScreenTimer = System.nanoTime();
			player.loseLife();
			player.setHealth(5);
		}
		if (player.getLives() == 0)
		{
			gameOver = true;
		}
		
	

		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		

		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//enemy attack
		player.checkAttack(enemies);
		player.checkObjects(objects);
		
		//enemies update
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
				if(rand.nextInt(10)<2)
				{
				createExtraHeart(e.getx(),e.gety());
				}
			}
		}
		
		//update all objects
		for(int i = 0; i<objects.size(); i++){
			Object o = objects.get(i);
			o.update();
			if(o.isDead())
			{
				
				objects.remove(i);
				i--;
				if(player.getHealth()<player.getMaxHealth())
				{
				
				player.increaseHealth(1);
				item.play();
				}

				
			}
			
		}

		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
	}
	public static Player getPlayer()
	{
		return player;
	}
	private void createExtraHeart(int x, int y)
	{
		
		Coin e = new Coin(tileMap);
		e.setPosition(x,y);
		objects.add(e);
		
	}
	public void draw(Graphics2D g) {
		
	
		
		//background draw
		bg.draw(g);
		
		//tilemap draw
		tileMap.draw(g);
		
		//player draw
		player.draw(g);
		
		//enemy draw
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		//objects draw
		for(int i = 0; i<objects.size(); i++)
		{
			objects.get(i).draw(g);
		}
		
		//explosions draw
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		hud.draw(g);
                g.drawString(sc+expo.score,260,20);

		if (deathScreen == true)
		{
				long currentTime = System.nanoTime();
				long elapsed = (currentTime-deathScreenTimer)/1000000;
					if (elapsed >= deathScreenDelay)
					{
						levelStart = true;
						levelStartTimer = 0;
					}
					if(elapsed < deathScreenDelay)
					{
						g.setColor(Color.BLACK);
						g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
						Font font = new Font("Arial", Font.PLAIN, 14);
						g.setFont(font);
						g.setColor(Color.RED);
							if(!gameOver)
							{
                                                            g.drawImage(img, 0,0,null);
							}
							else if(gameOver)
							{
                                                            g.drawImage(img1, 0,0,null);
								bgMusic.stop();
							}

						}
						
						else{
							deathScreen = false;
							if(gameOver)
							{
								
								gsm.setState(GameStateManager.MENUSTATE);	
								gameOver = false;
							}
						}
	
		}
		
		
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_A) player.setLeft(true);
		if(k == KeyEvent.VK_D) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setJumping(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_SPACE) player.setFiring();
		if(k == KeyEvent.VK_M && !player.getMute()) {player.setMute(true);bgMusic.stop();}
		else if(k == KeyEvent.VK_M && player.getMute()) {player.setMute(false);bgMusic.loop();}
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setJumping(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
		if(k == KeyEvent.VK_A) player.setLeft(false);
		if(k == KeyEvent.VK_D) player.setRight(false);
	}
	
}












