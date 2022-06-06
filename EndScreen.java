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
    /**
     * Constructor for objects of class EndScreen.
     * 
     */
    public EndScreen(int strokes)
    {    
        super(1100, 500, 1); 
        this.strokes = strokes;
        addObject(new Label(strokes, 50), 550, 250);
    }
    
    
}
