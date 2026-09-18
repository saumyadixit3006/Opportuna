package com.opportuna.repository;

import com.opportuna.model.Opportunity;

import java.util.List;
import java.util.Optional;

public interface OpportunityRepository {
    List<Opportunity> findAll();
    Optional<Opportunity> findById(long id);
    void save(Opportunity opportunity);
    void deleteById(long id);
}
