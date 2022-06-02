import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Circle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Circle extends SmoothMover
{
    private double speed;
    private int rotation;
    private boolean move = false;
    
    public Circle()
    {

    }
    
    public void act() 
    {
        if(move)
        {
            move(speed);
            speed -= 0.05;
            if(isTouching(Wall.class))
            {
                if(getX() + 10 >= getOneIntersectingObject(Wall.class).getX() - 20 && getX() + 10 <= getOneIntersectingObject(Wall.class).getX() - 21)
                {
                    setRotation(180 - rotation);
                }
                else if(getY() + 10 >= getOneIntersectingObject(Wall.class).getY() - 25)
                {
                    setRotation(180 - (90 - rotation));
                }
            }
            if(speed <= 0)
            {
                move = false;
                Alley.resetReleased();
            }
        }
    }  
    
    public void startMoving(double speed, double rotation)
    {
        this.speed = speed;
        rotation = Math.toDegrees(rotation);
        this.rotation = (int) rotation;
        move = true;
        setRotation(this.rotation);
    }
}
