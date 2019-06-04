package ro.msg.learning.shop.model;

        import lombok.Data;
        import lombok.NoArgsConstructor;
        import org.springframework.security.core.GrantedAuthority;

        import java.util.ArrayList;
        import java.util.List;

@Data
@NoArgsConstructor
public class UserEntity {
    private String username;
    private String password;
    private List<GrantedAuthority> grantedAuthorities;
}
