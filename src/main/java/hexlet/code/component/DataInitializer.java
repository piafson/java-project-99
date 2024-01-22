package hexlet.code.component;

import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskStatusService;
import lombok.AllArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private UserRepository userRepository;

    private final TaskStatusService statusService;

    private final TaskStatusRepository statusRepository;

    private final LabelRepository labelRepository;

    private final LabelService labelService;

    private final PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {

        var data = new User();
        data.setEmail("hexlet@example.com");
        data.setPasswordDigest(encoder.encode("qwerty"));
        userRepository.save(data);

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

        var labels = new ArrayList<>(List.of("bug", "feature"));
        LabelDTO labelData = new LabelDTO();
        labels.forEach(label -> {
            if (labelRepository.findByName(label).isEmpty()) {
                labelData.setName(JsonNullable.of(label));
                labelService.create(labelData);
            }
        });
    }
}
