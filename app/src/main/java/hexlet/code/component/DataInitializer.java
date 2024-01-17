package hexlet.code.component;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.UserCreateDTO;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.TaskStatusService;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserService userService;

    private final TaskStatusService statusService;

    private final TaskStatusRepository statusRepository;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        var data = new UserCreateDTO();
        data.setFirstName("Ivan");
        data.setLastName("Rurik");
        data.setEmail("hexlet@example.com");
        data.setPassword("qwerty");
        userService.create(data);

        Map<String, String> statuses = new HashMap<>();
        statuses.put("draft", "Draft");
        statuses.put("to_review", "ToReview");
        statuses.put("to_be_fixed", "ToBeFixed");
        statuses.put("to_publish", "ToPublish");
        statuses.put("published", "Published");

        TaskStatusCreateDTO statusCreateDTO = new TaskStatusCreateDTO();
        for (Map.Entry<String, String> status : statuses.entrySet()) {
            if (statusRepository.findBySlug(status.getKey()).isEmpty()) {
                statusCreateDTO.setSlug(status.getKey());
                statusCreateDTO.setName(status.getValue());
                statusService.create(statusCreateDTO);
            }
        }
    }

}
