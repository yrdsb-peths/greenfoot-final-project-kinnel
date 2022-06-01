import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Circle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Circle extends SmoothMover
{
    private double speedX = 0;
    private double speedY = 0;
    private boolean move = false;
    MyWorld world;
    
    public Circle()
    {
        world = (MyWorld) getWorld();
    }
    
    public void act() 
    {
        if(move)
        {
            setLocation(getX() + speedX, getY() + speedY);
            speedX -= 0.05;
            speedY -= 0.03;
            if(speedX <= 0)
            {
                move = false;
                //world.resetReleased();
            }
        }
    }  
    
    public void startMoving(double x, double y)
    {
        speedX = x;
        speedY = y;
        move = true;
    }
}
