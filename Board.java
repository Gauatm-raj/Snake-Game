import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    //window height width
    int height=300;
    int width=300;

    int maxDots=900;// pixel of heigth & width 600*600
    int dotSize=10;// size of a dot of snake or apple

    // moving of snake to vertical and horizontal with help of array
    int x_axis[]=new int[maxDots];
    int y_axis[]=new int[maxDots];


    int dot;//snaked body length

    // position of aaple
    int apple_x;
    int apple_y;

    //make an image
    Image body,head,apple;

    //timer implement
    Timer timer;
    int Delay=200;

    //direction to go up down left right
    boolean left=true;
    boolean right=false;
    boolean up=false;
    boolean down=false;

    //boolean variable to check coolision in game
    boolean inGame=true;

    //constructor
    Board(){
        Click click=new Click();
        addKeyListener(click);
        setFocusable(true);
         setPreferredSize(new Dimension(width,height));
         setBackground(Color.BLACK);
         inGame();
         insertImage();
    }

    // method for intialise axis of snake apple
    public void inGame(){
        dot=3;
        x_axis[0]=100;
        y_axis[0]=100;
        for(int i=1;i<dot;i++){
           x_axis[i]=x_axis[0]+dotSize*i;
           y_axis[i]=y_axis[0];
        }
         // giving apple loaction in window
        appleLocation();
        timer =new Timer(Delay,this );
        timer.start();

    }

    //method for image
    public void insertImage(){
        //insertion in body
        ImageIcon bodyIcon=new ImageIcon("src/resources/dot.png");
        body=bodyIcon.getImage();

        //insertion in head
        ImageIcon headIcon=new ImageIcon("src/resources/head.png");
        head=headIcon.getImage();

        //insertion in apple
        ImageIcon appleIcon=new ImageIcon("src/resources/apple.png");
        apple=appleIcon.getImage();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDraw(g);
    }

    // draw snake and apple
    public void doDraw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<dot;i++){
                if(i==0){
                    g.drawImage(head,x_axis[0],y_axis[0],this);
                }else{
                    g.drawImage(body,x_axis[i],y_axis[i],this);
                }
            }
        }
        else{
            gameOver(g);
            timer.stop();
        }

    }

    //randomize the apple
    public void appleLocation(){
        apple_x=((int)(Math.random()*29))*dotSize;
        apple_y=((int)(Math.random()*29))*dotSize;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
       if(inGame){
           eatApple();
           move();
           collision();
       }
        repaint();
    }

    // move snake in four direction;
     public void move(){
        for(int i=dot-1;i>0;i--){
            x_axis[i]=x_axis[i-1];
            y_axis[i]=y_axis[i-1];
        }
        if(left){
           x_axis[0]-=dotSize;
        }
         if(right){
             x_axis[0]+=dotSize;
         }
         if(up){
             y_axis[0]-=dotSize;
         }
         if(down){
             y_axis[0]+=dotSize;
         }
     }
     //snake eat apple
    public void eatApple(){
        if(x_axis[0]==apple_x && y_axis[0]==apple_y){
            dot++;
            appleLocation();
        }

    }


     //new class for keyboard move
    public class Click extends KeyAdapter{

        @Override
         public void keyPressed (KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !right){
                left=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_RIGHT && !left){
                right=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_UP && !down){
                up=true;
                right=false;
                left=false;
            }
            if(key==KeyEvent.VK_DOWN && !up){
                down=true;
                right=false;
                left=false;
            }
        }
    }

    //check collison of snake with itself and with b order
    public void collision(){
        for(int i=3;i<dot;i++){
            if(i>4 && x_axis[0]==x_axis[i] && y_axis[0]==y_axis[i]){
                inGame=false;
            }
        }

        if(x_axis[0]<0 || x_axis[0]>height || y_axis[0]<0 || y_axis[0]>width){
            inGame=false;
        }
    }

    //Game over and score message on screen
    public void gameOver(Graphics g){
        String msg = "Game Over";//msg of game over
        int score = (dot-3)*10;// high score
        String Score = "Score : "+ Integer.toString(score);
        Font font=new Font("Helvetica",Font.BOLD,14);//font style and size
        FontMetrics font_h_w = getFontMetrics(font);// height and width of the font style
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(width-font_h_w.stringWidth(msg))/2,height/4);
        g.drawString(Score,(width-font_h_w.stringWidth(Score))/2,3*(height/4));
    }
}
