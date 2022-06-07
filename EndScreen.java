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

    // Constructor takes in number of strokes counted from the game World
    public EndScreen(int strokes)
    {    
        super(1100, 500, 1); 
        this.strokes = strokes;
        addObject(new Label("Strokes: " + strokes, 50), 850, 325);
    }
    
    public void act()
    {
        // Returns to the title screen when the user clicks the screen
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new TitleScreen());
        }
    }
}
