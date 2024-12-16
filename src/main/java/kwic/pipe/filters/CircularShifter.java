package pipe.filters;

import java.util.ArrayList;
import java.util.List;
import pipe.Pipe;

/**
 * 循环移位过滤器
 * 将输入的每个句子进行循环移位处理
 */
public class CircularShifter implements Pipe<String> {
    
    /**
     * 对输入的句子列表进行循环移位处理
     * @param input 输入的句子列表
     * @return 所有可能的循环移位结果
     */
    @Override
    public List<String> process(List<String> input) {
        List<String> shifts = new ArrayList<>();
        for (String sentence : input) {
            // 将句子分割成单词数组
            String[] words = sentence.trim().split("\\s+");
            int n = words.length;
            
            // 对每个单词位置进行移位
            for (int i = 0; i < n; i++) {
                StringBuilder shifted = new StringBuilder();
                // 构建移位后的句子
                for (int j = 0; j < n; j++) {
                    shifted.append(words[(i + j) % n]).append(" ");
                }
                shifts.add(shifted.toString().trim());
            }
        }
        return shifts;
    }
} 