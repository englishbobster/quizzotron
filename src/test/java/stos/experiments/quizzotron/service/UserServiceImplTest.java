package stos.experiments.quizzotron.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.repo.UserRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@DataJpaTest(excludeAutoConfiguration = FlywayAutoConfiguration.class,
    properties = {"spring.jpa.generate-ddl=true",
                  "spring.jpa.hibernate.ddl-auto=update",
                  "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
                  "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=TRUE"})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class UserServiceImplTest {

  public static final String USER_NAME = "USERNAME";
  public static final String PASSWORD = "SECRETPWD";
  private static final ApiUser API_USER_1 = ApiUser.builder().name(USER_NAME).password(PASSWORD).build();

  @Autowired
  private UserRepository userRepository;

  private static UserServiceImpl userService;

  @BeforeEach
  void beforeEach() {
    userService = new UserServiceImpl(userRepository);
  }

  @Test
  void return_a_user_when_it_has_been_saved_in_the_repo_with_an_index() {
    ApiUser savedUser = userService.registerUser(API_USER_1);
    assertThat(savedUser, is(equalTo(API_USER_1)));
    assertThat(savedUser.getId(), is(equalTo(1L)));
  }

  @Test
  void return_all_the_registered_users() {
    userService.registerUser(API_USER_1);
    List<ApiUser> allRegisteredUsers = userService.getAllRegisteredUsers();
    assertThat(allRegisteredUsers.size(), is(equalTo(1)));
  }
}

