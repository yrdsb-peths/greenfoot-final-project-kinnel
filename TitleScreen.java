import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the title screen.
 * 
 * @author Kinnel Tsang
 * @version June 16, 2022
 */
public class TitleScreen extends World
{
    private static GreenfootSound music = new GreenfootSound("Intro.mp3");
    
    /**
     * Constructor for TitleScreen class.
     */
    public TitleScreen()
    {    
        super(1100, 500, 1);
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new SelectMode());
        }
        else if(Greenfoot.isKeyDown("space"))
        {
            Greenfoot.setWorld(new Instructions());
        }
    }
    
    /**
     * 
     */
    public void started()
    {
        music.playLoop();
    }
    
    public static void stopMusic()
    {
        music.stop();
    }
    
    public static void playMusic()
    {
        music.playLoop();
    }
}
