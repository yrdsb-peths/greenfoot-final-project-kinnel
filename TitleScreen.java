import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{

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
}
