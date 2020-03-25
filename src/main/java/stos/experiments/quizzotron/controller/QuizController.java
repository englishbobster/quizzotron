package stos.experiments.quizzotron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/quizzotron")
public class QuizController {

  private final UserService userService;

  @Autowired
  public QuizController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String home(Model model) {
    model.addAttribute("registeredUsers", userService.getAllRegisteredUsers());
    return "quizzotron";
  }

  @GetMapping("/register")
  public String registration(Model model) {
    model.addAttribute("user", new ApiUser());
    return "register";
  }

  @PostMapping("/register")
  public String register(@Valid ApiUser user, BindingResult result, Model model) {
    model.addAttribute("user", user);
    if (result.hasErrors()) {
      return "register";
    }
    userService.registerUser(user);
    model.addAttribute("registeredUsers", userService.getAllRegisteredUsers());
    return "redirect:/quizzotron";
  }
}
