package stos.experiments.quizzotron.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ApiUser {
  @EqualsAndHashCode.Exclude
  private Long id;

  private String name;
}
