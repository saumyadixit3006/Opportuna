package com.opportuna.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public final class JpaBootstrap {
    private JpaBootstrap() {
    }

    public static EntityManagerFactory createFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(
                "jakarta.persistence.jdbc.url",
                System.getenv().getOrDefault(
                        "OPPORTUNA_DB_URL",
                        "jdbc:mysql://localhost:3306/opportuna?useSSL=false&serverTimezone=UTC"));
        properties.put(
                "jakarta.persistence.jdbc.user",
                System.getenv().getOrDefault("OPPORTUNA_DB_USER", "root"));
        properties.put(
                "jakarta.persistence.jdbc.password",
                System.getenv().getOrDefault("OPPORTUNA_DB_PASSWORD", ""));
        properties.put(
                "jakarta.persistence.jdbc.driver",
                "com.mysql.cj.jdbc.Driver");

        return Persistence.createEntityManagerFactory("opportunaPU", properties);
    }

    public static EntityManager openEntityManager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }
}
