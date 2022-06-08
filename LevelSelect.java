import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LevelSelect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelSelect extends World
{
    private Label text;
    private Label[] levels;
    private Light light;    
    private int level;
    
    public LevelSelect()
    {    
        super(1100, 500, 1); 
        text = new Label("Press a number to select a level", 50);
        addObject(text, 375, 420);
        light = new Light();
        level = 1;
        
        addObject(light, 185 * level, 150);
        
        levels = new Label[5];
        for(int i = 0; i < levels.length; i++)
        {
            levels[i] = new Label(i + 1, 50);
            addObject(levels[i], 185 * (i + 1), 150);
        }
    }
    
    public void act()
    {
        // The following allows the user to choose a level
        if(Greenfoot.isKeyDown("1"))
        {
            text.setValue("Level 1 - Press 'space' to confirm");
            level = 1;
        }
        if(Greenfoot.isKeyDown("2"))
        {
            text.setValue("Level 2 - Press 'space' to confirm");
            level = 2;
        }
        if(Greenfoot.isKeyDown("3"))
        {
            text.setValue("Level 3 - Press 'space' to confirm");
            level = 3;
        }
        if(Greenfoot.isKeyDown("4"))
        {
            text.setValue("Level 4 - Press 'space' to confirm");
            level = 4;
        }
        if(Greenfoot.isKeyDown("5"))
        {
            text.setValue("Level 5 - Press 'space' to confirm");
            level = 5;
        }
        
        // Universal escape key, moves to the previous menu
        if(Greenfoot.isKeyDown("escape"))
        {
            Greenfoot.setWorld(new SelectMode());
        }
        
        // The following checks which level the user selected and
        // highlights it so that it is more clear to the user
        light.setLocation(185 * level, 150);
        
        // The following will be activated when the player chooses a level
        // This will change the world to the desired level
        if(Greenfoot.isKeyDown("space"))
        {
            
        }
    }
}
