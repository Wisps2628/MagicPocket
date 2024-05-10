package com.wisps.mpcommon.utils;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    public static void main(String[] args) throws IOException {
//        zipsBySize();
        zipsByScale();
//        circles();
    }

    public static void zipsBySize() throws IOException {
        File dir = new File("D:\\wow_from");
        for (File file : dir.listFiles()) {
            Thumbnails.of(file)
                    .size(120, 120)
                    .outputFormat("jpg")
                    .toFile("D:\\wow_to\\" + file.getName());
            System.out.println("图片压缩成功！");
        }
    }

    public static void zipsByScale() throws IOException {
        File dir = new File("D:\\wow_from");
        for (File file : dir.listFiles()) {
            Thumbnails.of(file)
                    .scale(0.5)
                    .outputFormat("jpg")
                    .toFile("D:\\wow_to\\" + file.getName());
            System.out.println("图片压缩成功！");
        }
    }

    public static void circles() throws IOException {
        File dir = new File("D:\\wow_to");
        for (File file : dir.listFiles()) {
            // 加载输入图片
            BufferedImage inputImage = ImageIO.read(file);

            // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
            BufferedImage image = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, inputImage.getWidth(), inputImage.getHeight());
            Graphics2D g2 = image.createGraphics();
            image = g2.getDeviceConfiguration().createCompatibleImage(inputImage.getWidth(), inputImage.getHeight(), Transparency.TRANSLUCENT);
            g2 = image.createGraphics();
            // 将背景设置为透明。如果注释该段代码，默认背景为白色.也可通过g2.setPaint(paint) 设置背景色
            g2.setComposite(AlphaComposite.Clear);
            g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            g2.setClip(shape);
            // 使用 setRenderingHint 设置抗锯齿
            g2.drawImage(inputImage, 0, 0, null);
            g2.dispose();

            File file1 = new File("D:\\wow_circle\\" + file.getName());
            if (file1.exists()) {

            }
            ImageIO.write(image, "png", new File("D:\\wow_circle\\" + file.getName()));
            System.out.println("图片圆角裁剪成功！");
        }
    }
}
