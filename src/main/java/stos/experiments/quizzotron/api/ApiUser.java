package stos.experiments.quizzotron.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiUser {
  private Long id;
  private String name;
}
