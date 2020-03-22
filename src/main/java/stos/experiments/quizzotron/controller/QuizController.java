package stos.experiments.quizzotron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import stos.experiments.quizzotron.repo.UserRepository;

@Controller
public class QuizController {

  private final UserRepository userRepository;

  @Autowired
  public QuizController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/quizzotron")
  public String home(Model model) {
    model.addAttribute("registeredUsers", userRepository.getAllRegisteredUsers());
    return "quizzotron";
  }

}
