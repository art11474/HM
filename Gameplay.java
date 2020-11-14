package hm;
import java.util.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.sound.sampled.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    public boolean play = false;
    private int score = 0;
    static int counter = 0;
    private int totalBricks = 21;
    private int count=0;
    private Timer timer;
    private int delay = 5;

    private int playerX = 310, playerXL = 310, playerXL1 = 250, playerXR = 410, playerXR1 = 470;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = 2;

    private int ballposX2 = 120;
    private int ballposY2 = 350;
    private int ballXdir2 = 1;
    private int ballYdir2 = -2;

    private int ballposX3 = 120;
    private int ballposY3 = 350;
    private int ballXdir3 = 0;
    private int ballYdir3 = -1;

    private int L = 0, R = 0;
    private MapGenerator map;

    private Item item1;
    private Item item2;

    private int rpos;
    private int ypos = 0;
    private int ypos2 = 0;

    private int countitem1 = 0;
    private int countitem2 = 0;

    static Thread t1 = new Thread();
    static int ballcount = 3;

    int a = 1, b = 1, c = 1;

    Image img;

    public Gameplay() {
        img = new ImageIcon("C:\\Users\\ACER\\Documents\\NetBeansProjects\\Brickbreaker\\src\\brickbreaker\\5.jpg").getImage();
        map = new MapGenerator(3,7);
        item1 = new Item(1);
        item2 = new Item(2);

        Random ran = new Random();
        rpos = ran.nextInt(680) + 10;

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public class SoundBounce {

        public SoundBounce() {
            try {
                File soundFile = new File("C:\\Users\\ACER\\Documents\\NetBeansProjects\\Brickbreaker\\src\\brickbreaker\\sfx_movement_jump1.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException s) {
                s.printStackTrace();
            } catch (IOException s) {
                s.printStackTrace();
            } catch (LineUnavailableException s) {
                s.printStackTrace();
            }
        }
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 800, 800);
        g.drawImage(img, 0, 50, 700, 600, this);

        // drawing map
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.red);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // the scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // the paddle
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);
        if (item1.getstatus() == 1) {
            g.drawLine(playerXL, 550, playerXL1, 500);
            g.drawLine(playerXR, 550, playerXR1, 500);
        }

        // the ball
        g.setColor(Color.green);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if (item2.getstatus() == 1) {
            g.fillOval(ballposX2, ballposY2, 20, 20);
            g.fillOval(ballposX3, ballposY3, 20, 20);
        }

        // item1
        if (score >= 30 && score < 50) {
            countitem1 = 1;
            g.drawImage(item1.getimage(), rpos, ypos, 60, 60, (ImageObserver) this);
        }

        // item 2
        if (score > 80) {
            countitem2 = 1;
            g.drawImage(item2.getimage(), rpos, ypos2, 60, 60, (ImageObserver) this);
        }

        // when you won the game
        if (map.GettotalBricks() == 0) {
            count++;
            play = false;
            ballXdir = 0;
            ballYdir = 0;           
            if(count == 1 || count ==2){
                g.setColor(Color.WHITE);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("You Won !!", 250, 300);
                g.setColor(Color.WHITE);
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("Press (Spacebar) To Next Level", 210, 350);
            }
            else if(count == 3){
                g.setColor(Color.WHITE);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("YOU ARE THE WINNER", 210, 300);
                g.setColor(Color.WHITE);
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("Press (Enter) To Start New Game", 210, 350);
            }           
        }

        // when you lose the game
        if (ballposY > 570) {
            System.out.println("a " + a);

            if (a != 0) {
                ballcount--;
                a = 0;
                System.out.println("ball1 " + ballcount);
            }
        }
        if (ballposY2 > 570) {
            System.out.println("b " + b);
            if (b != 0) {
                ballcount--;
                b = 0;
                System.out.println("ball2 " + ballcount);
            }
        }
        if (ballposY3 > 570) {
            System.out.println("c " + c);
            if (c != 0) {
                ballcount--;
                c = 0;
                System.out.println("ball3 " + ballcount);
            }
        }
        if (ballcount == 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: " + score, 210, 300);
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Start New Game", 210, 350);
        }
        g.dispose();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (item1.getstatus() == 1) {
                if (playerX >= 540) {
                    playerX = 540;
                    playerXL = 540;
                    playerXL1 = 480;
                    playerXR = 640;
                    playerXR1 = 700;

                } else {
                    moveRight();
                }
            } else {
                if (playerX > 600) {
                    playerX = 600;
                } else {
                    moveRight();
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (item1.getstatus() == 1) {
                if (playerX < 80) {
                    playerX = 80;
                    playerXL = 80;
                    playerXL1 = 20;
                    playerXR = 180;
                    playerXR1 = 240;
                } else {
                    moveLeft();
                }
            } else {
                if (playerX < 0) {
                    playerX = 0;
                } else {
                    moveLeft();
                }

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                count = 0;
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                ballcount = 3;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                if(count==1){
                    totalBricks = 18;
                    map = new MapGenerator(4,6);
                    repaint();
                }
                else if(count==2){
                    totalBricks = 24;
                    map = new MapGenerator(5,6);
                    repaint();
                }
                else if(count == 3){
                    totalBricks = 21;
                    map = new MapGenerator(3,7);
                    repaint();
                }
                
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Z) // Left Paddle
        {
            playerXL1 += 40;
            L = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_C) // Right Paddle
        {
            playerXR1 -= 40;
            R = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Z) // Left Paddle
        {
            playerXL1 -= 40;
            L = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_C) // Right Paddle
        {
            playerXR1 += 40;
            R = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void moveRight() {
        play = true;
        playerX += 20;
        playerXL += 20;
        playerXL1 += 20;
        playerXR += 20;
        playerXR1 += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
        playerXL -= 20;
        playerXL1 -= 20;
        playerXR -= 20;
        playerXR1 -= 20;
    }

    public void actionPerformed(ActionEvent e) {
        if (play) {
            if (countitem1 == 1) {
                ypos += 1;
                if (ypos >= 550) {
                    countitem1 = 0;
                }
            }
            if (countitem2 == 1) {
                ypos2 += 1;
                if (ypos >= 550) {
                    countitem2 = 0;
                }
            }
            
            if (item2.getstatus() == 0) {
                ballposX2 = ballposX;
                ballposY2 = ballposY;
                ballposX3 = ballposX;
                ballposY3 = ballposY;
            }

            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) {
                new SoundBounce();
                ballYdir = -ballYdir;
                ballXdir = -2;
            } else if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                new SoundBounce();
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
            } else if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                new SoundBounce();
                ballYdir = -ballYdir;
            } else if (item1.getstatus() == 1) {
                if (ballposY >= 500) {
                    if (ballposX <= playerXL && ballposX >= playerXL1) {
                        new SoundBounce();
                        System.out.println("HitL");
                        ballYdir = -ballYdir;
                        if (L == 1) {
                            ballXdir = 5;
                        } else {
                            ballXdir = 3;
                        }
                    }
                    if (ballposX >= playerXR && ballposX <= playerXR1) {
                        new SoundBounce();
                        System.out.println("HitR");
                        ballYdir = -ballYdir;

                        if (R == 1) {
                            ballXdir = -5;
                        } else {
                            ballXdir = -3;
                        }
                    }
                }
            }

            if (new Rectangle(ballposX2, ballposY2, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) // ball2
            {
                new SoundBounce();
                ballYdir2 = -ballYdir2;
                ballXdir2 = -2;
            } else if (new Rectangle(ballposX2, ballposY2, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                new SoundBounce();
                ballYdir2 = -ballYdir2;
                ballXdir2 = ballXdir2 + 1;
            } else if (new Rectangle(ballposX2, ballposY2, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                new SoundBounce();
                ballYdir2 = -ballYdir2;
            }

            if (new Rectangle(ballposX3, ballposY3, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) // ball 3
            {
                new SoundBounce();
                ballYdir3 = -ballYdir3;
                ballXdir3 = -2;
            } else if (new Rectangle(ballposX3, ballposY3, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                new SoundBounce();
                ballYdir3 = -ballYdir3;
                ballXdir3 = ballXdir3 + 1;
            } else if (new Rectangle(ballposX3, ballposY3, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                new SoundBounce();
                ballYdir3 = -ballYdir3;
            }

            // hit item 1
            if (new Rectangle(rpos, ypos, 60, 60).intersects(new Rectangle(playerX, 550, 100, 8))) {
                item1.setstatus(1);
            }

            // hit item 2
            if (new Rectangle(rpos, ypos2, 60, 60).intersects(new Rectangle(playerX, 550, 100, 8))) {
                item2.setstatus(1);
                ballcount = 3;
            }

            // check map collision with the ball
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            if (i != 3) {
                                map.setBrickValue(0, i, j);
                                score += 5;
                                totalBricks--;
                            }
                            // ball hit left or right of brick
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } // ball hits top or bottom of brick
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {                     
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect2 = new Rectangle(ballposX2, ballposY2, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect2.intersects(brickRect)) {
                            if (i != 3) {
                                map.setBrickValue(0, i, j);
                                score += 5;
                                totalBricks--;
                            }
                            // ball hit right or left of brick
                            if (ballposX2 + 19 <= brickRect.x || ballposX2 + 1 >= brickRect.x + brickRect.width) {
                                ballXdir2 = -ballXdir2;
                            } // ball hits top or bottom of brick
                            else {
                                ballYdir2 = -ballYdir2;
                            }
                            break A;
                        }
                    }
                }
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                       
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect3 = new Rectangle(ballposX3, ballposY3, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect3.intersects(brickRect)) {
                            if (i != 3) {
                                map.setBrickValue(0, i, j);
                                score += 5;
                                totalBricks--;
                            }
                            // ball hit right or left of brick
                            if (ballposX3 + 19 <= brickRect.x || ballposX3 + 1 >= brickRect.x + brickRect.width) {
                                ballXdir3 = -ballXdir3;
                            } // ball hits top or bottom of brick
                            else {
                                ballYdir3 = -ballYdir3;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            ballposX2 += ballXdir2;
            ballposY2 += ballYdir2;

            ballposX3 += ballXdir3;
            ballposY3 += ballYdir3;

            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }

            if (ballposX2 < 0) // ball2
            {
                ballXdir2 = -ballXdir2;
            }
            if (ballposY2 < 0) {
                ballYdir2 = -ballYdir2;
            }
            if (ballposX2 > 670) {
                ballXdir2 = -ballXdir2;
            }

            if (ballposX3 < 0) // ball3
            {
                ballXdir3 = -ballXdir3;
            }
            if (ballposY3 < 0) {
                ballYdir3 = -ballYdir3;
            }
            if (ballposX3 > 670) {
                ballXdir3 = -ballXdir3;
            }
            repaint();
        }
    }
}
