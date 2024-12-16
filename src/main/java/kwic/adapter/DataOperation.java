package adapter;

import java.util.List;

public interface DataOperation {
    void write(List<String> data);
    List<String> read();
} 