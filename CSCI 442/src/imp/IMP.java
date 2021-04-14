package imp;

import javax.imageio.ImageIO;

/*
 *Hunter Lloyd
 * Copyrite.......I wrote, ask permission if you want to use it outside of class. 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.prefs.Preferences;

class IMP implements MouseListener {
	JFrame frame;
	JPanel mp;
	JButton start;
	JScrollPane scroll;
	JMenuItem openItem, exitItem, resetItem;
	Toolkit toolkit;
	File pic;
	ImageIcon img;
	int[] loc = null;
	int[] locRGB;
	int colorX, colorY;
	int[] pixels;
	int[] results;
	int[] red;
	int[] blue;
	int[] green;
	MyPanel redPanel;
	MyPanel bluePanel;
	MyPanel greenPanel;
	// Instance Fields you will be using below

	// This will be your height and width of your 2d array
	int height = 0, width = 0;

	// your 2D array of pixels
	int picture[][];

	/*
	 * In the Constructor I set up the GUI, the frame the menus. The open pulldown
	 * menu is how you will open an image to manipulate.
	 */
	IMP() {
		toolkit = Toolkit.getDefaultToolkit();
		frame = new JFrame("Image Processing Software by Hunter");
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu functions = getFunctions();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				quit();
			}
		});
		openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				handleOpen();
			}
		});
		resetItem = new JMenuItem("Reset");
		resetItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				reset();
			}
		});
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				quit();
			}
		});
		file.add(openItem);
		file.add(resetItem);
		file.add(exitItem);
		bar.add(file);
		bar.add(functions);
		frame.setSize(600, 600);
		mp = new JPanel();
		mp.setBackground(new Color(0, 0, 0));
		scroll = new JScrollPane(mp);
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		JPanel butPanel = new JPanel();
		butPanel.setBackground(Color.black);
		start = new JButton("start");
		start.setEnabled(false);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				redPanel.drawHistogram();
				greenPanel.drawHistogram();
				bluePanel.drawHistogram();
			}
		});
		butPanel.add(start);
		frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
		frame.setJMenuBar(bar);
		frame.setVisible(true);
	}

	/*
	 * This method creates the pulldown menu and sets up listeners to selection of
	 * the menu choices. If the listeners are activated they call the methods for
	 * handling the choice, fun1, fun2, fun3, fun4, etc. etc.
	 */

	private JMenu getFunctions() {
		JMenu fun = new JMenu("Functions");

		JMenuItem firstItem = new JMenuItem("MyExample - fun1 method");
		JMenuItem secondItem = new JMenuItem("GrayScale - Luminosity");
		JMenuItem thirdItem = new JMenuItem("GrayScale - Blur");
		JMenuItem fourthItem = new JMenuItem("Rotate 90 degs cw");
		JMenuItem fifthItem = new JMenuItem("Edge Detection - 5x5");
		JMenuItem sixthItem = new JMenuItem("Histogram - Color");
		JMenuItem seventhItem = new JMenuItem("Equalize - Histogram");
		JMenuItem eighthItem = new JMenuItem("Track Colored Object");
		JMenuItem ninthItem = new JMenuItem("Export Image");
		
		JMenuItem firstQuizItem = new JMenuItem("Quiz one");

		firstItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun1();
			}
		});

		secondItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun2();
			}
		});

		thirdItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun3();
			}
		});

		fourthItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun4();
			}
		});

		fifthItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun5();
			}
		});

		sixthItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun6();
			}
		});

		seventhItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun7();
			}
		});

		eighthItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun8();
			}
		});
		
		ninthItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fun9();
			}
		});

		fun.add(firstItem);
		fun.add(secondItem);
		fun.add(thirdItem);
		fun.add(fourthItem);
		fun.add(fifthItem);
		fun.add(sixthItem);
		fun.add(seventhItem);
		fun.add(eighthItem);
		fun.add(ninthItem);
		
		
		firstQuizItem.addActionListener((ActionEvent evt) -> {
			firstQuiz();
		});
		
		JMenuItem secondQuizItem = new JMenuItem("Quiz two");
		secondQuizItem.addActionListener((ActionEvent evt) -> {
			secondQuiz();
		});
		
		fun.add(firstQuizItem);
		fun.add(secondQuizItem);

		return fun;

	}

	/*
	 * This method handles opening an image file, breaking down the picture to a
	 * one-dimensional array and then drawing the image on the frame. You don't need
	 * to worry about this method.
	 */
	private void handleOpen() {

		img = new ImageIcon();
		JFileChooser chooser = new JFileChooser();
		Preferences pref = Preferences.userNodeForPackage(IMP.class);
		String path = pref.get("DEFAULT_PATH", "");

		chooser.setCurrentDirectory(new File(path));
		int option = chooser.showOpenDialog(frame);

		if (option == JFileChooser.APPROVE_OPTION) {
			pic = chooser.getSelectedFile();
			pref.put("DEFAULT_PATH", pic.getAbsolutePath());
			img = new ImageIcon(pic.getPath());
		}
		width = img.getIconWidth();
		height = img.getIconHeight();

		JLabel label = new JLabel(img);
		label.addMouseListener(this);
		pixels = new int[width * height];

		results = new int[width * height];

		Image image = img.getImage();

		PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("Interrupted waiting for pixels");
			return;
		}
		for (int i = 0; i < width * height; i++)
			results[i] = pixels[i];
		turnTwoDimensional();
		mp.removeAll();
		mp.add(label);

		mp.revalidate();
	}

	/*
	 * The libraries in Java give a one dimensional array of RGB values for an
	 * image, I thought a 2-Dimensional array would be more usefull to you So this
	 * method changes the one dimensional array to a two-dimensional.
	 */
	private void turnTwoDimensional() {
		picture = new int[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				picture[i][j] = pixels[i * width + j];

	}

	/*
	 * This method takes the picture back to the original picture
	 */
	private void reset() {
		img = new ImageIcon(pic.getPath());
		width = img.getIconWidth();
		height = img.getIconHeight();

		JLabel label = new JLabel(img);
		label.addMouseListener(this);
		pixels = new int[width * height];

		results = new int[width * height];

		Image image = img.getImage();

		PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("Interrupted waiting for pixels");
			return;
		}
		for (int i = 0; i < width * height; i++)
			results[i] = pixels[i];
		turnTwoDimensional();
		mp.removeAll();
		mp.add(label);

		mp.revalidate();
	}

	/*
	 * This method is called to redraw the screen with the new image.
	 */
	private void resetPicture() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				pixels[i * width + j] = picture[i][j];
		Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));

		JLabel label2 = new JLabel(new ImageIcon(img2));
		mp.removeAll();
		mp.add(label2);

		mp.revalidate();

	}

	/*
	 * This method takes a single integer value and breaks it down doing bit
	 * manipulation to 4 individual int values for A, R, G, and B values
	 */
	private int[] getPixelArray(int pixel) {
		int temp[] = new int[4];
		temp[0] = (pixel >> 24) & 0xff;
		temp[1] = (pixel >> 16) & 0xff;
		temp[2] = (pixel >> 8) & 0xff;
		temp[3] = (pixel) & 0xff;
		return temp;

	}

	/*
	 * This method takes an array of size 4 and combines the first 8 bits of each to
	 * create one integer.
	 */
	private int getPixels(int rgb[]) {
		int alpha = 0;
		int rgba = (rgb[0] << 24) | (rgb[1] << 16) | (rgb[2] << 8) | rgb[3];
		return rgba;
	}

	public void getValue() {
		int pix = picture[colorY][colorX];
		int temp[] = getPixelArray(pix);
		System.out.println("Color value " + temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3]);
	}
	
	public int[] getValueReturn() {
		int pix = picture[colorY][colorX];
		return getPixelArray(pix);
	}

	/**************************************************************************************************
	 * This is where you will put your methods. Every method below is called when
	 * the corresponding pulldown menu is used. As long as you have a picture open
	 * first the when your fun1, fun2, fun....etc method is called you will have a
	 * 2D array called picture that is holding each pixel from your picture.
	 *************************************************************************************************/
	/*
	 * Example function that just removes all red values from the picture. Each
	 * pixel value in picture[i][j] holds an integer value. You need to send that
	 * pixel to getPixelArray the method which will return a 4 element array that
	 * holds A,R,G,B values. Ignore [0], that's the Alpha channel which is
	 * transparency, we won't be using that, but you can on your own. getPixelArray
	 * will breaks down your single int to 4 ints so you can manipulate the values
	 * for each level of R, G, B. After you make changes and do your calculations to
	 * your pixel values the getPixels method will put the 4 values in your ARGB
	 * array back into a single integer value so you can give it back to the program
	 * and display the new picture.
	 */
	private void fun1() {

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				int rgbArray[] = new int[4];

				// get three ints for R, G and B
				rgbArray = getPixelArray(picture[i][j]);

				rgbArray[1] = 0;
				// take three ints for R, G, B and put them back into a single int
				picture[i][j] = getPixels(rgbArray);
			}
		resetPicture();
	}

	// grayscale conversion
	private void fun2() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {

				int rgbArray[] = new int[4];
				// get three ints for R, G and B
				rgbArray = getPixelArray(picture[i][j]);

				// converts the image to grayscale using the equation for luminosity
				int gray = (int) (rgbArray[1] * 0.21 + rgbArray[2] * 0.72 + rgbArray[3] * 0.07);
				rgbArray[1] = (int) (gray); // R
				rgbArray[2] = (int) (gray); // G
				rgbArray[3] = (int) (gray); // B

				// take three ints for R, G, B and put them back into a single int
				picture[i][j] = getPixels(rgbArray);
			}
		resetPicture();
	}

	// blur grayscale with 3x3 mask
	private void fun3() {
		fun2();
		int newPicture[][] = new int[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {

				int rgbArray[] = new int[4];
				rgbArray = getPixelArray(picture[i][j]);

				int newRGBArray[] = new int[4];
				for (int k = 0; k < rgbArray.length; k++) {
					newRGBArray[k] = rgbArray[k];
				}

				int sum = 0;
				for (int l = -1; l < 2; l++) { // analogous to height
					for (int m = -1; m < 2; m++) { // analogous to width
						if ((i + l < 0) || (i + l > (height - 1)) || (j + m < 0) || (j + m > (width - 1))) {
							continue;
						} else if (m == 0 && l == 0) {
							continue;
						} else {
							sum += getPixelArray(picture[i + l][j + m])[1];
						}
					}
				}

				sum = (int) (((newRGBArray[1]) + sum) / 9);
				newRGBArray[1] = sum;
				newRGBArray[2] = sum;
				newRGBArray[3] = sum;

				newPicture[i][j] = getPixels(newRGBArray);
			}
		picture = newPicture;
		resetPicture();
	}

	// Rotate image 90degs cw
	private void fun4() {
		int newPicture[][] = new int[width][height];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				int rgbArray[] = new int[4];
				rgbArray = getPixelArray(picture[i][j]);
				newPicture[j][height - i - 1] = getPixels(rgbArray);
			}
		int temp = height;
		height = width;
		width = temp;
		picture = newPicture;

		resetPicture();
	}

	// Color image -> grayscale for 5x5 mask edge detection
	private void fun5() {
		fun2();
		int newPicture[][] = new int[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {

				int rgbArray[] = new int[4];
				rgbArray = getPixelArray(picture[i][j]);

				int newRGBArray[] = new int[4];
				for (int k = 0; k < rgbArray.length; k++) {
					newRGBArray[k] = rgbArray[k];
				}

				int sum = 0;
				for (int l = -2; l < 3; l++) { // analogous to height
					for (int m = -2; m < 3; m++) { // analogous to width
						if ((i + l < 0) || (i + l > (height - 1)) || (j + m < 0) || (j + m > (width - 1))) {
							continue;
						} else if (m == 0 && l == 0) {
							continue;
						} else if ((m >= -1 && m <= 1) && (l >= -1 && l <= 1)) {
							continue;
						} else {
							sum += getPixelArray(picture[i + l][j + m])[1];
						}
					}
				}

				sum = (int) (((16 * newRGBArray[1]) - sum) / 16);
				newRGBArray[1] = sum;
				newRGBArray[2] = sum;
				newRGBArray[3] = sum;

				newPicture[i][j] = getPixels(newRGBArray);
			}
		picture = newPicture;
		resetPicture();
	}

	// Histogram - colors in seprate windows
	private void fun6() {
		red = new int[256];
		green = new int[256];
		blue = new int[256];

		int freqMax = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				int rgbArray[] = new int[4];
				// get three ints for R, G and B
				rgbArray = getPixelArray(picture[i][j]);

				red[rgbArray[1]]++;
				green[rgbArray[2]]++;
				blue[rgbArray[3]]++;

				if (red[rgbArray[1]] > freqMax) {
					freqMax = red[rgbArray[1]];
				}
				if (green[rgbArray[2]] > freqMax) {
					freqMax = green[rgbArray[2]];
				}
				if (blue[rgbArray[3]] > freqMax) {
					freqMax = blue[rgbArray[3]];
				}
				
				
				
				picture[i][j] = getPixels(rgbArray);
			}
		}
		int frameMax = 570;
		if (frameMax < freqMax) {
			red = rescale(red, freqMax, frameMax);
			green = rescale(green, freqMax, frameMax);
			blue = rescale(blue, freqMax, frameMax);
		}
		resetPicture();
		histogram();
	}

	// histogram equalizer
	private void fun7() {
		red = new int[256];
		green = new int[256];
		blue = new int[256];

		int freqMax = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				int rgbArray[] = new int[4];
				// get three ints for R, G and B
				rgbArray = getPixelArray(picture[i][j]);

				red[rgbArray[1]]++;
				green[rgbArray[2]]++;
				blue[rgbArray[3]]++;

				if (red[rgbArray[1]] > freqMax) {
					freqMax = red[rgbArray[1]];
				}
				if (green[rgbArray[2]] > freqMax) {
					freqMax = green[rgbArray[2]];
				}
				if (blue[rgbArray[3]] > freqMax) {
					freqMax = blue[rgbArray[3]];
				}
				
				picture[i][j] = getPixels(rgbArray);
			}
		}
		int frameMax = 570;
		int gridSize = width * height;
		int pixelSum = 0;
		
		red = cp(red, gridSize);
		green = cp(green, gridSize);
		blue = cp(blue, gridSize);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				int rgbArray[] = new int[4];
				// get three ints for R, G and B
				rgbArray = getPixelArray(picture[i][j]);
				rgbArray[1] = red[rgbArray[1]];
				rgbArray[2] = green[rgbArray[2]];
				rgbArray[3] = blue[rgbArray[3]];
				picture[i][j] = getPixels(rgbArray);
			}
		}
		
		resetPicture(); 
		histogram();		
		
	}

	// Track colored object
	private void fun8() {
		int[] redRange = new int[2];
		int[] blueRange = new int[2];
		int[] greenRange = new int[2];
		if(locRGB == null) {
			System.out.println("Choose a pixel then call function\nBreak");
			return;
		}else {
			System.out.println("Pixel Location: ");
			System.out.println("(" + colorX + "," + colorY + ")");
			System.out.println("Color value " + locRGB[1] + " " + locRGB[2] + " " + locRGB[3]);
			redRange[0] = locRGB[1] - 20;
			redRange[1] = locRGB[1] + 20;
			greenRange[0] = locRGB[2] - 20;
			greenRange[1] = locRGB[2] + 20;
			blueRange[0] = locRGB[3] - 20;
			blueRange[1] = locRGB[3] + 20;
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					int rgbArray[] = new int[4];
					// get three ints for R, G and B
					rgbArray = getPixelArray(picture[i][j]);
					if ((rgbArray[1] > redRange[0] && rgbArray[1] < redRange[1]) && (rgbArray[2] > greenRange[0] && rgbArray[2] < greenRange[1]) && (rgbArray[3] > blueRange[0] && rgbArray[3] < blueRange[1])) {
						rgbArray[1] = 255;
						rgbArray[2] = 255;
						rgbArray[3] = 255;
					}else {
						rgbArray[1] = 0;
						rgbArray[2] = 0;
						rgbArray[3] = 0;
					}
					//if r g and b values are within the threshold make 255
					//else make 0
					picture[i][j] = getPixels(rgbArray);
				}
			}
			
			resetPicture(); 
		}
		
	}
	
	//export
	private void fun9() {
		int rgbArray[] = new int[4]; 
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				rgbArray = getPixelArray(picture[i][j]);
			}
		}
		BufferedImage img = getImageFromArray(rgbArray, width, height);
		File imageFile = new File("exportedImage.jpg");
		try {
			ImageIO.write(img, "jpg", imageFile);
			System.out.println(imageFile.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(imageFile.exists()) {
//			
//		}else {
//			System.out.println("File does not exist");
//		}
		
		
	}
	
	private void firstQuiz() {
		
		int rgbArray[] = new int[4]; //changed
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) { //if change j to incriment by 4; if statement with mod is unnecessary

				rgbArray = getPixelArray(picture[i][j]);

				if (j % 4 == 0) {
					rgbArray[1] = 0;
					rgbArray[2] = 0;
					rgbArray[3] = 0;
				}

				picture[i][j] = getPixels(rgbArray);
			}
		}

		resetPicture();
	}
	
	private static BufferedImage getImageFromArray(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //WritableRaster raster = (WritableRaster) image.getData();
        //raster.setPixels(0,0,width,height,pixels);
        return image;
    }
	
	private void secondQuiz() {
		fun3(); //grayscale method
		int rgbArray[] = new int[4]; //changed 
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				
				// get three ints for R, G and B
				rgbArray = getPixelArray(picture[i][j]);
				
				if(rgbArray[1] < 200) {
					rgbArray[1] = 255;
					rgbArray[2] = 255;
					rgbArray[3] = 255;
				}else {
					rgbArray[1] = 0;
					rgbArray[2] = 0;
					rgbArray[3] = 0;
				}

				
				picture[i][j] = getPixels(rgbArray);
			}
		}

		resetPicture();
	}
	
	//Helper functions
	
	private int[] rescale(int[] array, int arrayMax, int max) {
		double scaleFactor = (double) max / arrayMax;
		// double scaleFactor = 1.0;
		// System.out.println("ScaleFactor: " + scaleFactor + "Max Height: " + max + ";
		// ArrayMax: " + arrayMax);
		for (int i = 0; i < array.length; i++) {
			array[i] = (int) (array[i] * scaleFactor);
		}
		return array;
	}
	
	private int[] cp(int[] array, int gridSize) {
		int pixelSum = 0;
		for (int i = 0; i < array.length; i++) {
			int og = array[i];
			pixelSum += array[i];
			float prob = (float) array[i]/gridSize;
			float cp = (float) (prob * pixelSum) / array[i];
			float cpMap = cp * 255;
			array[i] = (int) cpMap;
			//System.out.println("og red[i]: " + og + "; prob: " + prob + "; cp: " + cp + "; cpMap: " + cpMap + "; pixelSum: " + pixelSum + "; new red[i]: " + array[i]);
		}
		return array;
	}

	private void histogram() {
		JFrame redFrame = new JFrame("Red");
		redFrame.setSize(305, 630);
		redFrame.setLocation(800, 0);
		JFrame greenFrame = new JFrame("Green");
		greenFrame.setSize(305, 630);
		greenFrame.setLocation(1150, 0);
		JFrame blueFrame = new JFrame("blue");
		blueFrame.setSize(305, 630);
		blueFrame.setLocation(1450, 0);
		redPanel = new MyPanel(red);
		greenPanel = new MyPanel(green);
		bluePanel = new MyPanel(blue);
		redFrame.getContentPane().add(redPanel, BorderLayout.CENTER);
		redFrame.setVisible(true);
		greenFrame.getContentPane().add(greenPanel, BorderLayout.CENTER);
		greenFrame.setVisible(true);
		blueFrame.getContentPane().add(bluePanel, BorderLayout.CENTER);
		blueFrame.setVisible(true);
		start.setEnabled(true);
	}

	private void quit() {
		System.exit(0);
	}

	@Override
	public void mouseEntered(MouseEvent m) {
	}

	@Override
	public void mouseExited(MouseEvent m) {
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		colorX = m.getX();
		colorY = m.getY();
		System.out.println(colorX + "  " + colorY);
		loc = new int[2];
		loc[0] = colorX;
		loc[1] = colorY;
		locRGB = getValueReturn();
		start.setEnabled(true);
	}

	@Override
	public void mousePressed(MouseEvent m) {
	}

	@Override
	public void mouseReleased(MouseEvent m) {
	}

	public static void main(String[] args) {
		IMP imp = new IMP();
	}

}