package pl.speechrecognition.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.speechrecognition.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public boolean existsByUsername(String username);
	public User findByUsername(String username);
}
