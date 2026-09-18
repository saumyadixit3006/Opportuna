package com.opportuna.repository.jdbc;

import com.opportuna.exception.DatabaseOperationException;
import com.opportuna.model.Opportunity;
import com.opportuna.repository.OpportunityRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JdbcOpportunityRepository implements OpportunityRepository {
    private final Connection connection;

    public JdbcOpportunityRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Opportunity> findAll() {
        String sql = "SELECT id, title, organization, type, deadline, duration_weeks, stipend, required_skills "
                + "FROM opportunities ORDER BY deadline, title";
        List<Opportunity> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(map(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw databaseError("Could not read opportunities", e);
        }
    }

    @Override
    public Optional<Opportunity> findById(long id) {
        String sql = "SELECT id, title, organization, type, deadline, duration_weeks, stipend, required_skills "
                + "FROM opportunities WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(map(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw databaseError("Could not find opportunity", e);
        }
    }

    @Override
    public void save(Opportunity opportunity) {
        String sql = "INSERT INTO opportunities "
                + "(id, title, organization, type, deadline, duration_weeks, stipend, required_skills) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "title = VALUES(title), organization = VALUES(organization), "
                + "type = VALUES(type), deadline = VALUES(deadline), "
                + "duration_weeks = VALUES(duration_weeks), stipend = VALUES(stipend), "
                + "required_skills = VALUES(required_skills)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, opportunity.getId());
            statement.setString(2, opportunity.getTitle());
            statement.setString(3, opportunity.getOrganization());
            statement.setString(4, opportunity.getType());
            statement.setDate(5, java.sql.Date.valueOf(opportunity.getDeadline()));
            statement.setInt(6, opportunity.getDurationWeeks());
            statement.setInt(7, opportunity.getStipend());
            statement.setString(8, String.join(",", opportunity.getRequiredSkills()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw databaseError("Could not save opportunity", e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM opportunities WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw databaseError("Could not delete opportunity", e);
        }
    }

    private Opportunity map(ResultSet resultSet) throws SQLException {
        String rawSkills = resultSet.getString("required_skills");
        List<String> skills = rawSkills == null
                ? List.of()
                : Arrays.stream(rawSkills.split(","))
                .map(String::trim)
                .filter(skill -> !skill.isBlank())
                .toList();

        return new Opportunity(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("organization"),
                resultSet.getString("type"),
                resultSet.getDate("deadline").toLocalDate(),
                resultSet.getInt("duration_weeks"),
                resultSet.getInt("stipend"),
                skills);
    }

    private IllegalStateException databaseError(String message, SQLException cause) {
        return new IllegalStateException(new DatabaseOperationException(message, cause));
    }
}

