package io;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class IOFactory {
    public static InputStrategy createInput(String type, JFrame parent, JTextArea textArea) {
        return switch (type) {
            case "text" -> new TextInputStrategy(textArea);
            case "file" -> new FileInputStrategy(parent);
            case "database" -> new DatabaseInputStrategy();
            default -> throw new IllegalArgumentException("Unknown input type: " + type);
        };
    }
    
    public static OutputStrategy createOutput(String type, JFrame parent, JTextArea outputArea) {
        return switch (type) {
            case "text" -> new TextOutputStrategy(outputArea);
            case "file" -> new FileOutputStrategy(parent);
            case "database" -> new DatabaseOutputStrategy();
            default -> throw new IllegalArgumentException("Unknown output type: " + type);
        };
    }
} 