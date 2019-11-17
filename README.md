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
    
  2. Security Configure example
    ```  
      @Configuration
      public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

          //인메모리에 "USER"롤을 가진 계정 "user", "ADMIN"롤을 가진 계정 "admin"을 등록
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

          //모든 url은 "USER"롤 이상만 접근 가능하며, "/admin/**"은 "ADMIN" 권한만 접근 가능
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
    
  3. 
