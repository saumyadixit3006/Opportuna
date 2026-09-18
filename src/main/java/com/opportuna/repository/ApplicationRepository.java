package com.opportuna.repository;

import com.opportuna.model.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {
    List<Application> findAll();
    Optional<Application> findById(long id);
    void save(Application application);
    void deleteById(long id);
}
