package hexlet.code.repository;

import hexlet.code.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByName(String name);
    List<Task> findByAssigneeId(Long id);
    List<Task> findByTaskStatusId(Long id);
}
