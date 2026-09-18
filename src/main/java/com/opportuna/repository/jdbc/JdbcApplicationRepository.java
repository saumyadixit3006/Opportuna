package com.opportuna.repository.jdbc;

import com.opportuna.exception.DatabaseOperationException;
import com.opportuna.model.Application;
import com.opportuna.model.ApplicationStatus;
import com.opportuna.repository.ApplicationRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcApplicationRepository implements ApplicationRepository {
    private final Connection connection;

    public JdbcApplicationRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Application> findAll() {
        String sql = "SELECT id, opportunity_id, applied_on, status, note "
                + "FROM applications ORDER BY applied_on DESC, id DESC";
        List<Application> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw databaseError("Could not read applications", e);
        }
    }

    @Override
    public Optional<Application> findById(long id) {
        String sql = "SELECT id, opportunity_id, applied_on, status, note "
                + "FROM applications WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(map(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw databaseError("Could not find application", e);
        }
    }

    @Override
    public void save(Application application) {
        String sql = "INSERT INTO applications "
                + "(id, opportunity_id, applied_on, status, note) VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE status = VALUES(status), note = VALUES(note)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, application.getId());
            statement.setLong(2, application.getOpportunityId());
            statement.setDate(3, Date.valueOf(application.getAppliedOn()));
            statement.setString(4, application.getStatus().name());
            statement.setString(5, application.getNote());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw databaseError("Could not save application", e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM applications WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw databaseError("Could not delete application", e);
        }
    }

    private Application map(ResultSet resultSet) throws SQLException {
        return new Application(
                resultSet.getLong("id"),
                resultSet.getLong("opportunity_id"),
                resultSet.getDate("applied_on").toLocalDate(),
                ApplicationStatus.valueOf(resultSet.getString("status")),
                resultSet.getString("note"));
    }

    private IllegalStateException databaseError(String message, SQLException cause) {
        return new IllegalStateException(new DatabaseOperationException(message, cause));
    }
}
