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
    private Label par;
    private Light light;    
    private int level;
    private Preview preview;
    
    public LevelSelect()
    {    
        super(1100, 500, 1); 
        text = new Label("Click a number to select a level", 50);
        addObject(text, 375, 430);
        par = new Label("", 30);
        addObject(par, 280, 375);
        preview = new Preview();
        addObject(preview, 924, 416);
        light = new Light();
        level = 1;
        
        // Highlights the number chosen (makes it easier for the user to see)
        addObject(light, 185 * level, 150);
        
        // Creates the labels that explains each level
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
        if(Greenfoot.mouseClicked(levels[0]))
        {
            text.setValue("Level 1 - Press 'space' to start");
            par.setValue("Par: 2 strokes || Challenge: 1 stroke");
            level = 1;
        }
        if(Greenfoot.mouseClicked(levels[1]))
        {
            text.setValue("Level 2 - Press 'space' to start");
            par.setValue("Par: 3 strokes || Challenge: 2 strokes");
            level = 2;
        }
        if(Greenfoot.mouseClicked(levels[2]))
        {
            text.setValue("Level 3 - Press 'space' to start");
            par.setValue("Par: 4 strokes || Challenge: 2 strokes");
            level = 3;
        }
        if(Greenfoot.mouseClicked(levels[3]))
        {
            text.setValue("Level 4 - Press 'space' to start");
            par.setValue("Par: 5 strokes || Challenge: 3 strokes");
            level = 4;
        }
        if(Greenfoot.mouseClicked(levels[4]))
        {
            text.setValue("Level 5 - Press 'space' to start");
            level = 5;
        }
        
        // The following changes the preview image to the level chosen
        preview.setImage("Level" + level + ".jpg");
        
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
            Greenfoot.setWorld(new Alley(level));
        }
    }
}
