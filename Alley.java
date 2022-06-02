import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Alley extends World
{
    private MouseInfo mouse; 
    private Circle object = new Circle();
    
    private double angleX;
    private double startX;
    private double angleY;
    private double startY;
    private double speed;
    private double rotation;
    
    private boolean startDrag = false;
    private static boolean released = false;
    private boolean clicked = false;
    
    private Label check;
    
    public Alley()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1100, 500, 1); 
        addObject(object, 200, 250);
        check = new Label(angleX + ", " + angleY, 50);
        addObject(check, 650, 250);
        startX = object.getExactX();
        startY = object.getExactY();
    }
    
    public void act()
    {
        mouse = Greenfoot.getMouseInfo();

        if(mouse != null && mouse.getActor() == object && mouse.getButton() == 1)
        {
            startDrag = true;
        }
        else if(mouse != null && startDrag)
        {
            angleX = mouse.getX();
            angleY = mouse.getY();
            check.setValue(angleX + ", " + angleY);
            if(mouse.getButton() == 1)
            {
                released = true;
                startDrag = false;
            }
        }
        else if(released)
        {
            speed = Math.sqrt(Math.pow(object.getExactX() - angleX, 2) + Math.pow(object.getExactY() - angleY, 2)) / 20;
            rotation = Math.atan((object.getExactY() - angleY) / (object.getExactX() - angleX));
            object.startMoving(speed, rotation);
            released = false;
        }
    }
    
    public static void resetReleased()
    {
        released = false;
    }
}
