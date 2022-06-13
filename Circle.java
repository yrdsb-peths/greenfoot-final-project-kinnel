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
    private double slowDown;
    private boolean move = false;
    private boolean chaotic;
    private double prevX;
    private double prevY;
    private Actor curWall;
    
    public static boolean win = false;
    public static boolean lose = false;
    
    public Circle()
    {
        win = false;
        lose = false;
        prevX = 200;
        prevY = 250;
    }
    
    public void act() 
    {
        // This will only happen when the ball is hit by the player.
        if(move)
        {
            move(speed);
            speed -= slowDown;
                            
            // Collision with walls - makes ball bounce at reasonable angles
            if(isTouching(Wall.class))
            {
                curWall = getOneIntersectingObject(Wall.class);
                if(getX() + 10 >= curWall.getX() - 20 && getX() <= curWall.getX() - 15 && prevX < curWall.getX() - 20)
                {
                    rotation = 180 - rotation;
                    setRotation(rotation);
                    setLocation(curWall.getX() - 31, getExactY());
                    setLastPosition();
                }
                if(getX() - 10 <= curWall.getX() + 20 && getX() >= curWall.getX() + 15 && prevX > curWall.getX() + 20)
                {
                    rotation = 180 - rotation;
                    setRotation(rotation);
                    setLocation(curWall.getX() + 31, getExactY());
                    setLastPosition();
                }
                if(getY() + 10 >= curWall.getY() - 25 && getY() <= curWall.getY() - 20 && prevY < curWall.getY() - 25)
                {
                    rotation = -rotation;
                    setRotation(rotation);
                    setLocation(getExactX(), curWall.getY() - 36);
                    setLastPosition();
                }
                if(getY() - 10 <= curWall.getY() + 25 && getY() >= curWall.getY() + 20 && prevY > curWall.getY() + 25)
                {
                    rotation = -rotation;
                    setRotation(rotation);
                    setLocation(getExactX(), curWall.getY() + 36);
                    setLastPosition();
                }
                
                // If the game mode is augmented fiziks, then it will bounce 
                // off walls at random angles
                if(chaotic)
                {
                    setRotation(Greenfoot.getRandomNumber(360));
                }
            }
            
            // Collision with boundaries, makes ball bounce off edges of the world
            if(getX() + 10 >= 1100)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
                setLocation(1089, getExactY());
                setLastPosition();
            }
            if(getX() - 10 <= 0)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
                setLocation(11, getExactY());
                setLastPosition();
            }
            if(getY() + 10 >= 500)
            {
                rotation = -rotation;
                setRotation(rotation);
                setLocation(getExactX(), 489);
                setLastPosition();
            }
            if(getY() - 10 <= 0)
            {
                rotation = -rotation;
                setRotation(rotation);
                setLocation(getExactX(), 11);
                setLastPosition();
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
                        setLastPosition();
                    }
                    else
                    {
                        win = true;
                    }
                }
            }
            
            // Checks whether the player has landed in water.
            // If they do fall in water, the game is over. 
            if(isTouching(Water.class))
            {
                Actor water = getOneIntersectingObject(Water.class);
                if(getX() >= water.getX() - 50 && getX() <= water.getX() + 50 && getY() >= water.getY() - 50 && getY() <= water.getY() + 50)
                {
                    lose = true;
                }
            }
            
            // Checks whether the ball has stopped or not. If it has stopped, the ball will no longer move and
            // the player can put the ball again.
            if(speed <= 0)
            {
                if(Alley.strokes == 12)
                {
                    lose = true;
                }
                move = false;
                setLastPosition();
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
            if(Greenfoot.getRandomNumber(2) == 0)
            {
                this.speed = speed * (Greenfoot.getRandomNumber(3) + 1);
            }
            else
            {
                this.speed = speed / (Greenfoot.getRandomNumber(3) + 1);
            }
        }
        else
        {
            slowDown = 0.05;
            this.speed = speed;
        }
    }
    
    // This allows the program to understand where the ball came from
    // Fixes weird bounces off walls
    public void setLastPosition()
    {
        prevX = getExactX();
        prevY = getExactY();
    }
    
    public void stop()
    {
        move = false;
    }
    
    public void resume()
    {
        move = true;     
    }
    
    public void reset()
    {
        speed = 0;
        rotation = 0;
    }
}
