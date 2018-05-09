package pl.speechrecognition.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.speechrecognition.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public boolean existsByUsername(String username);
}
