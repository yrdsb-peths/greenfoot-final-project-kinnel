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
    private int edgeX;
    private int edgeY;
    private int rotation;
    private int delay;
    private boolean move = false;
    private Actor curWall;
    
    public Circle()
    {

    }
    
    public void act() 
    {
        // This will only happen when the ball is hit by the player.
        if(move)
        {
            move(speed);
            delay--;
            speed -= 0.05;
            edgeX = getX();
            edgeY = getY();
            
            // Collision with walls - makes ball bounce at reasonable angles
            if(isTouching(Wall.class))
            {
                curWall = getOneIntersectingObject(Wall.class);
                if(edgeX + 10 >= curWall.getX() - 20 && edgeX <= curWall.getX() - 15 && edgeY < curWall.getY() + 25 && edgeY> curWall.getY() - 25)
                {
                    rotation = 180 - rotation;
                    setRotation(rotation);
                }
                if(edgeX - 10 <= curWall.getX() + 20 && edgeX >= curWall.getX() + 15 && edgeY < curWall.getY() + 25 && edgeY> curWall.getY() - 25)
                {
                    rotation = 180 - rotation;
                    setRotation(180 - rotation);
                }
                if(edgeY + 10 >= curWall.getY() - 25 && edgeY <= curWall.getY() - 20 && edgeX < curWall.getX() + 20 && edgeX > curWall.getX() - 20 )
                {
                    rotation = -rotation;
                    setRotation(rotation);
                }
                if(edgeY - 10 <= curWall.getY() + 25 && edgeY >= curWall.getY() + 20 && edgeX < curWall.getX() + 20 && edgeX > curWall.getX() - 20 )
                {
                    rotation = -rotation;
                    setRotation(rotation);
                }
            }
            
            // Collision with boundaries, makes ball bounce off edges of the world
            if(edgeX + 10 >= 1100)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
            }
            if(edgeX - 10 <= 0)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
            }
            if(edgeY + 10 >= 500)
            {
                rotation = -rotation;
                setRotation(rotation);
            }
            if(edgeY - 10 <= 0)
            {
                rotation = -rotation;
                setRotation(rotation);
            }
            
            // Checks whether the ball has stopped or not. If it has stopped, the ball will no longer move and
            // the player can put the ball again.
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
        //System.out.println(this.rotation);
    }
}
