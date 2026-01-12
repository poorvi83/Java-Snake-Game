import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;  //segment of snake body
import java.util.Random; //get random x,y values for food
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;
        Tile( int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    int brdWidth, brdHeight;
    int tileSize = 25;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //Game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int brdWidth, int brdHeight)
    {
        this.brdWidth= brdWidth;
        this.brdHeight= brdHeight;
        setPreferredSize(new Dimension(this.brdWidth, this.brdHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food= new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX=0;
        velocityY=0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        //Grid
        for(int i=0; i<brdWidth/tileSize; i++)
        {
            //(x1,y1,x2,y2)
            g.drawLine(i*tileSize, 0, i*tileSize, brdHeight);   //vertical
            g.drawLine(0, i*tileSize, brdWidth, i*tileSize);   //horizontal
        }

        //Food
        g.setColor(Color.red);
        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);

        //Snake
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);

        //Snake body
        for(int i=0; i< snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        //Score
        g.setFont( new Font("Arial", Font.PLAIN, 16));
        if(gameOver)
        {
            g.setColor(Color.red);
            g.drawString("Game Over: "+ String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score: "+ String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }
    public void placeFood()   //will set random coordinates of food
    {
        food.x = random.nextInt(brdWidth/tileSize);   // 600/25 = 24..i.e. x will be a random position btw 0 and 24
        food.y = random.nextInt(brdHeight/tileSize);
    }

    public boolean collision( Tile tile1, Tile tile2)
    {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move()
    {
        //eat food
        if(collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake body
        for(int i= snakeBody.size()-1; i>=0; i--)
        {
            Tile snakePart = snakeBody.get(i);
            if(i == 0)
            {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else
            {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //Game over condition
        
        for(int i=0; i < snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);
            //collide with own body or head
            if(collision(snakeHead, snakePart))
            {
                gameOver = true;
            }
        }

        if(snakeHead.x * tileSize <0 || snakeHead.x * tileSize > brdWidth || snakeHead.y * tileSize <0 || snakeHead.y * tileSize > brdHeight)
        {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed( ActionEvent e)
    {
        move();
        repaint();   //calls draw again n again
        if(gameOver)
        {
            gameLoop.stop();
        }
           
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_UP && velocityY!=1)
        {
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN && velocityY!=-1)
        {
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityX!= 1)
        {
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode()== KeyEvent.VK_RIGHT && velocityX!= -1)
        {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {  }

     @Override
    public void keyReleased(KeyEvent e) { }
       
}
