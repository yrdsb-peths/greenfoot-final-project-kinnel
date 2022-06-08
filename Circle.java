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
    private double slowDown;
    private boolean move = false;
    private boolean chaotic;
    private Actor curWall;
    
    public static boolean win = false;
    
    
    public Circle()
    {
        win = false;
    }
    
    public void act() 
    {
        // This will only happen when the ball is hit by the player.
        if(move)
        {
            move(speed);
            speed -= slowDown;
                
            edgeX = getX();
            edgeY = getY();
            
            // Collision with walls - makes ball bounce at reasonable angles
            if(isTouching(Wall.class))
            {
                curWall = getOneIntersectingObject(Wall.class);
                if(edgeX + 10 >= curWall.getX() - 20 && edgeX <= curWall.getX() - 15)
                {
                    rotation = 180 - rotation;
                    setRotation(rotation);
                    setLocation(curWall.getX() - 31, getExactY());
                }
                if(edgeX - 10 <= curWall.getX() + 20 && edgeX >= curWall.getX() + 15)
                {
                    rotation = 180 - rotation;
                    setRotation(rotation);
                    setLocation(curWall.getX() + 31, getExactY());
                }
                if(edgeY + 10 >= curWall.getY() - 25 && edgeY <= curWall.getY() - 20)
                {
                    rotation = -rotation;
                    setRotation(rotation);
                    setLocation(getExactX(), curWall.getY() - 36);
                }
                if(edgeY - 10 <= curWall.getY() + 25 && edgeY >= curWall.getY() + 20)
                {
                    rotation = -rotation;
                    setRotation(rotation);
                    setLocation(getExactX(), curWall.getY() + 36);
                }
                
                if(chaotic)
                {
                    setRotation(Greenfoot.getRandomNumber(360));
                }
            }
            
            // Collision with boundaries, makes ball bounce off edges of the world
            if(edgeX + 10 >= 1100)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
                setLocation(1089, getExactY());
            }
            if(edgeX - 10 <= 0)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
                setLocation(11, getExactY());
            }
            if(edgeY + 10 >= 500)
            {
                rotation = -rotation;
                setRotation(rotation);
                setLocation(getExactX(), 489);
            }
            if(edgeY - 10 <= 0)
            {
                rotation = -rotation;
                setRotation(rotation);
                setLocation(getExactX(), 11);
            }
            
            // Collision with hole - goes in hole at low enough speeds
            if(isTouching(Hole.class))
            {
                Actor hole = getOneIntersectingObject(Hole.class);
                if(getX() + 10 >= hole.getX() && getX() - 10 <= hole.getX() && getY() + 10 >= hole.getY() && getY() - 10 <= hole.getY())
                {
                    if(speed > 3.5)
                    {
                        setRotation(Greenfoot.getRandomNumber(360));
                    }
                    else
                    {
                        getWorld().removeObject(this);
                        win = true;
                    }
                }
            }
            
            // Checks whether the ball has stopped or not. If it has stopped, the ball will no longer move and
            // the player can put the ball again.
            if(speed <= 0)
            {
                move = false;
                Alley.reset();
            }
        }
    }  
    
    public void startMoving(double speed, double rotation, boolean chaotic)
    {
        rotation = Math.toDegrees(rotation);
        this.rotation = (int) rotation;
        move = true;
        setRotation(this.rotation);
        this.chaotic = chaotic;
        if(chaotic)
        {
            slowDown = (double) (Greenfoot.getRandomNumber(5) + 1) / 100;
            this.speed = speed * (Greenfoot.getRandomNumber(3) + 1);
        }
        else
        {
            slowDown = 0.05;
            this.speed = speed;
        }
    }
}
