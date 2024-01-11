package hexlet.code.component;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        String firstName = "Ivan";
        String lastName = "Rurik";
        String email = "hexlet@example.com";
        String password = "qwerty";

        UserCreateDTO data = new UserCreateDTO();
        data.setFirstName(firstName);
        data.setLastName(lastName);
        data.setEmail(email);
        data.setPassword(password);

        userService.create(data);
    }

}
