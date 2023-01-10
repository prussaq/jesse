package net.prussaq.jesse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class DatabaseDao {

    @Value("${database.backup-path}")
    private String backupPath;

    private final DataSource dataSource;

    public void backup() {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("BACKUP TO '" + backupPath + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
