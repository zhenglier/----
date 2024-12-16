package adapter;

import io.IOFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * 数据操作工厂类
 * 用于创建输入输出操作的适配器
 */
public class DataOperationFactory {
    /**
     * 创建数据操作对象
     * @param type 操作类型（text/file/database）
     * @param mode 操作模式（input/output）
     * @param parent 父窗口组件
     * @param textArea 文本区域组件
     * @return 对应的数据操作对象
     */
    public static DataOperation createOperation(String type, String mode, JFrame parent, JTextArea textArea) {
        return switch (mode) {
            case "input" -> new InputAdapter(IOFactory.createInput(type, parent, textArea));
            case "output" -> new OutputAdapter(IOFactory.createOutput(type, parent, textArea));
            default -> throw new IllegalArgumentException("Unknown mode: " + mode);
        };
    }
} 