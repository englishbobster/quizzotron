package stos.experiments.quizzotron.service;

import org.springframework.stereotype.Component;
import stos.experiments.quizzotron.api.ApiUser;

import java.util.Collections;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

  public UserServiceImpl() {

  }

  @Override
  public List<ApiUser> getAllRegisteredUsers() {
    return Collections.emptyList();
  }

  @Override
  public ApiUser registerUser(ApiUser user) {
    return null;
  }
}
