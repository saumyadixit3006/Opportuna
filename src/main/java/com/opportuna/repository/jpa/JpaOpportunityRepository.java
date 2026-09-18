package com.opportuna.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaOpportunityRepository {
    private final EntityManager entityManager;

    public JpaOpportunityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(JpaOpportunity opportunity) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(opportunity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Optional<JpaOpportunity> findById(long id) {
        return Optional.ofNullable(entityManager.find(JpaOpportunity.class, id));
    }

    public List<JpaOpportunity> findAll() {
        return entityManager.createQuery(
                        "SELECT o FROM JpaOpportunity o ORDER BY o.deadline, o.title",
                        JpaOpportunity.class)
                .getResultList();
    }

    public void deleteById(long id) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            JpaOpportunity opportunity = entityManager.find(JpaOpportunity.class, id);
            if (opportunity != null) {
                entityManager.remove(opportunity);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

