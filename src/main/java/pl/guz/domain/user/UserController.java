package pl.guz.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/users")
@Slf4j
class UserController {

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    void createUser(@RequestBody @Valid NewUser newUser) {
        log.info("Created new user: {}", newUser);
    }
}
