package com.kodoku.matjip.config;

import com.kodoku.matjip.config.enums.ActiveProfiles;
import com.kodoku.matjip.config.handler.LoginFailureHandler;
import com.kodoku.matjip.config.handler.LoginSuccessHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Environment environment;
    private CustomAuthProvider customAuthProvider;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setCustomAuthProvider(CustomAuthProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ActiveProfiles에 stage가 포함될 경우 https 강제 옵션을 사용한다.
        for (String profile : environment.getActiveProfiles()) {
            if (profile.toUpperCase().equals(ActiveProfiles.STAGE.name())) {
                http
                    .requiresChannel()
                    .antMatchers("/**")
                    .requiresSecure();
                break;
            }
        }

        http
            .authorizeRequests()
                .antMatchers("/j_spring_security_check", "/user/register", "/html/*", "/assets/**", "/actuator/**").permitAll()
                .antMatchers("/admin/**", "/html/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**", "/html/user/**").hasAnyRole("USER")
                .anyRequest()
                .authenticated()
            .and()
                .headers()
                .frameOptions()
                .disable()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/html/login.html"))
            .and()
                .formLogin()
                .loginPage("/html/login.html")
                .loginPage("/admin-login.html")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(getLoginSuccessHandler())
                .failureHandler(getLoginFailureHandler())
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "J_REMEMBER_ME")
                .invalidateHttpSession(true)
            .and()
                .addFilterBefore(getCharEncFilter(), CsrfFilter.class)
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringAntMatchers("/j_spring_security_check");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthProvider);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CharacterEncodingFilter getCharEncFilter() {
        return new CharacterEncodingFilter();
    }

    @Bean
    public LoginSuccessHandler getLoginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler getLoginFailureHandler() {
        return new LoginFailureHandler();
    }
}
