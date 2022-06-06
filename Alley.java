import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is the random game world. Will randomize objects
 * 
 * @KT
 * @version (a version number or a date)
 */
public class Alley extends World
{
    private MouseInfo mouse; 
    private Circle object = new Circle();
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
    private static boolean reset = true;
    
    private Label check;
    private Wall[] walls;
    
    public Alley()
    {    
        // Create a new world
        super(1100, 500, 1); 
        // Initializes the world by adding all necessary objects and variables
        reset = true;
        strokes = 0;
        addObject(object, 200, 250);
        check = new Label(angleX + ", " + angleY, 50);
        addObject(check, 650, 50);
        addObject(hole, 900, 250);
        
        // Randomizes the number of walls and adds them at random locations
        walls = new Wall[Greenfoot.getRandomNumber(7) + 4];
        for(int i = 0; i < walls.length; i++)
        {
            walls[i] = new Wall();
            height = (Greenfoot.getRandomNumber(9) + 1) * 50;
            addObject(walls[i], 500, height);
        } 
    }
    
    public void act()
    {
        /* The following code is used to calculate the launch
         * angle and speed of the ball relative to the user's mouse.
         * It can only be activated when the ball is not moving and when 
         * the mouse is in the world.
         */
        mouse = Greenfoot.getMouseInfo();

        if(mouse != null && mouse.getActor() == object && mouse.getButton() == 1 && !released && reset)
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
            if(angleX >= object.getExactX())
            {
                rotation = Math.PI + Math.atan((object.getExactY() - angleY) / (object.getExactX() - angleX));
            }
            else
            {
                rotation = Math.atan((object.getExactY() - angleY) / (object.getExactX() - angleX));
            }
            if(speed > 10)
            {
                speed = 10;
            }
            object.startMoving(speed, rotation);
            released = false;
            reset = false;
            strokes++;
        }
        
        // If the ball lands in the hole, the game ends and the player wins
        if(Circle.win)
        {
            Greenfoot.setWorld(new EndScreen(strokes));
        }
    }
    
    public static void reset()
    {
        reset = true;
    }
}
