import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Circle class creates a golf ball used in the game world.
 * The player will be trying to fire this ball into the hole.
 * 
 * @author Kinnel Tsang
 * @version June 16, 2022
 */
public class Circle extends SmoothMover
{
    // The following variables are used for the movement of the ball
    private double speed;
    private int rotation;
    private double slowDown;
    private boolean move = false;
    private double prevX;
    private double prevY;
    private Actor curWall;
    
    // This variable makkes the ball's physics be weird if true
    private boolean chaotic;
    
    // The following determine whether the player wins or loses
    public static boolean win = false;
    public static boolean lose = false;
    
    // The following are sounds
    private GreenfootSound in = new GreenfootSound("BallIn.mp3");
    private GreenfootSound out = new GreenfootSound("BallRattle.mp3");
    private GreenfootSound bounce;
    private GreenfootSound dropped = new GreenfootSound("Water.mp3");
    
    /**
     * Constructor to create an object of Circle class.
     */
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
                
                // If the game mode is 'augmented fiziks', then it will bounce 
                // off walls at random angles
                if(chaotic)
                {
                    setRotation(Greenfoot.getRandomNumber(360));
                }
                bounce.stop();
                bounce.play();
            }
            
            // Collision with boundaries, makes ball bounce off edges of the world
            if(getX() + 10 >= 1100)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
                setLocation(1089, getExactY());
                setLastPosition();
                bounce.stop();
                bounce.play();
            }
            if(getX() - 10 <= 0)
            {
                rotation = 180 - rotation;
                setRotation(rotation);
                setLocation(11, getExactY());
                setLastPosition();
                bounce.play();
            }
            if(getY() + 10 >= 500)
            {
                rotation = -rotation;
                setRotation(rotation);
                setLocation(getExactX(), 489);
                setLastPosition();
                bounce.stop();
                bounce.play();
            }
            if(getY() - 10 <= 0)
            {
                rotation = -rotation;
                setRotation(rotation);
                setLocation(getExactX(), 11);
                setLastPosition();
                bounce.stop();
                bounce.play();
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
                        out.play();
                    }
                    else
                    {
                        win = true;
                        in.play();
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
                    dropped.play();
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
    
    /**
     * Makes the ball move based on the given parameters.
     * 
     * @param speed: Sets the speed of the ball, how fast it will move.
     * @param rotation: Sets the rotation of the ball, at which direction it will move.
     * @param chaotic: Sets the type of physics used. If true, the physics is wacky and
     * the sounds or crazy. If false, the physics and sounds used will be basic.
     */
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
            bounce = new GreenfootSound("ChaosBounce.mp3");
        }
        else
        {
            slowDown = 0.05;
            this.speed = speed;
            bounce = new GreenfootSound("BallBounce.mp3");
        }
    }
    
    // This allows the program to understand where the ball came from
    // Fixes weird bounces off walls
    private void setLastPosition()
    {
        prevX = getExactX();
        prevY = getExactY();
    }
    
    /**
     * Pauses the ball's movement.
     */
    public void stop()
    {
        move = false;
    }
    
    /**
     * Resumes the ball's movement.
     */
    public void resume()
    {
        move = true;     
    }
    
    /**
     * Resets the ball's speed and rotation.
     */
    public void reset()
    {
        speed = 0;
        rotation = 0;
    }
}
