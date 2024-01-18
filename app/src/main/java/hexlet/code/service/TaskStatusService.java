package hexlet.code.service;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskStatusService {

    private final TaskStatusRepository statusRepository;
    private final TaskStatusMapper statusMapper;

    public List<TaskStatusDTO> getAll() {
        var statuses = statusRepository.findAll();
        return statuses.stream()
                .map(statusMapper::map)
                .toList();
    }

    public TaskStatusDTO getById(Long id) {
        var status = statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + "not found"));
        return statusMapper.map(status);
    }

    public TaskStatusDTO create(TaskStatusCreateDTO data) {
        var status = statusMapper.map(data);
        statusRepository.save(status);
        return statusMapper.map(status);
    }

    public TaskStatusDTO update(Long id, TaskStatusUpdateDTO data) {
        var status = statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + "not found"));
        statusMapper.update(data, status);
        statusRepository.save(status);
        return statusMapper.map(status);
    }

    public void delete(Long id) {
        statusRepository.deleteById(id);
    }
}
