import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import adapter.DataOperation;
import adapter.DataOperationFactory;
import observer.IndexObserver;

public class SwingUI extends JFrame implements IndexObserver {
    private KWICService kwicService;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextArea sentencesArea;
    
    public SwingUI() {
        this.kwicService = new KWICService();
        this.kwicService.addObserver(this);
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("KWIC索引系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // 创建左侧面板（输入区域和已输入句子列表）
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        
        // 输入面板
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入区域"));
        
        inputArea = new JTextArea(5, 40);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        
        // 在输入面板添加文件选择按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("添加句子");
        JButton openFileButton = new JButton("打开文件");
        JButton dbButton = new JButton("从数据库导入");
        
        addButton.addActionListener(e -> addSentence());
        openFileButton.addActionListener(e -> openFile());
        dbButton.addActionListener(e -> importFromDB());
        
        buttonPanel.add(addButton);
        buttonPanel.add(openFileButton);
        buttonPanel.add(dbButton);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 已输入句子显示面板
        JPanel sentencesPanel = new JPanel(new BorderLayout(5, 5));
        sentencesPanel.setBorder(BorderFactory.createTitledBorder("已输入句子"));
        
        sentencesArea = new JTextArea(10, 40);
        sentencesArea.setEditable(false);
        sentencesArea.setLineWrap(true);
        sentencesArea.setWrapStyleWord(true);
        sentencesPanel.add(new JScrollPane(sentencesArea), BorderLayout.CENTER);
        
        // 将输入面板和已输入句子面板添加到左侧面板
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(sentencesPanel, BorderLayout.CENTER);
        
        // 创建右侧输出面板
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.setBorder(BorderFactory.createTitledBorder("KWIC索引结果"));
        
        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        // 修改输出面板底部按钮布局
        JPanel outputButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exportButton = new JButton("导出索引");
        JButton exportToDBButton = new JButton("导出到数据库");
        
        exportButton.addActionListener(e -> exportIndex());
        exportToDBButton.addActionListener(e -> exportToDB());
        
        outputButtonPanel.add(exportButton);
        outputButtonPanel.add(exportToDBButton);
        outputPanel.add(outputButtonPanel, BorderLayout.SOUTH);
        
        // 添加到主窗口
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(outputPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void addSentence() {
        try {
            DataOperation operation = DataOperationFactory.createOperation("text", "input", this, inputArea);
            List<String> sentences = operation.read();
            for (String sentence : sentences) {
                kwicService.addSentence(sentence);
            }
            updateSentencesArea();
            inputArea.setText("");
            JOptionPane.showMessageDialog(this, "句子已添加！", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void onIndexChanged(List<String> newIndex) {
        try {
            DataOperation operation = DataOperationFactory.createOperation("text", "output", this, outputArea);
            operation.write(newIndex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateSentencesArea() {
        StringBuilder sb = new StringBuilder();
        for (String sentence : kwicService.getSentences()) {
            sb.append(sentence).append("\n");
        }
        sentencesArea.setText(sb.toString());
    }
    
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        kwicService.addSentence(line);
                        count++;
                    }
                }
                updateSentencesArea();
                JOptionPane.showMessageDialog(this, 
                    String.format("成功导入%d个句子！", count), 
                    "提示", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "读取文件失败：" + ex.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void exportIndex() {
        if (outputArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "请先生成索引！",
                "提示",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            DataOperation operation = DataOperationFactory.createOperation("file", "output", this, outputArea);
            operation.write(kwicService.generateIndex());
            JOptionPane.showMessageDialog(this,
                "索引已成功导出！",
                "提示",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "导出失败：" + ex.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void importFromDB() {
        // 这里可以弹出配置对话框让用户输入数据库接信息
        try {
            DatabaseService dbService = createDatabaseService();  // 创建具体实现类的实例
            dbService.connect("jdbc:mysql://localhost:3306/kwic", "username", "password");
            List<String> sentences = dbService.loadSentencesFromDB();
            
            int count = 0;
            for (String sentence : sentences) {
                if (!sentence.trim().isEmpty()) {
                    kwicService.addSentence(sentence);
                    count++;
                }
            }
            
            updateSentencesArea();
            JOptionPane.showMessageDialog(this,
                String.format("从数据库成功导入%d个句子！", count),
                "提示",
                JOptionPane.INFORMATION_MESSAGE);
                
            dbService.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "数据库操作失败：" + ex.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private DatabaseService createDatabaseService() {
        // 返回一个空实现，因为实际实现将在后续完成
        return new DatabaseService() {
            public List<String> loadSentencesFromDB() { return new ArrayList<>(); }
            public void saveSentencesToDB(List<String> sentences) { }
            public void connect(String url, String username, String password) { }
            public void disconnect() { }
        };
    }
    
    private void exportToDB() {
        if (outputArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "请先生成索引！",
                "提示",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            DataOperation operation = DataOperationFactory.createOperation("database", "output", this, outputArea);
            operation.write(kwicService.generateIndex());
            
            JOptionPane.showMessageDialog(this,
                "索引已成功导出到数据库！",
                "提示",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "导出到数据库失败：" + ex.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void start() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
} 