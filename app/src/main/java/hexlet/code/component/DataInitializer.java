package hexlet.code.component;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        if (userRepository.findByEmail("hexlet@example.com").isEmpty()) {
            var data = new UserCreateDTO();
            data.setFirstName("Ivan");
            data.setLastName("Rurik");
            data.setEmail("hexlet@example.com");
            data.setPassword("qwerty");
            userService.create(data);
        }
    }
}
