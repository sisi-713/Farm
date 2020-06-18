/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.util;


import java.awt.*;

import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 对图片进行缩小处理
 * @author Administrator
 */
public class ImageUtil {
    private static Component component = new Canvas();
    private static float zoomRatio=0.5f;    //缩放倍数

/**
 * 将图片缩小50%
 * @param fileName 要缩小的图片相对地址
 * @return 显示的图标
 * @throws java.lang.Exception
 */
    public ImageIcon createZoomSizeImage(String fileName) throws IOException
    {
        Image image=ImageIO.read(getClass().getResource(fileName));

        int width = new Double(image.getWidth(null) * zoomRatio).intValue();
        int height = new Double(image.getHeight(null) * zoomRatio).intValue();

        AreaAveragingScaleFilter areaAveragingScaleFilter = new AreaAveragingScaleFilter(width,height);
        FilteredImageSource filteredImageSource = new FilteredImageSource(image.getSource(),areaAveragingScaleFilter);
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.drawImage(component.createImage(filteredImageSource),0,0,null);

        ImageIcon icon=new ImageIcon(bufferedImage);
        return icon;
        
    }

//    public ImageIcon mergeImg(String fileName, int alpha) throws IOException {
//
////        Image image = ImageIO.read(getClass().getResource(fileName));
//        ImageIcon imageIcon =new ImageIcon(getClass().getResource(fileName));
//        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(),
//                imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
//
//        Graphics2D g2d = bufferedImage.createGraphics();
//        g2d.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
//
//        for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
//            for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
//                int rgb = bufferedImage.getRGB(j2, j1);
//                rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
//                bufferedImage.setRGB(j2, j1, rgb);
//            }
//        }
//        g2d.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
//        ImageIcon icon=new ImageIcon(bufferedImage);
//        return icon;
//    }
}
