import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndScreen extends World
{
    private int strokes;
    private int delay;
    private GreenfootSound music;

    // Constructor takes in number of strokes counted from the game World
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
