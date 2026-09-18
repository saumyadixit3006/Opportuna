package com.opportuna.repository.file;

import com.opportuna.model.Application;
import com.opportuna.repository.ApplicationRepository;
import com.opportuna.util.FileStore;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileApplicationRepository implements ApplicationRepository {
    private final Path file;

    public FileApplicationRepository(Path file) {
        this.file = file;
    }

    @Override
    public synchronized List<Application> findAll() {
        return FileStore.readList(file, Application.class);
    }

    @Override
    public synchronized Optional<Application> findById(long id) {
        return findAll().stream()
                .filter(application -> application.getId() == id)
                .findFirst();
    }

    @Override
    public synchronized void save(Application application) {
        List<Application> all = findAll();
        all.removeIf(existing -> existing.getId() == application.getId());
        all.add(application);
        FileStore.writeList(file, all);
    }

    @Override
    public synchronized void deleteById(long id) {
        List<Application> all = findAll();
        all.removeIf(application -> application.getId() == id);
        FileStore.writeList(file, all);
    }
}
