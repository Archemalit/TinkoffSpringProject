package com.archemalt.TinkoffSpringProject.repository.Impl;

import com.archemalt.TinkoffSpringProject.repository.TranslateRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Repository
public class TranslateRepositoryImpl implements TranslateRepository {
    private final String SQL_CREATE_TABLE_INFO =
            "CREATE TABLE IF NOT EXISTS info" +
            "(" +
            "    id    BIGSERIAL PRIMARY KEY," +
            "    ip  VARCHAR(50) NOT NULL," +
            "    source_language VARCHAR(15) NOT NULL," +
            "    result_language VARCHAR(15)  NOT NULL," +
            "    text text NOT NULL," +
            "    result_text text NOT NULL" +
            ");";

    private final String SQL_INSERT_TABLE_INFO =
            "INSERT INTO info" +
            "(ip, source_language, result_language, text, result_text)" +
            "VALUES (?, ?, ?, ?, ?)";
    private DataSource dataSource;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    private void checkConnectionSsl() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setMaximumPoolSize(40);
        dataSource = new HikariDataSource(hikariConfig);

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(SQL_CREATE_TABLE_INFO);
            stmt.executeUpdate();
            System.out.println("Connection was successful");
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
    }
    @Override
    public void saveResult(String ip, String srcLanguage, String resultLanguage, String text, String resultText) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(SQL_INSERT_TABLE_INFO);
            stmt.setString(1, ip);
            stmt.setString(2, srcLanguage);
            stmt.setString(3, resultLanguage);
            stmt.setString(4, text);
            stmt.setString(5, resultText);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
    }
}
