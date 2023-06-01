package com.backend.hygeia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.backend.hygeia.security.jwt.AuthEntryPointJwt;
import com.backend.hygeia.security.jwt.AuthTokenFilter;
import com.backend.hygeia.security.services.UserDetailsServiceImpl;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
// securedEnabled = true,
// jsr250Enabled = true,
prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
      return new AuthTokenFilter();
    }

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
        authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/login","/register","/forgetPasswd","/ForgetPassword","/api/auth/**").permitAll()
        .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
        .authenticated().and().csrf().disable().formLogin()
        .loginPage("/login").failureUrl("/login?error=true")
        .defaultSuccessUrl("/admin/home")
        .usernameParameter("email")
        .passwordParameter("password")
        .and().logout()
        .logoutUrl("/logout") // Çıkış URL'si
        .logoutSuccessUrl("/") // Çıkış başarılı olduğunda yönlendirilecek sayfa
        .invalidateHttpSession(true)
        .deleteCookies("JWT")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/").and().exceptionHandling()
        .accessDeniedPage("/access-denied");

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/fonts/**");
    }
    
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//           .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/fonts/**");
//    }
//    
//    private AccessDecisionManager accessDecisionManager() {
//        WebExpressionVoter expressionVoter = new WebExpressionVoter();
//        expressionVoter.setExpressionHandler(customWebSecurityExpressionHandler());
//
//        return new AffirmativeBased(Arrays.asList(expressionVoter));
//    }
//
//    private CustomDefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
//        return new CustomDefaultWebSecurityExpressionHandler();
//    }
}

