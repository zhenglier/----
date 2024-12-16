package io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * 数据库输出策略
 * 将数据写入数据库
 */
public class DatabaseOutputStrategy implements OutputStrategy {
    private final String url;
    private final String username;
    private final String password;

    public DatabaseOutputStrategy() {
        this.url = "jdbc:mysql://localhost:3306/kwic";
        this.username = "root";
        this.password = "123456";
    }

    @Override
    public void writeSentences(List<String> sentences) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO sentences (text) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (String sentence : sentences) {
                    statement.setString(1, sentence);
                    statement.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("写入数据库失败：" + e.getMessage());
        }
    }
} 