package hexlet.code.component;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        var data = new UserCreateDTO();
        data.setFirstName("Ivan");
        data.setLastName("Rurik");
        data.setEmail("hexlet@example.com");
        var hashedPassword = passwordEncoder.encode("qwerty");
        data.setPassword(hashedPassword);
        userService.create(data);
    }
}
