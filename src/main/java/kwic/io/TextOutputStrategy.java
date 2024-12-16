package io;

import javax.swing.JTextArea;
import java.util.List;

public class TextOutputStrategy implements OutputStrategy {
    private final JTextArea outputArea;
    
    public TextOutputStrategy(JTextArea outputArea) {
        this.outputArea = outputArea;
    }
    
    @Override
    public void writeSentences(List<String> sentences) {
        StringBuilder result = new StringBuilder();
        for (String sentence : sentences) {
            result.append(sentence).append("\n");
        }
        outputArea.setText(result.toString());
    }
} 