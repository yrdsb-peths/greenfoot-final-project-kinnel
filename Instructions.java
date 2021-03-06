import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is shows the 'how to play' screen for the user.
 * 
 * @author Kinnel Tsang
 * @version June 16, 2022
 */
public class Instructions extends World
{
    // This array stores all the pages of 'how to play'
    private GreenfootImage[] frames = new GreenfootImage[7];
    private Label back = new Label("Press 'Esc' to go back", 20);
    private int curFrame;
    private int delay;
    private MouseInfo mouse;
    
    /**
     * Constructor for instructions class.
     */
    public Instructions()
    {    
        super(1100, 500, 1);
        for(int i = 0; i < frames.length; i++)
        {
            frames[i] = new GreenfootImage("images/Instructions/I" + i + ".jpg");
        }
        addObject(back, 90 ,20);
        curFrame = 0;
        setBackground(frames[curFrame]);
        delay = 10;
    }
    
    // Changes the screen depending for next or previous set of instructions
    public void act()
    {
        mouse = Greenfoot.getMouseInfo();
        
        if(Greenfoot.isKeyDown("escape"))
        {
            Greenfoot.setWorld(new TitleScreen());
        }
        
        if(mouse != null && delay <= 0)
        {
            if(mouse.getX() <= 300 && mouse.getButton() == 1)
            {
                if(curFrame > 0)
                {
                    curFrame--;
                    delay = 20;
                }
            }
            if(mouse.getX() >= 800 && mouse.getButton() == 1)
            {
                if(curFrame < 6)
                {
                    curFrame++;
                    delay = 20;
                }
            }
        }
        
        delay--;
        setBackground(frames[curFrame]);
    }
}
