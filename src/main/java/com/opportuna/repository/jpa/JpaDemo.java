package com.opportuna.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class JpaDemo {
    public static void main(String[] args) {
        EntityManagerFactory factory = JpaBootstrap.createFactory();

        try {
            EntityManager entityManager = JpaBootstrap.openEntityManager(factory);
            try {
                JpaOpportunityRepository repository =
                        new JpaOpportunityRepository(entityManager);

                JpaOpportunity sample = new JpaOpportunity(
                        9100L,
                        "JPA Demo Opportunity",
                        "OPPORTUNA Test",
                        "Demo",
                        LocalDate.now().plusDays(30).toString(),
                        4,
                        10000,
                        "Java,JPA,Hibernate");

                repository.save(sample);

                System.out.println("Saved: " + repository.findById(9100L)
                        .map(JpaOpportunity::getTitle)
                        .orElse("Not found"));
                System.out.println("Total JPA opportunities: " + repository.findAll().size());

                repository.deleteById(9100L);
                System.out.println("JPA demo record deleted successfully.");
            } finally {
                entityManager.close();
            }
        } finally {
            factory.close();
        }
    }
}


