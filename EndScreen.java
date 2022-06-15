import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is the game world where the player will play the game.
 * 
 * @author Kinnel Tsang
 * @version June 15, 2022
 */
public class EndScreen extends World
{
    private int strokes;
    private int delay;
    private GreenfootSound music;

    /**
     * Creates a the endscreen.
     * 
     * @param strokes: The number of strokes the player took
     * @param win: Displays the number of strokes on the screen
     * if they won. If they lost, shows a 'you lose' text.
     */
    public EndScreen(int strokes, boolean win)
    {    
        super(1100, 500, 1); 
        this.strokes = strokes;
        Alley.stopMusic();
        delay = 10;
        
        if(win)
        {
            addObject(new Label("Strokes: " + strokes, 50), 850, 325);
            music = new GreenfootSound("WinTheme.mp3");
        }
        else
        {
            addObject(new Label("You lose", 50), 850, 325);
            music = new GreenfootSound("LoseTheme.mp3");
        }     
        music.setVolume(40);
        music.play();
    }
    
    public void act()
    {
        // Returns to the title screen when the user clicks the screen
        if(Greenfoot.mouseClicked(this) && delay <= 0)
        {
            music.stop();
            TitleScreen.playMusic();
            Greenfoot.setWorld(new TitleScreen());
        }
        delay--;
    }
}
