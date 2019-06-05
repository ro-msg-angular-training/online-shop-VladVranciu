package ro.msg.learning.shop.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

}
