package io;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class FileOutputStrategy implements OutputStrategy {
    private final JFrame parent;
    
    public FileOutputStrategy(JFrame parent) {
        this.parent = parent;
    }
    
    @Override
    public void writeSentences(List<String> sentences) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                for (String sentence : sentences) {
                    writer.println(sentence);
                }
            } catch (IOException e) {
                throw new RuntimeException("写入文件失败：" + e.getMessage());
            }
        }
    }
} 