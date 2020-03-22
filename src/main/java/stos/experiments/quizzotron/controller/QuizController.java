package stos.experiments.quizzotron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import stos.experiments.quizzotron.repo.UserRepository;
import stos.experiments.quizzotron.repo.UserRepositoryImpl;

@Controller
public class QuizController {

  private final UserRepository userRepository;

  public QuizController() {
    userRepository = new UserRepositoryImpl();
  }

  @GetMapping("/quizzotron")
  public String home(Model model) {
    model.addAttribute("registeredUsers", userRepository.getAllRegisteredUsers());
    return "quizzotron";
  }

}
