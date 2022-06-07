import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is the random game world. Will randomize balls
 * 
 * @KT
 * @version (a version number or a date)
 */
public class Alley extends World
{
    private MouseInfo mouse; 
    private Circle ball = new Circle();
    private Hole hole = new Hole();
    
    private double angleX;
    private double angleY;
    private double speed;
    private double rotation;
    private int height = 0;
    private int strokes;
    
    private boolean startDrag = false;
    private boolean released = false;
    private boolean clicked = false;
    private boolean chaotic;
    private static boolean reset = true;
    
    private Wall[] walls;
    private Wall[] walls2;
    private Wall[] walls3;
    private Arrow aim;
    
    public Alley()
    {    
        // Create a new world
        super(1100, 500, 1); 
        // Initializes the world by adding all necessary balls and variables
        // Ordered in this manner in order for objects to appear above 
        // one another.
        reset = true;
        chaotic = SelectMode.getMode();
        strokes = 0;
        aim = new Arrow(1);
        addObject(hole, 900, 250);
        addObject(aim, 200, 250);
        addObject(ball, 200, 250);
        
        // Randomizes the number of walls and adds them at random locations
        walls = new Wall[Greenfoot.getRandomNumber(7) + 4];
        walls2 = new Wall[Greenfoot.getRandomNumber(12)];
        walls3 = new Wall[Greenfoot.getRandomNumber(8) + 4];
        for(int i = 0; i < walls.length; i++)
        {
            walls[i] = new Wall();
            height = (Greenfoot.getRandomNumber(9) + 1) * 50;
            addObject(walls[i], 400, height);
        } 
        
        for(int i = 0; i < walls2.length; i++)
        {
            walls2[i] = new Wall();
            height = (Greenfoot.getRandomNumber(9) + 1) * 50;
            addObject(walls2[i], 600, height);
        } 
        
        for(int i = 0; i < walls3.length; i++)
        {
            walls3[i] = new Wall();
            height = (Greenfoot.getRandomNumber(9) + 1) * 50;
            addObject(walls3[i], 800, height);
        } 
    }
    
    public void act()
    {
        /* The following code is used to calculate the launch
         * angle and speed of the ball relative to the user's mouse.
         * It can only be activated when the ball is not moving and when 
         * the mouse is in the world.
         * Makes use of trigonometry if this makes the code more readable.
         */
        mouse = Greenfoot.getMouseInfo();

        if(mouse != null && mouse.getActor() == ball && mouse.getButton() == 1 && !released && reset)
        {
            startDrag = true;
            aim.setLocation(ball.getX(), ball.getY());
            aim.reappear();
        }
        else if(mouse != null && startDrag)
        {
            angleX = mouse.getX();
            angleY = mouse.getY();
            aim.setHeight((int) speed * 20);
            aim.setRotation((int) Math.toDegrees(Math.PI/2 + Math.atan((ball.getExactY() - angleY) / (ball.getExactX() - angleX))));
            if(mouse.getButton() == 1)
            {
                released = true;
                startDrag = false;
            }
        }
        else if(released)
        {
            ball.startMoving(speed, rotation, chaotic);
            aim.disappear();
            released = false;
            reset = false;
            strokes++;
        }
        
        // Values for the rotation of ball and aim
        // Values for speed of ball and length of aim
        speed = Math.sqrt(Math.pow(ball.getExactX() - angleX, 2) + Math.pow(ball.getExactY() - angleY, 2)) / 20;
        if(angleX >= ball.getExactX())
        {
            rotation = Math.PI + Math.atan((ball.getExactY() - angleY) / (ball.getExactX() - angleX));
        }
        else
        {
            rotation = Math.atan((ball.getExactY() - angleY) / (ball.getExactX() - angleX));
        }
        if(speed > 10)
        {
            speed = 10;
        }
        
        // If the ball lands in the hole, the game ends and the player wins
        if(Circle.win)
        {
            Greenfoot.setWorld(new EndScreen(strokes));
        }
    }
    
    // This line would be called after each stroke ends
    // or when the ball stops moving. It allows the user to 
    // start another stroke.
    public static void reset()
    {
        reset = true;
    }
}
