import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The player selects a mode on this screen
 * 
 * @author Kinnel Tsang
 * @version June 16, 2022
 */
public class SelectMode extends World
{
    private static boolean chaotic;
    
    /**
     * Constructor for SelectMode class.
     */
    public SelectMode()
    {    
        super(1100, 500, 1); 
    }
    
    // The screen will tell the user what actions
    // they can do to access each mode.
    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            chaotic = false;
            Greenfoot.setWorld(new Alley(0));
        }
        else if(Greenfoot.isKeyDown("space"))
        {
            chaotic = true;
            Greenfoot.setWorld(new Alley(0));
        }
        else if(Greenfoot.isKeyDown("escape"))
        {
            Greenfoot.setWorld(new TitleScreen());
        }
        else if(Greenfoot.isKeyDown("shift"))
        {
            chaotic = false;
            Greenfoot.setWorld(new LevelSelect());
        }
    }
    
    // Tells the game world whether the game has moderated
    // physics or not.
    public static boolean getMode()
    {
        return chaotic;
    }
}
