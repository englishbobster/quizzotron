package stos.experiments.quizzotron.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stos.experiments.quizzotron.repo.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { }
