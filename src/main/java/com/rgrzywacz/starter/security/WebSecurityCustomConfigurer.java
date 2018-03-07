package com.rgrzywacz.starter.security;


import com.rgrzywacz.starter.security.filter.CsrfGrantingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;

import static com.rgrzywacz.starter.security.filter.CsrfGrantingFilter.X_XSRF_TOKEN;

@Configuration
@EnableWebSecurity
@EnableSpringHttpSession
public class WebSecurityCustomConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/api/**")
                .hasAuthority("USER");
        http.csrf().disable().httpBasic().and()
                .addFilterAfter(csrfFilter("/login"), FilterSecurityInterceptor.class)
                .addFilterAfter(new CsrfGrantingFilter(), CsrfFilter.class);
        http.cors();

    }

    private Filter csrfFilter(String pattern) {
        CsrfFilter csrfFilter = new CsrfFilter(csrfTokenRepository());
        csrfFilter.setRequireCsrfProtectionMatcher(
                new NegatedRequestMatcher(new AntPathRequestMatcher(pattern)));
        return csrfFilter;
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(X_XSRF_TOKEN);
        return repository;
    }

    @Bean
    public SessionRepository sessionRepository() {
        return new MapSessionRepository();
    }

    @Bean
    public HeaderHttpSessionStrategy sessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

