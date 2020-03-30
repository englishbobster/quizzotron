package stos.experiments.quizzotron.repo.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "user")
public class UserEntity {

  @Id
  @EqualsAndHashCode.Exclude
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")

  private Long id;

  @Column(name = "user_name")
  private String name;

  @Column(name = "password")
  private String password;
}
