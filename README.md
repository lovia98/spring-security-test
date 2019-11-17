# spring-security-test

### Spring Security Configure에 대한 테스트 코드를 쉽게 작성 할 수 있도록 해주는 라이브러리

* pom.xml 에 dependency 추가
```xml
  <dependency>
    <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-test</artifactId>
      <scope>test</scope>
  </dependency>
```

* Security Configure example
  
```java
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
    
* Test Code
  * user, anonymous static 메소드 이용
```java
    import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
    import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    class MyControllerTest {
    
        private final String LOGIN_PAGE_URI = "http://localhost/login";
    
        @Autowired
        private MockMvc mvc;

        @Test
        public void given_requestMyPage_withAccountUser_expect_viewNameIsMyPage() throws Exception {
            mvc.perform(get("/myPage").with(user("user")))  //user계정으로 로그인 한 mockUser
                    .andExpect(view().name("myPage"));
        }
    
        @Test
        public void given_requestRootPage_expect_redirectLoginPage() throws Exception {
            mvc.perform(get("/myPage").with(anonymous()))   //anonymous로 로그인 한 mockUser
                    .andDo(print())
                    .andExpect(redirectedUrl(LOGIN_PAGE_URI));
        }
    }
```
  * mockUser 어노테이션 이용
```java
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    class MyControllerTest {
    
        private final String LOGIN_PAGE_URI = "http://localhost/login";
    
        @Autowired
        private MockMvc mvc;

        @Test
        @WithMockUser("user")
        public void given_requestMyPage_withAccountUserAnnotation_expect_viewNameIsMyPage() throws Exception {
            mvc.perform(get("/myPage"))
                    .andExpect(view().name("myPage"));
        }
    
        @Test
        @WithMockUser(username = "mockUser", roles = "USER")
        public void given_requestMyPage_withRoleUSER_expect_viewNameIsMyPage() throws Exception {
            mvc.perform(get("/myPage"))
                    .andExpect(view().name("myPage"));
        }
    
        @Test
        @WithMockUser(username = "mockUser", roles = "ADMIN")
        public void given_requestAdminPage_withRoleADMIN_expect_viewNameIsMyPage() throws Exception {
            mvc.perform(get("/admin/myPage"))
                    .andExpect(view().name("myPage"));
        }
    
        @Test
        @WithMockUser(username = "mockUser", roles = {"USER", "ADMIN"})
        public void given_requestAdminPage_withRoleBoth_expect_viewNameIsMyPage() throws Exception {
            mvc.perform(get("/admin/myPage"))
                    .andExpect(view().name("myPage"));
        }

        @Test
        @WithAnonymousUser
        public void given_requestRootPageAnnotaion_expect_redirectLoginPage() throws Exception {
            mvc.perform(get("/myPage"))
                    .andDo(print())
        }

    }
```
  * 어노테이션을 정의해서 코드를 좀 더 깔끔하게 보이게 할 수 있다.
```java
    //어노테이션
    @Retention(RetentionPolicy.RUNTIME)
    @WithMockUser(username = "mockUser", roles = "USER")
    public @interface WithRoleUser {
    }

    //테스트 코드
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    class MyControllerTest {
    
        private final String LOGIN_PAGE_URI = "http://localhost/login";
    
        @Autowired
        private MockMvc mvc;

        @Test
        @WithRoleUser
        public void given_requestMyPage_withAccountUserAnnotation_expect_viewNameIsMyPage() throws Exception {
            mvc.perform(get("/myPage"))
                    .andExpect(view().name("myPage"));
        }
    }
```
  * 테스트 코드 전체에 하나의 롤만 지정해서 테스트 하고자 한다면, 클래스에 적용한다.
```java
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    @WithRoleUser //<-- 전체 test 메소드에 적용
    public class XssRequestTest2 {

        //...

    }
```