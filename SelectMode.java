import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SelectMode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SelectMode extends World
{
    private static boolean chaotic;
    
    public SelectMode()
    {    
        super(1100, 500, 1); 
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            chaotic = false;
            Greenfoot.setWorld(new Alley());
        }
        else if(Greenfoot.isKeyDown("space"))
        {
            chaotic = true;
            Greenfoot.setWorld(new Alley());
        }
        else if(Greenfoot.isKeyDown("escape"))
        {
            Greenfoot.setWorld(new TitleScreen());
        }
        else if(Greenfoot.isKeyDown("l"))
        {
            chaotic = false;
            Greenfoot.setWorld(new LevelSelect());
        }
    }
    
    public static boolean getMode()
    {
        return chaotic;
    }
}
