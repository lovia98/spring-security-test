# spring-security-test

### Spring Security Configure에 대한 테스트 코드를 쉽게 작성 할 수 있도록 해주는 라이브러리

  1. pom.xml 에 dependency 추가  
    ```
      <dependency>
          <groupId>org.springframework.security</groupId>
          <artifactId>spring-security-test</artifactId>
          <scope>test</scope>
      </dependency>
    ```  
  2. security Configure  
    ```
      @Configuration
      public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

          @Override
          protected void configure(AuthenticationManagerBuilder auth) throws Exception {
              PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
              auth.inMemoryAuthentication()
                      .passwordEncoder(encoder)
                      .withUser("user").password(encoder.encode("password"))
                      .roles("USER").and()
                      .withUser("admin").password(encoder.encode("password"))
                      .roles("ADMIN");
          }

          @Override
          protected void configure(HttpSecurity http) throws Exception {
              http.authorizeRequests()
                      .antMatchers("/admin/**").hasAnyRole("ADMIN")
                      .antMatchers("/**").hasAnyRole("USER")
                      .and()
                          .formLogin();
          }
      }
    ```
