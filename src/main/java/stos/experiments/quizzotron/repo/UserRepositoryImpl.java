package stos.experiments.quizzotron.repo;

import org.springframework.stereotype.Component;
import stos.experiments.quizzotron.api.ApiUser;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository {

  private List<ApiUser> registeredUsers;
  private static Long idCtr;

  public UserRepositoryImpl() {
    this.registeredUsers = new ArrayList<>();
    idCtr = 0L;
  }

  @Override
  public List<ApiUser> getAllRegisteredUsers() {
    return registeredUsers;
  }

  @Override
  public ApiUser registerUser(ApiUser user) {
    user.setId(idCtr);
    registeredUsers.add(user);
    idCtr++;
    return user;
  }
}
