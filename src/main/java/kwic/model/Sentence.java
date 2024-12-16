
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Sentence {
    private String originalText;
    private List<String> words;
    
    public Sentence(String text) {
        this.originalText = text;
        this.words = Arrays.asList(text.trim().split("\\s+"));
    }
    
    public List<String> getShiftedSentences() {
        List<String> shifts = new ArrayList<>();
        int n = words.size();
        
        for (int i = 0; i < n; i++) {
            StringBuilder shifted = new StringBuilder();
            for (int j = 0; j < n; j++) {
                shifted.append(words.get((i + j) % n)).append(" ");
            }
            shifts.add(shifted.toString().trim());
        }
        return shifts;
    }
    
    public String getOriginalText() {
        return originalText;
    }
} 