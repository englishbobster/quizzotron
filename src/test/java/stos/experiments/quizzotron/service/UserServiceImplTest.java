package stos.experiments.quizzotron.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.repo.UserRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@DataJpaTest(excludeAutoConfiguration = FlywayAutoConfiguration.class,
    properties = {"spring.jpa.generate-ddl=true",
                  "spring.jpa.hibernate.ddl-auto=update",
                  "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
                  "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=TRUE"})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Import(BCryptPasswordEncoder.class)
class UserServiceImplTest {

  public static final String USER_NAME = "USERNAME";
  public static final String PASSWORD = "SECRETPWD";
  private static final ApiUser API_USER_1 = ApiUser.builder().name(USER_NAME).password(PASSWORD).build();

  @Autowired
  private UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  private static UserServiceImpl userService;

  @BeforeEach
  void beforeEach() {
    userService = new UserServiceImpl(userRepository, passwordEncoder);
  }

  @Test
  void return_a_user_when_it_has_been_saved_in_the_repo_with_an_index() {
    ApiUser savedUser = userService.registerUser(API_USER_1);
    assertThat(savedUser, is(equalTo(API_USER_1)));
    assertThat(savedUser.getId(), is(equalTo(1L)));
  }

  @Test
  void find_a_user_by_name_and_check_that_password_is_never_returned() {
    ApiUser savedUser = userService.registerUser(API_USER_1);
    ApiUser registeredUser = userService.getRegisteredUser(API_USER_1.getName());
    assertThat(registeredUser.getId(), is(equalTo(savedUser.getId())));
    assertThat(registeredUser, is(equalTo(savedUser)));
    assertThat(registeredUser.getPassword(), is(emptyString()));
  }

  @Test
  void return_all_the_registered_users() {
    userService.registerUser(API_USER_1);
    List<ApiUser> allRegisteredUsers = userService.getAllRegisteredUsers();
    assertThat(allRegisteredUsers.size(), is(equalTo(1)));
  }

  @Test
  void password_is_saved_encoded() {
    userService.registerUser(API_USER_1);
    List<ApiUser> allRegisteredUsers = userService.getAllRegisteredUsers();
    assertThat(allRegisteredUsers.get(0).getPassword(), is(not(equalTo(API_USER_1.getPassword()))));
  }
}

