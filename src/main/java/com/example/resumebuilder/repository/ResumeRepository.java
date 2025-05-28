package com.example.resumebuilder.repository;

import com.example.resumebuilder.model.Resume;
import org.springframework.data.repository.CrudRepository;

public interface ResumeRepository extends CrudRepository<Resume, Long> {
}
