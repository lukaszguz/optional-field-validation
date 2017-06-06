package pl.guz.domain.user;

import lombok.Data;
import pl.guz.domain.validation.OptionalSpecialField;
import pl.guz.domain.validation.SpecialField;

import javax.validation.constraints.Min;

@Data
class NewUser {

    @OptionalSpecialField
    private String firstName;

    @SpecialField
    private String lastName;

    @Min(18)
    private Integer age;

}
