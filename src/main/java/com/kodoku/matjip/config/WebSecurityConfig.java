package com.kodoku.matjip.config;

import com.kodoku.matjip.config.contants.Profiles;
import com.kodoku.matjip.config.handler.CustomLoginFailureHandler;
import com.kodoku.matjip.config.handler.CustomLoginSuccessHandler;
import com.kodoku.matjip.config.handler.CustomLogoutSuccessHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${custom.remember-me.enckey}")
  String REMEMBER_ME_ENC_KEY;

  @Value("${custom.remember-me.remember-me-cookie-key}")
  String REMEMBER_ME_COOKIE_KEY;

  @Setter(onMethod_ = @Autowired)
  private Environment environment;

  @Setter(onMethod_ = @Autowired)
  private CustomAuthProvider customAuthProvider;

  @Setter(onMethod_ = @Autowired)
  UserDetailsService userDetailsService;

  // Lombok @Setter로 대체
  //    @Autowired
  //    public void setEnvironment(Environment environment) {
  //        this.environment = environment;
  //    }
  //    @Autowired
  //    public void setCustomAuthProvider(CustomAuthProvider customAuthProvider) {
  //        this.customAuthProvider = customAuthProvider;
  //    }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // ActiveProfiles에 stage 또는 product가 포함될 경우 https 강제 옵션을 사용한다.
    for (String profile : environment.getActiveProfiles()) {
      var lowerProf = profile.toLowerCase();
      if (lowerProf.equals(Profiles.STAGE) || lowerProf.equals(Profiles.PRODUCT)) {
        log.info("All CHANNELS SECURITY APPLIES HTTPS");
        http.requiresChannel().antMatchers("/**").requiresSecure();
        break;
      }
    }
    http.authorizeRequests()
        .antMatchers(
            "/",
            "/j_spring_security_check",
            "/user/register",
            "/html/*",
            "/assets/**",
            "/actuator/**")
        .permitAll()
        .antMatchers("/admin/**", "/html/admin/**")
        .hasAnyRole("ADMIN")
        .antMatchers("/user/**", "/html/user/**")
        .hasAnyRole("USER")
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
        .loginPage("/html/admin-login.html")
        .loginProcessingUrl("/j_spring_security_check")
        .usernameParameter("email")
        .passwordParameter("password")
        .successHandler(this.getLoginSuccessHandler())
        .failureHandler(this.getLoginFailureHandler())
        .and()
        .logout()
        .logoutUrl("/j_spring_security_logout")
        .permitAll()
        .logoutSuccessHandler(this.getLogoutSuccessHandler())
        .deleteCookies("JSESSIONID", REMEMBER_ME_COOKIE_KEY)
        .invalidateHttpSession(true)
        .and()
        .rememberMe()
        .key(REMEMBER_ME_ENC_KEY) // default: uuid random -> some times provider can't read these
        // value, please set constant value
        .rememberMeServices(this.getTokenBasedRememberMeServices())
        .tokenValiditySeconds(60 * 60 * 24 * 14)
        .and()
        .addFilterBefore(this.getCharEncFilter(), CsrfFilter.class)
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringAntMatchers("/j_spring_security_check");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(customAuthProvider);
  }

  @Bean
  public HttpSessionEventPublisher addSessionEventListener() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CharacterEncodingFilter getCharEncFilter() {
    CharacterEncodingFilter charEncFilter = new CharacterEncodingFilter();
    charEncFilter.setEncoding(StandardCharsets.UTF_8.name());
    charEncFilter.setForceEncoding(true);
    return charEncFilter;
  }

  @Bean
  public TokenBasedRememberMeServices getTokenBasedRememberMeServices() {
    TokenBasedRememberMeServices rememberMeServices =
        new TokenBasedRememberMeServices(REMEMBER_ME_ENC_KEY, userDetailsService);
    rememberMeServices.setAlwaysRemember(false);
    rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 14);
    rememberMeServices.setCookieName(REMEMBER_ME_COOKIE_KEY);
    return rememberMeServices;
  }

  @Bean
  public CustomAuthProvider addCustomAuthProvider() {
    return new CustomAuthProvider(userDetailsService);
  }

  private AuthenticationSuccessHandler getLoginSuccessHandler() {
    return new CustomLoginSuccessHandler();
  }

  private AuthenticationFailureHandler getLoginFailureHandler() {
    return new CustomLoginFailureHandler();
  }

  private LogoutSuccessHandler getLogoutSuccessHandler() {
    return new CustomLogoutSuccessHandler();
  }
}
