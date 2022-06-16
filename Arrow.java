import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shows an arrow to help the player when aiming to put the
 * golf ball.
 * 
 * @author Kinnel Tsang
 * @version June 16, 2022
 */
public class Arrow extends SmoothMover
{
    private int height;
    private int inHeight;
    GreenfootImage image = getImage();
    
    /**
     * Constructor for an object of the Arrow class.
     * 
     * @param height: Sets the height or size of the arrow.
     */
    public Arrow(int height)
    {
        this.height = height;
        inHeight = image.getHeight();
    }
    
    // Constantly scales the image based on its height
    public void act()
    {
        image.scale(image.getWidth(), inHeight * (2 * height));
    }
    
    /**
     * Sets the height of the arrow.
     * 
     * @param height: Sets the height or size of the arrow.
     */
    public void setHeight(int height)
    {
        if(height <= 0)
        {
            height = 1;
        }
        else
        {
            this.height = height;
        }
    }
    
    /**
     * Makes the arrow invisible.
     */
    public void disappear()
    {
        image.setTransparency(0);
    }
    
    /**
     * Makes the arrow visible.
     */
    public void reappear()
    {
        image.setTransparency(255);
    }
}
