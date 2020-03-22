package stos.experiments.quizzotron.repo;

import stos.experiments.quizzotron.api.ApiUser;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
  ApiUser user1 = ApiUser.builder().id(1L).name("Stu").build();
  ApiUser user2 = ApiUser.builder().id(2L).name("Anna").build();

  @Override
  public List<ApiUser> getAllRegisteredUsers() {
    return List.of(user1, user2);
  }
}
