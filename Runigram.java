import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		int [] int_val = in.readAllInts();
		int index_r = 0;
		int index_g = 1;
		int index_b = 2;
		for(int i = 0; i < numRows ; i++){
			for(int j = 0; j < numCols; j++){
				int red = int_val[index_r];
				index_r += 3;
				int green = int_val[index_g];
				index_g += 3;
				int blue = int_val[index_b];
				index_b += 3;
				image[i][j] = new Color(red, green, blue);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		for(int i = 0; i < image.length; i++){
			for(int j = 0; j < image[i].length; j++){
				print(image[i][j]);
			}
		}
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		//// Replace the following statement with your code
		Color[][] flipped = new Color[image.length][image[0].length];
		for(int i = 0; i < flipped.length; i++){
			int index = flipped[i].length - 1;
			for(int j = 0; j < flipped[i].length; j++){
				flipped[i][j] = image[i][index];
				index --; 
			}
		}
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		//// Replace the following statement with your code
		Color[][] flipped = new Color[image.length][image[0].length];
		for(int j = 0; j < flipped[0].length; j++){
			int index = flipped[0].length - 1;
			for(int i = 0; i < flipped.length; i++){
				flipped[i][j] = image[index][j];
				index --; 
			}
		}
		return flipped;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		//// Replace the following statement with your code
		int lum_color = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		Color lum  = new Color(lum_color, lum_color ,lum_color);
		return lum;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		//// Replace the following statement with your code
		Color [][] gray_vis = new Color [image.length][image[0].length];
		for(int i = 0; i < gray_vis.length; i++){
			for(int j = 0; j < gray_vis[i].length; j++){
				gray_vis[i][j] = luminance(image[i][j]);
			}
		}
		return gray_vis;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		//// Replace the following statement with your code
		int rows = image.length;
		int columns = image[0].length;
		Color [][] scaled_img = new Color [height][width];
		for(int i = 0; i < scaled_img.length; i++){
			for(int j = 0; j < scaled_img[i].length; j++){
				int i_img = (int) (i * ((double) rows / height));
            	int j_img = (int) (j * ((double) columns / width));
            	if (i_img >= rows){
					i_img = rows - 1;
				} 
            	if (j_img >= columns) {
					j_img = columns - 1;
				}
           		scaled_img[i][j] = image[i_img][j_img];
			}
		}	
		return scaled_img;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		//// Replace the following statement with your code
		double weight_c2 = 1 - alpha;
		int red = Math.min(255, Math.max(0, (int) (c1.getRed() * alpha + c2.getRed() * weight_c2)));
		int green = Math.min(255, Math.max(0,(int) (c1.getGreen() * alpha + c2.getGreen() * weight_c2)));
		int blue = Math.min(255, Math.max(0,(int) (c1.getBlue() * alpha + c2.getBlue() * weight_c2)));
		return new Color(red, green, blue);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		//// Replace the following statement with your code
		Color [][] blended = new Color[image1.length][image1[0].length];
		for(int i = 0; i < blended.length; i++){
			for(int j = 0 ; j < blended[0].length; j++){
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		//// Replace this comment with your code
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

