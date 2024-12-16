package pipe.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pipe.Pipe;

/**
 * 字母排序过滤器
 * 将输入的句子列表按字母顺序排序
 */
public class Alphabetizer implements Pipe<String> {
    
    /**
     * 对输入的句子列表进行排序
     * @param input 输入的句子列表
     * @return 排序后的句子列表
     */
    @Override
    public List<String> process(List<String> input) {
        List<String> sorted = new ArrayList<>(input);
        Collections.sort(sorted, String.CASE_INSENSITIVE_ORDER);
        return sorted;
    }
} 