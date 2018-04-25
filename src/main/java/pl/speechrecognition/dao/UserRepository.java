package pl.speechrecognition.dao;

import org.springframework.data.repository.CrudRepository;

import pl.speechrecognition.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
