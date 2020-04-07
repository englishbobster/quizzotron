package stos.experiments.quizzotron.service;

import stos.experiments.quizzotron.api.ApiUser;

import java.util.List;
import java.util.Optional;

public interface UserService {

  List<ApiUser> getAllRegisteredUsers();

  Optional<ApiUser> registerUser(ApiUser user);

  Optional<ApiUser> getRegisteredUser(String name);
}
