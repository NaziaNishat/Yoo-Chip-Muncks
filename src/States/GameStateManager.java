package States;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 8;
	public static final int MENUSTATE = 0;
        public static final int LEVELMENU = 7;
	public static final int LEVEL1STATE = 1;
	public static final int HELPSTATE = 2;
	public static final int LEVEL2STATE = 3;
	public static final int LEVEL3STATE = 4;

	
	public GameStateManager()
	{
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = 0;
		loadState(currentState);
	
	}
	private void loadState(int state)
	{
		if(state ==MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == HELPSTATE)
			gameStates[state] = new HelpState(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
                if(state == LEVELMENU)
			gameStates[state] = new LevelMenu(this);
		
	}
	private void unloadState(int state)
	{
		gameStates[state] = null;
	}
	public void setState(int state)
	{
		unloadState(currentState);
		currentState = state;
		loadState(state);
		
	}
	
	public void update()
	{
		try{
			
		
		gameStates[currentState].update();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void draw(java.awt.Graphics2D g)
	{
		try{
		gameStates[currentState].draw(g);
		}catch(Exception e){
		e.printStackTrace();
		}
	}
	public void keyPressed(int k)
	{
		gameStates[currentState].keyPressed(k);
	}
	public void keyReleased(int k)
	{
		gameStates[currentState].keyReleased(k);
	}
}
