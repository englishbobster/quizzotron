package stos.experiments.quizzotron.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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

  public static final String NAME_1 = "Stu";
  public static final String NAME_2 = "Anna";
  private static final ApiUser USER_1 = ApiUser.builder().id(1L).name(NAME_1).build();
  private static final ApiUser USER_2 = ApiUser.builder().id(2L).name(NAME_2).build();
  public static final String TOO_LONG_PWD = "thispasswordisgoingtobetoolongIamguessing";
  public static final String USERNAME = "Mungo";
  public static final String PASSWORD = "secretpwd";
  public static final String TOO_SHORT_USERNAME = "X";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;
  public static final ApiUser UNSAVED_USER = ApiUser.builder().name(USERNAME).password(PASSWORD).build();
  public static final ApiUser SAVED_USER = ApiUser.builder().id(1L).name(USERNAME).password(PASSWORD).build();

  @Test
  void reach_the_start_page_with_registered_users_displayed() throws Exception {
    List<ApiUser> mockedUsersList = List.of(USER_1, USER_2);
    when(userService.getAllRegisteredUsers()).thenReturn(mockedUsersList);

    mockMvc.perform(get("/quizzotron"))
        .andExpect(view().name("quizzotron"))
        .andExpect(model().attributeExists("registeredUsers"))
        .andExpect(model().attribute("registeredUsers", Matchers.hasItems(USER_1, USER_2)));
  }

  @Test
  void registration_form_is_displayed_when_following_link() throws Exception {
    mockMvc.perform(get("/quizzotron/register"))
        .andExpect(view().name("register"));
  }

  @Test
  void login_form_is_displayed_when_following_link() throws Exception {
    mockMvc.perform(get("/quizzotron/login"))
        .andExpect(view().name("login"))
        .andExpect(model().attribute("user", is(equalTo(new ApiUser()))));
  }

  @Test
  void login_form_is_displayed_when_choosing_a_registered_user_from_main_page() throws Exception {
    when(userService.getRegisteredUser(eq(NAME_1))).thenReturn(Optional.of(USER_1));

    mockMvc.perform(get("/quizzotron/{user}", NAME_1))
        .andExpect(view().name("login"))
        .andExpect(model().attribute("user", is(equalTo(USER_1))));
  }

  @Test
  void process_the_registration_form() throws Exception {
    when(userService.registerUser(UNSAVED_USER)).thenReturn(Optional.of(SAVED_USER));

    mockMvc.perform(post("/quizzotron/register")
                        .param("name", USERNAME)
                        .param("password", PASSWORD))
        .andExpect(redirectedUrl("/quizzotron"));

    verify(userService, times(1)).registerUser(UNSAVED_USER);
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

  @Test
  void return_to_registration_if_user_name_is_already_taken() throws Exception {
    when(userService.getRegisteredUser(NAME_1)).thenReturn(Optional.of(USER_1));
    mockMvc.perform(post("/quizzotron/register")
                        .param("name", NAME_1)
                        .param("password", PASSWORD))
        .andExpect(view().name("register"));
  }
}
