package stos.experiments.quizzotron.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.repo.UserRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuizController.class)
class QuizControllerTest {

  private static final ApiUser USER_1 = ApiUser.builder().id(1L).name("Stu").build();
  private static final ApiUser USER_2 = ApiUser.builder().id(2L).name("Anna").build();
  public static final String TOO_LONG_PWD = "thispasswordisgoingtobetoolongIamguessing";
  public static final String USERNAME = "Mungo";
  public static final String PASSWORD = "secretpwd";
  public static final String TOO_SHORT_USERNAME = "X";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @Test
  void reach_the_start_page_with_registered_users_displayed() throws Exception {
    List<ApiUser> mockedUsersList = List.of(USER_1, USER_2);
    when(userRepository.getAllRegisteredUsers()).thenReturn(mockedUsersList);

    mockMvc.perform(get("/quizzotron"))
        .andExpect(view().name("quizzotron"))
        .andExpect(model().attributeExists("registeredUsers"))
        .andExpect(model().attribute("registeredUsers", Matchers.hasItems(USER_1, USER_2)));
  }

  @Test
  void registration_form_is_displayed() throws Exception {
    mockMvc.perform(get("/quizzotron/register"))
        .andExpect(view().name("register"));
  }

  @Test
  void process_the_registration_form() throws Exception {
    ApiUser unsaved = ApiUser.builder().name(USERNAME).password(PASSWORD).build();
    ApiUser saved = ApiUser.builder().id(1L).name(USERNAME).password(PASSWORD).build();

    when(userRepository.registerUser(unsaved)).thenReturn(saved);

    mockMvc.perform(post("/quizzotron/register")
                        .param("name", USERNAME)
                        .param("password", PASSWORD))
        .andExpect(redirectedUrl("/quizzotron"));

    verify(userRepository, times(1)).registerUser(unsaved);
  }

  @Test
  void return_to_registration_if_user_name_not_validated() throws Exception {
    mockMvc.perform(post("/quizzotron/register")
                        .param("name", TOO_SHORT_USERNAME)
                        .param("password", PASSWORD))
        .andExpect(view().name("register"));

  }

  @Test
  void return_to_registration_if_password_not_validated() throws Exception {
    mockMvc.perform(post("/quizzotron/register")
                        .param("name", USERNAME)
                        .param("password", TOO_LONG_PWD))
        .andExpect(view().name("register"));

  }
}
