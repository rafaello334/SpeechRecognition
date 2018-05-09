package pl.speechrecognition.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.speechrecognition.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>{

	public List<Event> findByEventDateBetween(Date start, Date end);
}
