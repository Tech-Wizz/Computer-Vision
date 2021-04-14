package imp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {

	BufferedImage grid;
	Graphics2D gc;
	int rgb[];

	public MyPanel(int[] r) {
		rgb = r;
	}

	public void clear() {
		grid = null;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (grid == null) {
			int w = this.getWidth();
			int h = this.getHeight();
			grid = (BufferedImage) (this.createImage(w, h));
			gc = grid.createGraphics();

		}
		g2.drawImage(grid, null, 0, 0);
	}

	public void drawHistogram() {
		for (int i = 20; i < 276; i++) {
			gc.drawLine(i, 575, i, 575 - (int) (rgb[i - 20] / 1));
		}
		repaint();
	}
}