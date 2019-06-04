package ro.msg.learning.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.CustomUser;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.UserEntity;
import ro.msg.learning.shop.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomUserService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer login(String username, String password){
        return customerRepository.login(username,password);
    }

    @Override
    public CustomUser loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer customer=customerRepository.login(s);
        UserEntity userEntity=new UserEntity();
        if(customer!=null){
            userEntity.setPassword(customer.getPassword());
            userEntity.setUsername(customer.getUsername());
            List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SYSADMIN"));
            userEntity.setGrantedAuthorities(grantedAuthorities);
            return new CustomUser(userEntity);
        }else
            return null;
    }
}
