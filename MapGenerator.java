package hm;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;


public class MapGenerator {
    public int map[][];
    public char map2[][];
    public int brickWidth;
    public int brickHeight;
    public char c = 'A';
    public int n = 0;
    public int x = 0;

    public MapGenerator(int row, int col) {
        Random rand = new Random();
        map = new int[row][col];
        map2 = new char[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                n = rand.nextInt(4);
                switch (n){
                    case 0: c = 'A'; break;
                    case 1: c = 'B'; break;
                    case 2: c = 'C'; break;
                    case 3: c = 'D'; break;
                    default: c = 'A'; break;
                }
                map[i][j] = 1;
                map2[i][j] = c;
                x++;
            }
        }
        brickWidth = 540 / col;
        brickHeight = 50 / row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    if (i == 3) {
                        g.setColor(Color.gray);
                    } else {
                        g.setColor(Color.ORANGE);
                    }
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.drawString(String.valueOf(map2[i][j]), j * brickWidth + 100, i * brickHeight + 60);//60
                    // this is just to show separate brick, game can still run without it
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.red);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);//50
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        char temp = '0';
        if(value == 0){
            System.out.print("pom");
            temp = map2[row][col];
            for (int i = 0; i < map2.length; i++) {
                for (int j = 0; j < map2[0].length; j++) {

                    if(temp == map2[i][j]){
                        System.out.print("tu");
                        map[i][j] = 0;
                        map2[i][j] = '0';
                        x--;
                    }
                }
            }
        }
        map[row][col] = value;
    }
    public int GettotalBricks(){
        return x;
    }
}
