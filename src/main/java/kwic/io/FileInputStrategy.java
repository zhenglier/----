package io;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileInputStrategy implements InputStrategy {
    private final JFrame parent;
    
    public FileInputStrategy(JFrame parent) {
        this.parent = parent;
    }
    
    @Override
    public List<String> readSentences() {
        List<String> sentences = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        sentences.add(line.trim());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("读取文件失败：" + e.getMessage());
            }
        }
        return sentences;
    }
} 