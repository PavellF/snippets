package org.pavelf.test.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.Tuple;

import org.pavelf.test.domain.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Defines set of methods to interact with data source of events.
 * @author Pavel F.
 * @since 1.0
 */
public interface EventsRepository extends JpaRepository<Event, UUID> {

	//SELECT e.formid, COUNT(e.formid) FROM events AS e GROUP BY e.formid HAVING e.formId != ''
	//ORDER BY COUNT(e.formid) DESC LIMIT 10
	@Query("SELECT e.formId, COUNT(e.formId) FROM Event AS e "
			+ "GROUP BY e.formId HAVING e.formId != '' ORDER BY COUNT(e.formId) DESC")
	public List<Tuple> selectTop(Pageable pageable);

	//SELECT * FROM EVENTS AS e WHERE e.formid != '' AND e.subtype NOT IN ('send', '', 'success', 'result')
	@Query("SELECT e FROM Event AS e WHERE e.formId != '' AND e.time > ?1")
	public List<Event> selectForLastMinutes(Instant value);
	
	//SELECT * FROM EVENTS AS e WHERE e.formid != '' AND e.ts > DATEADD(MINUTE, -60, NOW())	
	@Query("SELECT e FROM Event AS e WHERE e.formId != '' AND e.subtype NOT IN ('send', '', 'success', 'result')")
	public List<Event> selectUndoneForms();
	
}
