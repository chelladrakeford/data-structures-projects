// “I will practice academic and personal integrity and excellence of character and expect the same from others"
// Chella Drakeford, cdrakeford@mocs.flsouthern.edu, 1314292, CSC3280 002, Program 3: FSC Recurse, 10/09/26
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // create scanner object to read input
        Scanner in = new Scanner(System.in);
        // read num of commands
        int numCommands = in.nextInt();

        // loop through each command and process one image at a time
        for (int i = 0; i < numCommands; i++) {
            String command = in.next();
            int size = in.nextInt();
            int threshold = in.nextInt();

            // create 2D array of pixels to store image
            Pixel[][] image = new Pixel[size][size];
            // read each row of image
            for (int row = 0; row < size; row++) {
                String pixelRow = in.next();
                // in each row read pixel in current row
                for (int col = 0; col < size; col++) {
                    // get pixel character and convert to integer
                    int pixelValue = pixelRow.charAt(col) - '0';
                    // create new Pixel object and assign to 2D array
                    image[row][col] = new Pixel(pixelValue);
                }
            }
            // compress entire image, starting from top left to bottom right
            compress(image, 0, size - 1, 0, size - 1, threshold);
            System.out.println("MocsCompress:");
            // print each row of compressed image
            for (int row = 0; row < size; row++) {
                // print color of each pixel in current row
                for (int col = 0; col < size; col++) {
                    System.out.print(image[row][col].getColor());
                }
                System.out.println();
            }
            System.out.println();
        }        
        in.close();
    }
    
    // recursively simplifies a region of the image
    // the start and end indices define the boundaries of the current region
    // compression method that takes in a 2D array of pixels, the size of the image, and a threshold value
    public static void compress(Pixel[][] image, int startRow, int endRow, int startCol, int endCol, int threshold) {
        // count black and white pixels in the current region
        int countWhite = 0;
        int countBlack = 0;

        // visit each pixel in the current region and count the number of black and white pixels
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                // 0 indicates white pixel
                if (image[row][col].getColor() == 0) {
                    countWhite++;
                // else 1 indicates black pixel
                } else {
                    countBlack++;
                }
            }
        }
        // calculate total number of pixels region
        int total = (endRow - startRow + 1) * (endCol - startCol + 1);
        // calculate percentage of each color
        double blackPercent = countBlack * 100.0 / total;
        double whitePercent = countWhite * 100.0 / total;

        // if black reaches threshold, fill with black
        if (blackPercent >= threshold) {
            // visit every pixel in region ad change to 1
            for (int row = startRow; row <= endRow; row++) {
                for (int col = startCol; col <= endCol; col++) {
                    image[row][col].setColor(1);
                }
            }
        }
        // otherwise, if white reaches threshold, fill with white
        else if (whitePercent >= threshold) {
            // visit every pixel in region and change to 0
            for (int row = startRow; row <= endRow; row++) {
                for (int col = startCol; col <= endCol; col++) {
                    image[row][col].setColor(0);
                }
            }
        }
        // if neither color reaches threshold, divide into quadrants
        else {
            // find middle row and column to spilt to region
            int midRow = (startRow + endRow) / 2;
            int midCol = (startCol + endCol) / 2;

            // top right quadrant
            compress(image, startRow, midRow, midCol + 1, endCol, threshold);
            // top left quadrant
            compress(image, startRow, midRow, startCol, midCol, threshold);
            // bottom left quadrant
            compress(image, midRow + 1, endRow, startCol, midCol, threshold);
            // bottom right quadrant
            compress(image, midRow + 1, endRow, midCol + 1, endCol, threshold);
        }
    }
}

// pixel class with integer field (0 or 1) to store the color
class Pixel {
    // data members
    private int color; // stores pixel color value

    // pixel constructor
    // initializes the pixel with its starting color
    Pixel(int color) {
        this.color = color;
    }
    // getter to return current color
    public int getColor() {
        return color;
    }
    // setter updates pixel's color
    public void setColor(int color) {
        this.color = color;
    }
}