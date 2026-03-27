package org.stonesoup.hackathon.services;

import jakarta.persistence.EntityNotFoundException;
import org.stonesoup.hackathon.models.BaseEntity;
import org.stonesoup.hackathon.repositories.BaseRepository;

import java.util.List;

public abstract class BaseService<T extends BaseEntity, R extends BaseRepository<T>> {
    protected final R repository;

    protected BaseService(R repository) {
        this.repository = repository;
    }

    public List<T> findAll() { return repository.findAll(); }

    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + id));
    }

    public T save(T entity) { return repository.save(entity); }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Resource not found with id: " + id);
        }
        repository.deleteById(id);
    }
}