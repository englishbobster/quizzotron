package stos.experiments.quizzotron.repo;

import stos.experiments.quizzotron.api.ApiUser;

import java.util.List;

public interface UserRepository {

  List<ApiUser> getAllRegisteredUsers();
}
