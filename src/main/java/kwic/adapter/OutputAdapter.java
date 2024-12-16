package adapter;

import io.OutputStrategy;
import java.util.List;

public class OutputAdapter implements DataOperation {
    private final OutputStrategy outputStrategy;
    
    public OutputAdapter(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }
    
    @Override
    public void write(List<String> data) {
        outputStrategy.writeSentences(data);
    }
    
    @Override
    public List<String> read() {
        throw new UnsupportedOperationException("Output adapter doesn't support read operation");
    }
} 