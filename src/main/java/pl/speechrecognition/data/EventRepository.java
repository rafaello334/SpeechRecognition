package pl.speechrecognition.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.speechrecognition.model.Event;
import pl.speechrecognition.model.User;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>{

	public Event findByEventID(Long eventID);
	public List<Event> findByDateBetweenAndUserOrderByDate(Date start, Date end, User user);
}
