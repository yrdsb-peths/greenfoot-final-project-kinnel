import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private MouseInfo mouse; 
    private Circle object = new Circle();
    private double angleX = 0;
    private double speedX = 0;
    private double angleY = 0;
    private double speedY = 0;
    private int delay = 10;
    private boolean startDrag = false;
    private boolean released = false;
    private Label check;
    
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 500, 1); 
        addObject(object, 200, 300);
        check = new Label(angleX + ", " + angleY, 50);
        addObject(check, 650, 250);
    }
    
    public void act()
    {
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null && mouse.getActor() != null && mouse.getButton() == 1)
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
            //delay--;
            //if(delay == 0)
            //{
                speedX = (object.getExactX() - angleX) / 10;
                speedY = (object.getExactY() - angleY) / 10;
                object.startMoving(speedX, speedY);
                released = false;
            //}
        }
    }
    
    public void resetReleased()
    {
        released = false;
        delay = 10;
    }
}
