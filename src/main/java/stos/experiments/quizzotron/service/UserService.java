package stos.experiments.quizzotron.service;

import stos.experiments.quizzotron.api.ApiUser;

import java.util.List;

public interface UserService {

  List<ApiUser> getAllRegisteredUsers();

  ApiUser registerUser(ApiUser user);

  ApiUser getRegisteredUser(String name);
}
