package com.opportuna.repository.jdbc;

import com.opportuna.model.Opportunity;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        try (Connection connection = Database.connect()) {
            JdbcOpportunityRepository repository = new JdbcOpportunityRepository(connection);

            Opportunity sample = new Opportunity(
                    9001L,
                    "JDBC Demo Opportunity",
                    "OPPORTUNA Test",
                    "Demo",
                    LocalDate.now().plusDays(30),
                    1,
                    0,
                    List.of("Java", "JDBC"));

            repository.save(sample);
            System.out.println("Saved: " + repository.findById(9001L).orElseThrow());
            System.out.println("Total opportunities: " + repository.findAll().size());

            repository.deleteById(9001L);
            System.out.println("Demo record deleted successfully.");
        }
    }
}

