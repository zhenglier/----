package adapter;

import io.InputStrategy;
import java.util.List;

public class InputAdapter implements DataOperation {
    private final InputStrategy inputStrategy;
    
    public InputAdapter(InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
    }
    
    @Override
    public void write(List<String> data) {
        throw new UnsupportedOperationException("Input adapter doesn't support write operation");
    }
    
    @Override
    public List<String> read() {
        return inputStrategy.readSentences();
    }
} 