package stos.experiments.quizzotron.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.repo.UserRepository;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuizController.class)
class QuizControllerTest {

  private static final ApiUser USER_1 = ApiUser.builder().id(1L).name("Stu").build();
  private static final ApiUser USER_2 = ApiUser.builder().id(2L).name("Anna").build();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    List<ApiUser> mockedUsersList = List.of(USER_1, USER_2);
    when(userRepository.getAllRegisteredUsers()).thenReturn(mockedUsersList);
  }

  @Test
  void reach_the_start_page() throws Exception {
    mockMvc.perform(get("/quizzotron"))
        .andExpect(view().name("quizzotron"))
        .andExpect(model().attributeExists("registeredUsers"))
        .andExpect(model().attribute("registeredUsers", Matchers.hasItems(USER_1,USER_2)));
  }
}
