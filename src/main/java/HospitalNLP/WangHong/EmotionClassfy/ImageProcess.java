package EmotionClassfy;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
  * @param args
  * 将图像灰度化并二值化
  */
public class ImageProcess {

	public static void main(String[] args) throws Exception {
		BufferedImage bufferedImage = ImageIO.read(new File("F:/图片/boy.jpg"));
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();
		
		//灰度化
		int[][] gray = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int RGB = bufferedImage.getRGB(x, y);
				int R = (RGB >> 16) & 0xFF;
				int G = (RGB >> 8) & 0xFF;
				int B = (RGB >> 0) & 0xFF;
				int grayPixel = (int) ((B * 29 + G * 150 + R * 77 + 128) >> 8);
				gray[x][y] = grayPixel;
			}
		}
		
		//二值化
		int threshold = ostu(gray, w, h);
		BufferedImage binaryBufferedImage = new BufferedImage(w, h,BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (gray[x][y] > threshold) {
					gray[x][y] |= 0x00FFFF;
				} else {
					gray[x][y] &= 0xFF0000;
				}
				binaryBufferedImage.setRGB(x, y, gray[x][y]);
			}
		}
		System.out.println("w"+w+"h"+h);
		for(int x = 0;x<w;x++)
		{
			for(int y = 0;y<h;y++)
			{
				System.out.println("像素"+gray[x][y]);
			}
		}
		ImageIO.write(binaryBufferedImage, "jpg", new File("F:/图片/boy1.jpg"));
		//打印矩阵
//		for (int y = 0; y < h; y++) {
//			for (int x = 0; x < w; x++) {
//				if (isBlack(binaryBufferedImage.getRGB(x, y))) {
//					System.out.print("*");
//				} else {
//					System.out.print(" ");
//				}
//			}
//			System.out.println();
//		}
	}
	
	public static int ostu(int[][] gray, int w, int h) {
		int[] histData = new int[w * h];
		// Calculate histogram
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
            int red = 0xFF & gray[x][y];
            histData[red]++;
			}
		}
		// Total number of pixels
        int total = w * h;
        float sum = 0;
        for (int t = 0; t < 256; t++)
        sum += t * histData[t];
        float sumB = 0;
        int wB = 0;
        int wF = 0;
        float varMax = 0;
        int threshold = 0;
        for (int t = 0; t < 256; t++) {
	        wB += histData[t]; // Weight Background
	        if (wB == 0)
	        	continue;
	        wF = total - wB; // Weight Foreground
	        if (wF == 0)
	            break;
	        sumB += (float) (t * histData[t]);
	        float mB = sumB / wB; // Mean Background
	        float mF = (sum - sumB) / wF; // Mean Foreground
	        // Calculate Between Class Variance
	        float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
	        // Check if new maximum found
	        if (varBetween > varMax) {
	        	varMax = varBetween;
	        	threshold = t;
	        }
        }
        return threshold;
	}
	
//	public static boolean isBlack(int colorInt) {
//	Color color = new Color(colorInt);
//	if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
//		return true;
//	}
//	return false;
//}

//public static boolean isWhite(int colorInt) {
//Color color = new Color(colorInt);
//	if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
//		return true;
//	}
//	return false;
//}

//public static int isBlackOrWhite(int colorInt) {
//   if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
//      return 1;
//   	}
//   	return 0;
//}
//public static int getColorBright(int colorInt) {
//  Color color = new Color(colorInt);
//   return color.getRed() + color.getGreen() + color.getBlue();
//}
}
