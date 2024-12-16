package observer;

import java.util.List;

public interface IndexObserver {
    void onIndexChanged(List<String> newIndex);
} 