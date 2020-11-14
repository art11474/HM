package hm;
import java.awt.*;

import javax.swing.ImageIcon;

public class Item {
	private int type;
	private int status = 0;
	Image img;

	Item(int x) {
		if (x == 1) {
			type = 1;
			img = new ImageIcon("C:\\Users\\ACER\\Documents\\NetBeansProjects\\Brickbreaker\\src\\brickbreaker\\canoe.png").getImage();
		} else {
			type = 2;
			img = new ImageIcon("C:\\Users\\ACER\\Documents\\NetBeansProjects\\Brickbreaker\\src\\brickbreaker\\three.png").getImage();
		}
	}

	public Image getimage() {
		return img;
	}

	public int gettype() {
		return type;
	}

	public int getstatus() {
		return status;
	}

	public void setstatus(int a) {
		status = a;
	}
}