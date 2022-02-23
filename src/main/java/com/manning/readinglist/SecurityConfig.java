package com.manning.readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;


@Configuration
@EnableWebSecurity
@Profile("production")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ReaderRepository readerRepository;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/").access("hasRole('READER')")//要求登录者有READER角色
            .antMatchers("/**").permitAll()
            .and()
            .formLogin().loginPage("/login").permitAll()//设置登录表单的路径
        .failureUrl("/login?error=true");
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder());
    //自定义认证用户获取方式（数据库获取），认证成功后会将UserDetails赋给Authentication的principal，再把Authentication保存到SecurityContext中
    auth.userDetailsService(new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Optional<Reader> readerOpt = readerRepository.findById(username);
          if (readerOpt.isPresent()) {
            UserDetails userDetails = readerOpt.get();
            if (userDetails != null) {
              return userDetails;
            }
          }
          throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
      }).passwordEncoder(new BCryptPasswordEncoder());
  }

  public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("123456"));
  }

}
