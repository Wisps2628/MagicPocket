package com.wisps.mpcommon.utils;

import lombok.Getter;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.util.List;

/**
 * 纯文字word文档读取演示
 */
public class WordReader {

    @Getter
    static class Intro {
        private String name;
        private List<String> intros;

        public Intro(String name, List<String> intros) {
            this.name = name;
            this.intros = intros;
        }
    }

    /**
     * 马克思主义早期文本典籍库项目 章节说明word文档读取，转换成mysql insert语句
     *
     * @param args
     */
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("D:\\doc\\develop\\人民社》》马克思主义早期文本典籍库/马克思文本说明文字-数字化用.docx")) {
            XWPFDocument document = new XWPFDocument(fis);

            // 读取所有段落
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            List<Intro> intros = Lists.newArrayList();
            Intro intro = null;
            for (XWPFParagraph para : paragraphs) {
                String text = para.getText();
                if (intro == null) {
                    intro = new Intro(text, Lists.newArrayList());
                } else if ("\n".equals(text)) {
                    if (intro != null) {
                        intros.add(intro);
                    }
                    intro = null;
                } else if ("说 明".equals(text)) {

                } else {
                    intro.intros.add(text);
                }
            }

            StringBuilder sb = new StringBuilder();
            intros.forEach(item -> {
                sb.append("INSERT INTO book_chapter_intro (`chapter_name`, `chapter_intro`, `create_time`) ");
                sb.append("VALUES (")
                        .append("\"").append(item.name).append("\", ")
                        .append("\"").append(String.join("【cjsz】", item.intros)).append("\",")
                        .append("\"2024-05-09 17:14:19\");\n");
            });
            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
