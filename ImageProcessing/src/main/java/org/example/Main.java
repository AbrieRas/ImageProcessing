package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        // The provided images are apple.jpg, flower.jpg, and kitten.jpg
        int[][] imageData = imgToTwoD("./apple.jpg");
        // Or load your own image using a URL!
        //int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
        // viewImageData(imageData);

        // Below commented out for step 15
        int[][] trimmed = trimBorders(imageData, 60);
        twoDToImage(trimmed, "./trimmed_apple.jpg");

        // Create a negative version of the image
        int[][] negativePixelImage = negativeColor(imageData);
        twoDToImage(negativePixelImage, "./negative_pixel_apple.jpg");

        // Create horizontally stretched image
        int[][] horizontallyStretchedImage = stretchHorizontally(imageData);
        twoDToImage(horizontallyStretchedImage, "./horizontally_stretched_apple.jpg");

        // Create shrinked image
        int[][] shrinkedImage = shrinkVertically(imageData);
        twoDToImage(shrinkedImage, "./shrinked_apple.jpg");

        // Create inverted image
        int[][] invertedImage = invertImage(imageData);
        twoDToImage(invertedImage, "./inverted_apple.jpg");

        // Filter color of image
        int[][] filteredImage = colorFilter(imageData, -75, 30, -30);
        twoDToImage(filteredImage, "./filtered_apple.jpg");

        // Paint random image - commented out, as this edits original
        // int[][] randomPaintedImage = paintRandomImage(imageData);
        // twoDToImage(randomPaintedImage, "./random_painted_image.jpg");

        // Draw rectangle on image - commented out, as this edits original
        // int[] rectangleRGBA = {0, 200, 0, 255};
        // int rectangleColor = getColorIntValFromRGBA(rectangleRGBA);
        // // Get painted rectangle 2D array
        // int[][] rectangleOnImage = paintRectangle(imageData, 100, 100, 100, 100, rectangleColor);
        // // Create painted rectangle picture
        // twoDToImage(rectangleOnImage, "./rectanle_on_apple.jpg");

        // Create abstract Geometric Art
        int[][] abstractGeometricArt = generateRectangles(imageData, 1000);
        twoDToImage(abstractGeometricArt, "./abstract_geometric_art.jpg");

        // int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
        // Painting with pixels
    }
    // Image Processing Methods
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
        // Example Method
        if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
            int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
            for (int i = 0; i < trimmedImg.length; i++) {
                for (int j = 0; j < trimmedImg[i].length; j++) {
                    trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
                }
            }
            return trimmedImg;
        } else {
            System.out.println("Cannot trim that many pixels from the given image.");
            return imageTwoD;
        }
    }
    public static int[][] negativeColor(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        // Declare new 2D array with same size as parameter 2D array
        int[][] negativeColorImg = new int[imageTwoD.length][imageTwoD[0].length];

        // Loop through each pixel of parameter 2D array
        for (int row = 0; row < imageTwoD.length; row++) {
            for (int col = 0; col < imageTwoD[0].length; col++) {
                // Get R, G, B and A values from each pixel
                int[] rgba = getRGBAFromPixel(imageTwoD[row][col]);

                // Create negative values for first three elements
                for (int i = 0; i < rgba.length - 1; i++) {
                    rgba[i] = 255 - rgba[i];
                }

                // Get hexadecimal pixel data
                negativeColorImg[row][col] = getColorIntValFromRGBA(rgba);
            }
        }

        return negativeColorImg;
    }
    public static int[][] stretchHorizontally(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] horizontallyStretchedImage = new int[imageTwoD.length][imageTwoD[0].length * 2];

        for(int row = 0; row < imageTwoD.length; row++) {
            for (int col = 0; col < imageTwoD[0].length; col++) {
                // Keep track of position
                int doubleColIndex = col * 2;

                // Copy current pixel
                horizontallyStretchedImage[row][doubleColIndex] = imageTwoD[row][col];
                horizontallyStretchedImage[row][doubleColIndex + 1] = imageTwoD[row][col];
            }
        }

        return horizontallyStretchedImage;
    }
    public static int[][] shrinkVertically(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        // Declare new shrinked 2D array
        int[][] shrinkedImage = new int[imageTwoD.length / 2][imageTwoD[0].length];

        // Iterate over input 2D array using col major
        for (int col = 0; col < imageTwoD[0].length; col++) {
            // End early to avoid out of bounds error
            for (int row = 0; row < imageTwoD.length-1; row += 2) {
                shrinkedImage[row / 2][col] = imageTwoD[row][col];
            }
        }

        // Return shrinked image
        return shrinkedImage;
    }
    public static int[][] invertImage(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        // Create new inverted image
        int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];

        // Iterate through pixels
        for (int row = 0; row < imageTwoD.length; row++) {
            for (int col = 0; col < imageTwoD[0].length; col++) {
                invertedImage[row][col] = imageTwoD[(imageTwoD.length-1)-row][(imageTwoD[0].length-1)-col];
            }
        }

        return invertedImage;
    }
    public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
        // Declare new 2D array
        int[][] filteredImage = new int[imageTwoD.length][imageTwoD[0].length];

        // Iterate through pixels
        for (int row = 0; row < imageTwoD.length; row++) {
            for (int col = 0; col < imageTwoD[0].length; col++) {
                // Get rgba
                int[] rgba = getRGBAFromPixel(imageTwoD[row][col]);

                // Add modifier parameter values
                rgba[0] += redChangeValue;
                rgba[1] += greenChangeValue;
                rgba[2] += blueChangeValue;

                // Ensure rgb is not out of range
                for (int i = 0; i < rgba.length-1; i++) {
                    if (rgba[i] < 0) {
                        rgba[i] = 0;
                    } else if (rgba[i] > 255) {
                        rgba[i] = 255;
                    }
                }

                // Get color value
                filteredImage[row][col] = getColorIntValFromRGBA(rgba);
            }
        }

        return filteredImage;
    }
    // Painting Methods
    public static int[][] paintRandomImage(int[][] canvas) {
        // TODO: Fill in the code for this method
        // New random object
        Random rand = new Random();

        // Iterate through pixels
        for (int row = 0; row < canvas.length; row++) {
            for (int col = 0; col < canvas[0].length; col++) {
                // Generate random R, G, B
                int[] rgba = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};

                // Convert to pixel value and save to input image
                canvas[row][col] = getColorIntValFromRGBA(rgba);
            }
        }

        return canvas;
    }
    public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
        // TODO: Fill in the code for this method
        // Iterate through pixels
        for (int row = 0; row < canvas.length; row++) {
            for (int col = 0; col < canvas[0].length; col++) {
                // Ensure correct pixels are replaced
                if (row >= rowPosition && row <= (rowPosition + width) && col >= colPosition && col <= (colPosition + height)) {
                    // Replace pixels
                    canvas[row][col] = color;
                }
            }
        }
        // Return new canvas
        return canvas;
    }
    public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
        // TODO: Fill in the code for this method
        // New random object
        Random rand = new Random();

        // Iterate through number of rectangles
        for (int rectangleIndex = 0; rectangleIndex < numRectangles; rectangleIndex++) {
            // Random number based of columns
            int width = rand.nextInt(canvas[0].length);
            // Random number based of rows
            int height = rand.nextInt(canvas.length);

            // Random number based of columns
            int colPosition = rand.nextInt(canvas[0].length - width);
            // Random number based of rows
            int rowPosition = rand.nextInt(canvas.length - height);

            // Create random color
            int[] rectangleRGBA = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};
            int rectangleColor = getColorIntValFromRGBA(rectangleRGBA);

            // Paint rectangle
            paintRectangle(canvas, width, height, rowPosition, colPosition, rectangleColor);
        }
        // Return modified canvas
        return canvas;
    }
    // Utility Methods
    public static int[][] imgToTwoD(String inputFileOrLink) {
        try {
            BufferedImage image = null;
            if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
                URL imageUrl = new URL(inputFileOrLink);
                image = ImageIO.read(imageUrl);
                if (image == null) {
                    System.out.println("Failed to get image from provided URL.");
                }
            } else {
                image = ImageIO.read(new File(inputFileOrLink));
            }
            int imgRows = image.getHeight();
            int imgCols = image.getWidth();
            int[][] pixelData = new int[imgRows][imgCols];
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    pixelData[i][j] = image.getRGB(j, i);
                }
            }
            return pixelData;
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getLocalizedMessage());
            return null;
        }
    }
    public static void twoDToImage(int[][] imgData, String fileName) {
        try {
            int imgRows = imgData.length;
            int imgCols = imgData[0].length;
            BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    result.setRGB(j, i, imgData[i][j]);
                }
            }
            File output = new File(fileName);
            ImageIO.write(result, "jpg", output);
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getLocalizedMessage());
        }
    }
    public static int[] getRGBAFromPixel(int pixelColorValue) {
        Color pixelColor = new Color(pixelColorValue);
        return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
    }
    public static int getColorIntValFromRGBA(int[] colorData) {
        if (colorData.length == 4) {
            Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
            return color.getRGB();
        } else {
            System.out.println("Incorrect number of elements in RGBA array.");
            return -1;
        }
    }
    public static void viewImageData(int[][] imageTwoD) {
        if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
            int[][] rawPixels = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rawPixels[i][j] = imageTwoD[i][j];
                }
            }
            System.out.println("Raw pixel data from the top left corner.");
            System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
            int[][][] rgbPixels = new int[3][3][4];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
                }
            }
            System.out.println();
            System.out.println("Extracted RGBA pixel data from top the left corner.");
            for (int[][] row : rgbPixels) {
                System.out.print(Arrays.deepToString(row) + System.lineSeparator());
            }
        } else {
            System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
        }
    }
}
