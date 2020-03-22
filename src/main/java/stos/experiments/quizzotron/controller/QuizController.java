package stos.experiments.quizzotron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizController {

  @GetMapping("/quizzotron")
  public String home() {
    return "quizzotron";
  }

}
