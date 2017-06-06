package pl.guz.domain.user;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.guz.domain.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_throw_validation_exceptions() throws Exception {
        // given
        String first_name_in_lower_case = "{ \"firstName\": \"jan\", \"lastName\": \"KOWA\",\"age\": 18}";
        String last_name_in_lower_case = "{ \"firstName\": \"JAN\", \"lastName\": \"kowa\",\"age\": 18}";
        String ageIsLessAs18 = "{ \"firstName\": \"JAN\", \"lastName\": \"KOWA\",\"age\": 17}";

        List<String> newUsers = Arrays.asList(
                first_name_in_lower_case,
                last_name_in_lower_case,
                ageIsLessAs18);

        // expect
        newUsers.forEach(this::badRequestMatcher);
    }

    @Test
    public void should_send_good_data() throws Exception {
        // given
        String correct_user = "{ \"firstName\": \"JAN\", \"lastName\": \"KOWA\",\"age\": 18}";
        String correct_user_without_first_name = "{ \"lastName\": \"KOWA\",\"age\": 18}";

        List<String> newUsers = Arrays.asList(
                correct_user,
                correct_user_without_first_name);

        // expect
        newUsers.forEach(this::notContentMatcher);
    }

    @SneakyThrows
    private void badRequestMatcher(String body) {
        mockMvc.perform(post("/users")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    private void notContentMatcher(String body) {
        mockMvc.perform(post("/users")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }
}