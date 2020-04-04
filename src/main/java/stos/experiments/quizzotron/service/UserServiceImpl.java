package stos.experiments.quizzotron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stos.experiments.quizzotron.api.ApiUser;
import stos.experiments.quizzotron.repo.UserRepository;
import stos.experiments.quizzotron.repo.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<ApiUser> getAllRegisteredUsers() {
    List<UserEntity> allUsers = userRepository.findAll();
    List<ApiUser> apiUsers = allUsers.stream().map(UserServiceImpl::apiUser).collect(Collectors.toList());
    return apiUsers;
  }

  @Override
  public ApiUser registerUser(ApiUser user) {
    UserEntity userEntity = userEntity(user);
    UserEntity save = userRepository.save(userEntity);
    return user.withId(save.getId());
  }

  @Override
  public ApiUser getRegisteredUser(String name) {
    UserEntity byName = userRepository.findByName(name);
    return apiUser(byName);
  }

  private static ApiUser apiUser(UserEntity entity) {
    return ApiUser.builder()
               .id(entity.getId())
               .name(entity.getName())
               .password("")
               .build();
  }

  private UserEntity userEntity(ApiUser user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setName(user.getName());
    userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    return userEntity;
  }

}
