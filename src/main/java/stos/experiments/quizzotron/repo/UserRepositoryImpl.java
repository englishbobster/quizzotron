package stos.experiments.quizzotron.repo;

import org.springframework.stereotype.Component;
import stos.experiments.quizzotron.api.ApiUser;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository {

  private List<ApiUser> registeredUsers;

  public UserRepositoryImpl() {
    this.registeredUsers = new ArrayList<>();
  }

  @Override
  public List<ApiUser> getAllRegisteredUsers() {
    return registeredUsers;
  }
}
