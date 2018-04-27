package pl.speechrecognition.data;

import org.springframework.data.repository.CrudRepository;

import pl.speechrecognition.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public boolean existsByUsername(String username);
}
