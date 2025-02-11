package com.jude.sparkinternshipjude.repository;

import com.jude.sparkinternshipjude.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
}