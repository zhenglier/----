package io;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TextInputStrategy implements InputStrategy {
    private final JTextArea textArea;
    
    public TextInputStrategy(JTextArea textArea) {
        this.textArea = textArea;
    }
    
    @Override
    public List<String> readSentences() {
        String text = textArea.getText().trim();
        List<String> sentences = new ArrayList<>();
        if (!text.isEmpty()) {
            sentences.add(text);
        }
        return sentences;
    }
} 