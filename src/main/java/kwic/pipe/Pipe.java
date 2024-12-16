package pipe;

import java.util.List;

/**
 * 管道接口，定义了过滤器的基本行为
 * @param <T> 处理数据的类型
 */
public interface Pipe<T> {
    /**
     * 处理输入的数据列表
     * @param input 输入数据列表
     * @return 处理后的数据列表
     */
    List<T> process(List<T> input);
} 