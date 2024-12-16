package pipe;

import java.util.ArrayList;
import java.util.List;

/**
 * 管道类，用于串联多个过滤器并按顺序处理数据
 * @param <T> 处理数据的类型
 */
public class Pipeline<T> {
    /** 存储所有的过滤器 */
    private List<Pipe<T>> pipes = new ArrayList<>();
    
    /**
     * 添加一个过滤器到管道中
     * @param pipe 要添加的过滤器
     * @return 当前管道对象，支持链式调用
     */
    public Pipeline<T> addPipe(Pipe<T> pipe) {
        pipes.add(pipe);
        return this;
    }
    
    /**
     * 执行管道中的所有过滤器
     * @param input 输入数据
     * @return 经过所有过滤器处理后的结果
     */
    public List<T> execute(List<T> input) {
        List<T> current = input;
        for (Pipe<T> pipe : pipes) {
            current = pipe.process(current);
        }
        return current;
    }
} 