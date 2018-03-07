package com.rgrzywacz.starter.security;


import com.rgrzywacz.starter.user.User;
import com.rgrzywacz.starter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class GlobalAuthenticationCustomConfigurer extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(login);
                if (user != null) {
                    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList(user.getAuthority()));
                } else {
                    throw new UsernameNotFoundException("Could not find the user '" + login + "'");
                }
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
