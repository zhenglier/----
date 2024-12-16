
import java.util.List;

public interface DatabaseService {
    List<String> loadSentencesFromDB();
    void saveSentencesToDB(List<String> sentences);
    void connect(String url, String username, String password);
    void disconnect();
} 