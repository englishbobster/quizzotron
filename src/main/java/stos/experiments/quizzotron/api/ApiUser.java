package stos.experiments.quizzotron.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ApiUser {
  @EqualsAndHashCode.Exclude
  @With
  private Long id;
  @NotNull
  @Size(min=3, max=32)
  private String name;
  @NotNull
  @Size(min=6, max=32)
  private String password;
}
