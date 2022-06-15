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
    public static int strokes;
    private int delay;
    
    private boolean startDrag = false;
    private boolean released = false;
    private boolean clicked = false;
    private boolean chaotic;
    private static boolean reset = true;
    private boolean pause = false;
    
    private Wall[] walls;
    private Wall[] walls2;
    private Wall[] walls3;
    private Arrow aim;
    private Label showStrokes;
    private Pause pauseMenu;
    
    // The following are sounds
    private static GreenfootSound music;
    private static GreenfootSound hit;

    /* Takes in parameter (level).
     * This parameter will dictate the position of the walls in the game.
     * If level = 0, world will have a random set of walls
     * If level = 1, level 1 mini golf stage;
     * If level = 2, level 2 mini golf stage; etc... 
     */
    public Alley(int level)
    {    
        // Create a new world
        super(1100, 500, 1); 
        // Initializes the world by adding all necessary balls and variables
        // Ordered in this manner in order for objects to appear above 
        // one another.
        reset = true;
        pause = false;
        delay = 0;
        chaotic = SelectMode.getMode();
        strokes = 0;
        aim = new Arrow(1);
        aim.disappear();
        showStrokes = new Label("Strokes: " + strokes, 25);
        
        // Stops the previous music and plays new music
        if(chaotic)
        {
            hit = new GreenfootSound("ChaosHit.mp3");
            music = new GreenfootSound("ChaosTheme.mp3");
        }
        else
        {
            hit = new GreenfootSound("BallHit.mp3");
            music = new GreenfootSound("GolfingTheme.mp3");
        }
        TitleScreen.stopMusic();
        music.setVolume(60);
        music.playLoop();
        
        // Randomizes the number of walls and adds them at random locations
        if(level == 0)
        {
            randomizeWalls();
        }
        else if(level == 1)
        {
            level1();
        }
        else if(level == 2)
        {
            level2();
        }
        else if(level == 3)
        {
            level3();
        }
        else if(level == 4)
        {
            level4();
        }
        else if(level == 5)
        {
            level5();
        }
        
        // Adds the stroke counter
        addObject(showStrokes, 55, 20);
        
        // Adds ball - Last in order for it to appear above all objects
        if(level == 5)
        {
            addObject(hole, 1050, 250);
        }
        else
        {
            addObject(hole, 900, 250);
        }
        addObject(aim, 200, 250);
        addObject(ball, 200, 250);
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
        else if(mouse != null && startDrag && delay <= 0)
        {
            angleX = mouse.getX();
            angleY = mouse.getY();
            aim.setHeight((int) speed);
            if(angleX - ball.getX() <= 0)
            {
                aim.setRotation((int) Math.toDegrees(Math.PI/2 + Math.atan((ball.getExactY() - angleY) / (ball.getExactX() - angleX))));
            }
            else
            {
                aim.setRotation((int) Math.toDegrees(-Math.PI/2 + Math.atan((ball.getExactY() - angleY) / (ball.getExactX() - angleX))));
            }
                
            if(mouse.getButton() == 1)
            {
                released = true;
                startDrag = false;
                hit.play();
            }
        }
        else if(released)
        {
            ball.startMoving(speed, rotation, chaotic);
            aim.disappear();
            released = false;
            reset = false;
            strokes++;
            showStrokes.setValue("Strokes: " + strokes);
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
        // If the number of strokes is greater than 12 or if the ball lands
        // in water, the game ends and the player loses.
        if(Circle.win)
        {
            Greenfoot.setWorld(new EndScreen(strokes, true));
        }
        if(Circle.lose)
        {
            Greenfoot.setWorld(new EndScreen(0, false));
        }
        
        /* The following 2 'if statements' are used to control the pause
         * menu of the game. When the game is paused, the ball will stop
         * moving and the player will be given the choice to either resume
         * the game, restart, or quit.
         */
        if(Greenfoot.isKeyDown("shift") && !pause)
        {
            pause = true;
            ball.stop();
            pauseMenu = new Pause();
            addObject(pauseMenu, 550, 250);
            music.setVolume(30);
        }
        
        if(pause)
        {
            if(mouse != null && mouse.getButton() == 1)
            {
                pause = false;
                ball.resume();
                removeObject(pauseMenu);
                music.setVolume(60);
            }
            else if(Greenfoot.isKeyDown("space"))
            {
                reset();
                Greenfoot.setWorld(new TitleScreen());
                pause = false;
                music.stop();
            }
            else if(Greenfoot.isKeyDown("r"))
            {
                ball.setLocation(200, 250);
                aim.setLocation(200, 250);
                ball.reset();
                strokes = 0;
                showStrokes.setValue("Strokes: " + strokes);
                removeObject(pauseMenu);
                pause = false;
                reset();
                music.setVolume(60);
            }
            delay = 10;
        }
        delay--;
    }
    
    // This line would be called after each stroke ends
    // or when the ball stops moving. It allows the user to 
    // start another stroke.
    public static void reset()
    {
        reset = true;
    }
    
    // This code randomizes the location of the walls
    public void randomizeWalls()
    {
        walls = new Wall[Greenfoot.getRandomNumber(6) + 5];
        walls2 = new Wall[Greenfoot.getRandomNumber(5) + 4];
        walls3 = new Wall[Greenfoot.getRandomNumber(7) + 5];
        for(int i = 0; i < walls.length; i++)
        {
            walls[i] = new Wall();
            height = (Greenfoot.getRandomNumber(10)) * 50 + 25;
            addObject(walls[i], 400, height);
        } 
        
        for(int i = 0; i < walls2.length; i++)
        {
            walls2[i] = new Wall();
            height = (Greenfoot.getRandomNumber(10)) * 50 + 25;
            addObject(walls2[i], 600, height);
        } 
        
        for(int i = 0; i < walls3.length; i++)
        {
            walls3[i] = new Wall();
            height = (Greenfoot.getRandomNumber(10)) * 50 + 25;
            addObject(walls3[i], 800, height);
        } 
    }
    
    /* The following methods are used to build the world for each level.
     * Each level has a unique level design that never changes, resulting
     * Iin a lot of code but all it does is create appropriate objects and
     * places them onto the world / alley.
     */
    public void level1()
    {
        addObject(new Wall(), 550, 150);
        addObject(new Wall(), 550, 200);
        addObject(new Wall(), 550, 250);
        addObject(new Wall(), 550, 300);
        addObject(new Wall(), 550, 350);
    }
    
    public void level2()
    {
        walls = new Wall[20];
        walls2 = new Wall[4];
        
        for(int i = 0; i < walls.length; i++)
        {
            walls[i] = new Wall();
            if(i <= 9)
            {
                addObject(walls[i], 40 * i + 300, 175);
            }
            else
            {
                addObject(walls[i], 40 * (i - 10) + 300, 325);
            }
        }
        
        for(int i = 0; i < walls2.length; i++)
        {
            walls2[i] = new Wall();
            addObject(walls2[i], 790, 50 * i + 175);
        }
    }
    
    public void level3()
    {
        Water[][] water = new Water[2][3];
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                water[i][j] = new Water();
                addObject(water[i][j], 400 * (i + 1), 50 + 100 * j);
            }
        }
        
        Water[] water2 = new Water[2];
        for(int k = 0; k < 2; k++)
        {
            water2[k] = new Water();
            addObject(water2[k], 600, 450 - k * 100);
        }
    }
    
    public void level4()
    {
        Water[] water = new Water[3];
        Wall[][] walled = new Wall[3][3];
        for(int i = 0; i < 3; i++)
        {
            water[i] = new Water();
            addObject(water[i], 400, 50 + 100 * i);
            for(int j = 0; j < 3; j++)
            {
                walled[i][j] = new Wall();
                if(i == 1)
                {
                    addObject(walled[i][j], 20, 475 - 50 * j);
                }
                else if(i == 2)
                {
                    addObject(walled[i][j], 330 - j * 40, 475);
                }
                else
                {
                    addObject(walled[i][j], 630, 275 - j * 50);
                }
            }
        }
        addObject(new Water(), 300, 300);
        addObject(new Water(), 400, 450);
        addObject(new Water(), 660, 350);
        addObject(new Water(), 760, 350);
        addObject(new Water(), 900, 450);
        addObject(new Water(), 1000, 450);
        addObject(new Water(), 1050, 50);
        addObject(new Water(), 1050, 150);
        addObject(new Water(), 1050, 250);
        addObject(new Water(), 1050, 350);
        
        addObject(new Wall(), 230, 275);
        addObject(new Wall(), 230, 325);
        
        Wall[] moreWalls = new Wall[6];
        for(int i = 0; i < 6; i++)
        {
            moreWalls[i] = new Wall();
            addObject(moreWalls[i], 470, 275 - i * 50);
        }
    }
    
    public void level5()
    {
        Water[][] water = new Water[2][7];
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                water[i][j] = new Water();
                addObject(water[i][j], 250 + 100 * j, 50 + 400 * i);
            }
        }
        addObject(new Water(), 300, 250);
        addObject(new Water(), 400, 250);
        addObject(new Water(), 50, 50);
        addObject(new Water(), 50, 150);
        addObject(new Water(), 50, 250);
        addObject(new Water(), 50, 350);
        addObject(new Water(), 50, 450);
        addObject(new Water(), 600, 350);
        addObject(new Water(), 600, 150);
        addObject(new Water(), 920, 250);
        
        walls = new Wall[4];
        walls2 = new Wall[4];
        walls3 = new Wall[4];
        for(int i = 0; i < 4; i++)
        {
            walls[i] = new Wall();
            walls2[i] = new Wall();
            walls3[i] = new Wall();
            addObject(walls[i], 1080, 25 + 50 * i);
            addObject(walls2[i], 1080, 475 - 50 * i);
            addObject(walls3[i], 850, 175 + 50 * i);
        }
        addObject(new Wall(), 670, 205);
        addObject(new Wall(), 710, 205);
        addObject(new Wall(), 750, 205);
        addObject(new Wall(), 670, 295);
        addObject(new Wall(), 710, 295);
        addObject(new Wall(), 750, 295);
    }
    
    // Stops the music
    public static void stopMusic()
    {
        music.stop();
    }
}
