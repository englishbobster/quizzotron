package stos.experiments.quizzotron.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.repo.UserRepository;
import stos.experiments.quizzotron.repo.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<ApiUser> getAllRegisteredUsers() {
    List<UserEntity> allUsers = userRepository.findAll();
    ModelMapper mapper = new ModelMapper();
    List<ApiUser> apiUsers = allUsers.stream().map(ue -> mapper.map(ue, ApiUser.class)).collect(Collectors.toList());
    return apiUsers;
  }

  @Override
  public ApiUser registerUser(ApiUser user) {
    ModelMapper mapper = new ModelMapper();
    UserEntity mappedEntity = mapper.map(user, UserEntity.class);
    UserEntity save = userRepository.save(mappedEntity);
    return user.withId(save.getId());
  }
}
