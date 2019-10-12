import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class Main {

    static int width = 25, height = 20;

    public static void main(String[] args) {
        try {
            String original_name = args[0];
            BufferedImage original_img = ImageIO.read(new File(original_name));
            BufferedImage destination_img = new BufferedImage(original_img.getWidth(), original_img.getHeight(), BufferedImage.TYPE_INT_RGB);
            ArrayList<ImageWithPosition> bufferedImages = new ArrayList<>();
            int w = original_img.getWidth() / width;
            int h = original_img.getHeight() / height;
            System.out.println("dividing image...");
            for(int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    int[] data = original_img.getRGB(i * width, j * height, width, height, null, 0, width);
                    temp.setRGB(0, 0, width, height, data, 0, width);
                    /*File f = new File("./a/"+Integer.toString(i * width + j)+".png");
                    f.createNewFile();
                    ImageIO.write(temp, "png", ImageIO.createImageOutputStream(f));*/
                    bufferedImages.add(new ImageWithPosition(reduceColor(temp), i, j));
                }
            }
            System.out.println("Done.");
            System.out.println("loading all images");
            List<File> images = findAllFile(new File("./data").getAbsolutePath());
            ArrayList<BufferedImage> images2 = new ArrayList<>();
            ArrayList<BufferedImage> reduced = new ArrayList<>();
            for (File image : images) {
                if (!(image.getName().endsWith("png") || image.getName().endsWith("jpg")))
                    continue;
                BufferedImage img = ImageIO.read(image);
                img = scaleImage(img, width, height);
                images2.add(img);
                reduced.add(reduceColor(img));
            }
            System.out.println("Done");
            System.out.println("processing...");
            int i = 0;
            for(ImageWithPosition iwp : bufferedImages) {
                i++;
                System.out.print("\r");
                System.out.print(i + "/" + bufferedImages.size());
                HashMap<BufferedImage, Double> map = new HashMap<>();
                for (BufferedImage im : reduced) {
                    map.put(im, colorDistance(im, iwp.img));
                }

                BufferedImage minImage = bufferedImages.get(0).img;
                double min = Double.MAX_VALUE;
                for(BufferedImage t : map.keySet()) {
                    if(map.get(t) < min) {
                        minImage = t;
                        min = map.get(t);
                    }
                }
                destination_img.setRGB(iwp.x * width, iwp.y * height, width, height, minImage.getRGB(0, 0, width, height, null, 0, width), 0, width);
            }


            File f = new File("result.png");
            f.createNewFile(); 
            ImageIO.write(destination_img, "png", ImageIO.createImageOutputStream(f));
    


        } catch(IOException e) {
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
    }

    // サイズの変更
    private static BufferedImage scaleImage(BufferedImage img, int dw, int dh) {
        // image のサイズを(dw, dh)に変更する
        BufferedImage resizeImage = new BufferedImage(dw, dh, BufferedImage.TYPE_3BYTE_BGR);
        resizeImage.createGraphics().drawImage(img.getScaledInstance(dw, dh, Image.SCALE_AREA_AVERAGING), 0, 0, dw, dh,
                null);
        return resizeImage;
    }

    private static BufferedImage reduceColor(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                int rgb = img.getRGB(x, y);
                int b = reduceValue((rgb & 0xff));
                int g = reduceValue(((rgb & 0xff00) >> 8));
                int r = reduceValue(((rgb & 0xff0000) >> 16));
                rgb = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, rgb);
            }
        }
        return img;
    }

    private static double colorDistance(BufferedImage a, BufferedImage b) {
        if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) return Double.MAX_VALUE;
        double d = 0.0;
        for(int x = 0; x < a.getWidth(); x++) {
            for(int y = 0; y < a.getHeight(); y++) {
                int rgb = a.getRGB(x, y);
                int ab = (reduceValue((rgb & 0xff)) / 32 - 1) / 2;
                int ag = (reduceValue(((rgb & 0xff00) >> 8)) / 32 - 1) / 2;
                int ar = (reduceValue(((rgb & 0xff0000) >> 16)) / 32 - 1) / 2;
                rgb = b.getRGB(x, y);
                int bb = (reduceValue((rgb & 0xff)) / 32 - 1) / 2;
                int bg = (reduceValue(((rgb & 0xff00) >> 8)) / 32 - 1) / 2;
                int br = (reduceValue(((rgb & 0xff0000) >> 16)) / 32 - 1) / 2;

                d += (double) ((ab - bb) * (ab - bb) + (ag - bg) * (ag - bg) + (ar - br) * (ar - br)) / 64.0; 
            }
        }
        return d;
    }

    private static int reduceValue(int value) {
        if (value < 64) {
            return 32;
        } else if (value < 128) {
            return 96;
        } else if (value < 196) {
            return 160;
        } else {
            return 224;
        }
    }

    public static List<File> findAllFile(String absolutePath) throws IOException {
        return Files.walk(Paths.get(absolutePath)).map(path -> path.toFile()).filter(file -> file.isFile())
                .collect(Collectors.toList());
    }

    private static class ImageWithPosition {
        public BufferedImage img;
        public int x, y;
        public ImageWithPosition(BufferedImage img, int x, int y) {
            this.img = img;
            this.x = x;
            this.y = y;
        }
    }
}