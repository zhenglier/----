import observer.IndexObserver;
import java.util.ArrayList;
import java.util.List;
import pipe.Pipeline;
import pipe.filters.CircularShifter;
import pipe.filters.Alphabetizer;

/**
 * KWIC系统的核心服务类
 * 负责管理句子、生成索引并通知观察者
 */
public class KWICService {
    private List<Sentence> sentences;          // 存储所有输入的句子
    private List<IndexObserver> observers;     // 存储所有观察者
    private Pipeline<String> pipeline;         // 处理管道
    
    /**
     * 初始化服务，设置管道和过滤器
     */
    public KWICService() {
        this.sentences = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.pipeline = new Pipeline<>();
        // 配置处理管道
        this.pipeline
            .addPipe(new CircularShifter())   // 首先进行循环移位
            .addPipe(new Alphabetizer());      // 然后按字母排序
    }
    
    public void addObserver(IndexObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(IndexObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers() {
        List<String> index = generateIndex();
        for (IndexObserver observer : observers) {
            observer.onIndexChanged(index);
        }
    }
    
    public void addSentence(String text) {
        sentences.add(new Sentence(text));
        notifyObservers();
    }
    
    public List<String> generateIndex() {
        List<String> input = getSentences();
        return pipeline.execute(input);
    }
    
    public List<String> getSentences() {
        List<String> result = new ArrayList<>();
        for (Sentence sentence : sentences) {
            result.add(sentence.getOriginalText());
        }
        return result;
    }
} 