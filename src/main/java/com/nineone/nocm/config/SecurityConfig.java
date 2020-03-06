package com.nineone.nocm.config;

import com.nineone.nocm.domain.enums.Role;
import com.nineone.nocm.service.CustomOAuthUserService;
import com.nineone.nocm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final HttpSession httpSession;
    private final CustomOAuthUserService customOAuthUserService;
    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");

        filter.setForceEncoding(true);
        http
                .addFilterBefore(filter, CsrfFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**","/img/**", "/login/**", "/oauth2/**","/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/main")
                .failureForwardUrl("/")
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/main")
                .loginPage("/")
                .userInfoEndpoint().userService(customOAuthUserService);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
