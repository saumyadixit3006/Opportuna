package com.opportuna.repository.file;

import com.opportuna.model.Opportunity;
import com.opportuna.repository.OpportunityRepository;
import com.opportuna.util.FileStore;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileOpportunityRepository implements OpportunityRepository {
    private final Path file;

    public FileOpportunityRepository(Path file) {
        this.file = file;
    }

    @Override
    public synchronized List<Opportunity> findAll() {
        return FileStore.readList(file, Opportunity.class);
    }

    @Override
    public synchronized Optional<Opportunity> findById(long id) {
        return findAll().stream()
                .filter(opportunity -> opportunity.getId() == id)
                .findFirst();
    }

    @Override
    public synchronized void save(Opportunity opportunity) {
        List<Opportunity> all = findAll();
        all.removeIf(existing -> existing.getId() == opportunity.getId());
        all.add(opportunity);
        FileStore.writeList(file, all);
    }

    @Override
    public synchronized void deleteById(long id) {
        List<Opportunity> all = findAll();
        all.removeIf(opportunity -> opportunity.getId() == id);
        FileStore.writeList(file, all);
    }
}

