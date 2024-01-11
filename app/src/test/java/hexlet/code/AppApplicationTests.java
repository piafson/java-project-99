package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private Faker faker;

    private User testUser;

    @BeforeEach
    public void create() {
        testUser = Instancio.of(User.class)
                .ignore(Select.field("id"))
                .supply(Select.field("firstName"), () -> faker.name().firstName())
                .supply(Select.field("lastName"), () -> faker.name().lastName())
                .supply(Select.field("email"), () -> faker.internet().emailAddress())
                .supply(Select.field("password"), () -> faker.internet().password())
                .create();
    }

    @Test
    public void testIndex() throws Exception {
        userRepository.save(testUser);
        var result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().isNotEmpty();
    }

    @Test
    public void testShow() throws Exception {
        userRepository.save(testUser);

        var result = mockMvc.perform(get("/api/users/" + testUser.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("firstName").isEqualTo(testUser.getFirstName()),
                a -> a.node("lastName").isEqualTo(testUser.getLastName()),
                a -> a.node("email").isEqualTo(testUser.getEmail())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var request = post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testUser));

        mockMvc.perform(request).andExpect(status().isCreated());

        var user = userRepository.findByEmail(testUser.getEmail()).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(testUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(testUser.getLastName());
        assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {
        userRepository.save(testUser);

        Map<String, String> data = new HashMap<>(Map.of("firstName", "Ivan", "email", "ttt@ya.ru"));
        var request = put("/api/users/" + testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isOk());

        var user = userRepository.findByEmail("ttt@ya.ru").orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("Ivan");
        assertThat(user.getLastName()).isEqualTo(testUser.getLastName());
    }

    @Test
    public void testDelete() throws Exception {
        userRepository.save(testUser);

        mockMvc.perform(delete("/api/users/" + testUser.getId()))
                .andExpect(status().isNoContent());

        var user = userRepository.findById(testUser.getId()).orElse(null);
        assertThat(user).isNull();
    }
}
