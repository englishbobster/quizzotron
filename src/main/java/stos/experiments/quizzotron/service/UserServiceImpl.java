package stos.experiments.quizzotron.service;

import org.springframework.stereotype.Component;
import stos.experiments.quizzotron.api.ApiUser;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

  private List<ApiUser> registeredUsers;
  private static Long idCtr;

  public UserServiceImpl() {
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
