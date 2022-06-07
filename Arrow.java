import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arrow extends SmoothMover
{
    private int height;
    GreenfootImage image = getImage();
    
    public Arrow(int height)
    {
        this.height = height;
    }
    
    public void act()
    {
        image.scale(image.getWidth(), height);
    }
    
    public void setHeight(int height)
    {
        if(height <= 0)
        {
            height = 1;
        }
        else
        {
            this.height = height;
        }
    }
    
    public void disappear()
    {
        image.setTransparency(0);
    }
    
    public void reappear()
    {
        image.setTransparency(255);
    }
}
