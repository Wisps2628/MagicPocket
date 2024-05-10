package com.wisps.mpcommon.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CircleImageGenerator {

    private static final Map<String, Color> JOB_COLOR_MAP = Maps.newHashMap();

    static {
        JOB_COLOR_MAP.put("ZS", Color.decode("#C79C6E"));
        JOB_COLOR_MAP.put("FS", Color.decode("#69CCF0"));
        JOB_COLOR_MAP.put("SM", Color.decode("#0070DE"));
        JOB_COLOR_MAP.put("MS", Color.decode("#FFFFFF"));
        JOB_COLOR_MAP.put("DZ", Color.decode("#FFF569"));
        JOB_COLOR_MAP.put("XD", Color.decode("#FF7D0A"));
        JOB_COLOR_MAP.put("QS", Color.decode("#F58CBA"));
        JOB_COLOR_MAP.put("LR", Color.decode("#ABD473"));
        JOB_COLOR_MAP.put("SS", Color.decode("#9482C9"));
        JOB_COLOR_MAP.put("DK", Color.decode("#C41F3B"));
        JOB_COLOR_MAP.put("WS", Color.decode("#00FF96"));
        JOB_COLOR_MAP.put("DH", Color.decode("#A330C9"));
    }

    static class Player {
        private String name;
        private String job;

        public Player(String name, String job) {
            this.name = name;
            this.job = job;
        }

        public static Player instance(String name, String job) {
            return new Player(name, job);
        }
    }

    public static void main(String[] args) throws IOException {
//        String[] arr = {"张三", "李四", "王五", "赵六", "孙七", "周八", "吴九", "郑十", "秦十一", "钱十二", "孙十三", "李十四", "王十五", "赵十六", "孙十七", "周十八", "吴十九", "郑二十", "秦二十一", "钱二十二", "孙二十三", "李二十四", "王二十五"};
        List<Player> players = Lists.newArrayList(
                Player.instance("张三", "ZS"), Player.instance("李四", "FS"), Player.instance("王五", "SM"),
                Player.instance("赵六", "MS"), Player.instance("孙七", "DZ"), Player.instance("周八", "XD"),
                Player.instance("吴九", "QS"), Player.instance("郑十", "LR"), Player.instance("秦十一", "SS"),
                Player.instance("钱十二", "DK"), Player.instance("孙十三", "WS"), Player.instance("李十四", "DH"),
                Player.instance("王十五", "ZS"), Player.instance("赵十六", "FS"), Player.instance("孙十七", "SM"),
                Player.instance("周十八", "MS"), Player.instance("吴十九", "DZ"), Player.instance("郑二十", "XD"),
                Player.instance("秦二十一", "QS"), Player.instance("钱二十二", "LR"), Player.instance("孙二十三", "SS"),
                Player.instance("李二十四", "DK"), Player.instance("王二十五", "WS"), Player.instance("王二十六", "ZS"),
                Player.instance("王二十七", "FS"), Player.instance("王二十八", "SM"), Player.instance("王二十九", "MS"),
                Player.instance("王三十", "DZ"), Player.instance("王三十一", "XD"), Player.instance("王三十二", "QS"),
                Player.instance("王三十三", "LR"), Player.instance("王三十四", "SS"), Player.instance("王三十五", "DK"),
                Player.instance("王三十六", "WS"), Player.instance("王三十七", "DH")
        );

        for (int i = 0; i < players.size(); i++) {
            generateImg(players.get(i).name, "D:\\", (i + 1) + "", players.get(i).job);
        }
    }

    /**
     * 绘制字体头像
     * 如果是英文名，只显示首字母大写
     * 如果是中文名，只显示最后两个字
     *
     * @param name       字符串
     * @param outputPath 图片储存地址
     * @param outputName 图片名称
     * @throws IOException
     */
    public static void generateImg(String name, String outputPath, String outputName, String job) throws IOException {
        int width = 100;
        int height = 100;
        int nameLen = name.length();
        String nameWritten;
        //如果用户输入的姓名少于等于2个字符，不用截取
        if (nameLen <= 2) {
            nameWritten = name;
        } else {
            //如果用户输入的姓名大于等于3个字符，截取后面两位
            String first = name.substring(0, 1);
            if (isChinese(first)) {
                //截取倒数两位汉字
//                nameWritten = name.substring(nameLen - 2);
                nameWritten = name.substring(0, 2);
            } else {
                //截取前面的两个英文字母
                nameWritten = name.substring(0, 2).toUpperCase();
            }
        }

        String filename = outputPath + File.separator + outputName + ".jpg";
        File file = new File(filename);
        //Font font = new Font("微软雅黑", Font.PLAIN, 30);

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(JOB_COLOR_MAP.get(job));
        g2.clearRect(0, 0, width, height);
        if ("MS".equals(job) || "DZ".equals(job)) {
            g2.setPaint(Color.GRAY);
        } else {
            g2.setPaint(Color.WHITE);
        }

        Font font = null;
        //两个字及以上
        if (nameWritten.length() >= 2) {
            font = new Font("微软雅黑", Font.PLAIN, 30);
            g2.setFont(font);

            String firstWritten = nameWritten.substring(0, 1);
            String secondWritten = nameWritten.substring(1, 2);

            //两个中文 如 言曌
            if (isChinese(firstWritten) && isChinese(secondWritten)) {
                g2.drawString(nameWritten, 20, 60);
            }
            //首中次英 如 罗Q
            else if (isChinese(firstWritten) && !isChinese(secondWritten)) {
                g2.drawString(nameWritten, 27, 60);

                //首英,如 AB
            } else {
                nameWritten = nameWritten.substring(0, 1);
            }
        }
        //一个字
        if (nameWritten.length() == 1) {
            //中文
            if (isChinese(nameWritten)) {
                font = new Font("微软雅黑", Font.PLAIN, 50);
                g2.setFont(font);
                g2.drawString(nameWritten, 25, 70);
            }
            //英文
            else {
                font = new Font("微软雅黑", Font.PLAIN, 55);
                g2.setFont(font);
                g2.drawString(nameWritten.toUpperCase(), 33, 67);
            }

        }

        BufferedImage rounded = makeRoundedCorner(bi, 99);
        ImageIO.write(rounded, "png", file);
    }

    /**
     * 判断字符串是否为中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 图片做圆角处理
     *
     * @param image
     * @param cornerRadius
     * @return
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }
}
