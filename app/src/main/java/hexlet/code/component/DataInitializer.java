package hexlet.code.component;

import hexlet.code.dto.LabelCreateDTO;
import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.UserCreateDTO;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskStatusService;
import hexlet.code.service.UserService;
import io.sentry.Sentry;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserService userService;

    private final TaskStatusService statusService;

    private final TaskStatusRepository statusRepository;

    private final LabelRepository labelRepository;

    private final LabelService labelService;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }

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
        statuses.forEach((key, value) -> {
            if (statusRepository.findBySlug(key).isEmpty()) {
                statusCreateDTO.setSlug(key);
                statusCreateDTO.setName(value);
                statusService.create(statusCreateDTO);
            }
        });

        List<String> labels = new ArrayList<>(List.of("bug", "feature"));
        LabelCreateDTO labelData = new LabelCreateDTO();
        labels.forEach(label -> {
            if (labelRepository.findByName(label).isEmpty()) {
                labelData.setName(label);
                labelService.create(labelData);
            }
        });
    }
}
