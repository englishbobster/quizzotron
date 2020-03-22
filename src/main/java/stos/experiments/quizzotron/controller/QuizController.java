package stos.experiments.quizzotron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.repo.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/quizzotron")
public class QuizController {

  private final UserRepository userRepository;

  @Autowired
  public QuizController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public String home(Model model) {
    model.addAttribute("registeredUsers", userRepository.getAllRegisteredUsers());
    return "quizzotron";
  }

  @GetMapping("/register")
  public String registration() {
    return "register";
  }

  @PostMapping("/register")
  public String register(@Valid ApiUser user, Errors errors) {
    if (errors.hasErrors()) {
      return "register";
    }
    userRepository.registerUser(user);
    return "redirect:/quizzotron";
  }
}
