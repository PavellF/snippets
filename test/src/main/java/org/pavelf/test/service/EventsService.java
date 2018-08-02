package org.pavelf.test.service;

import java.util.List;
import java.util.stream.Stream;

import org.pavelf.test.controller.domain.EventDTO;
import org.pavelf.test.controller.domain.TopEvent;
import org.pavelf.test.controller.domain.Version;
import org.pavelf.test.domain.Event;

/**
 * Defines set of actions for managing {@link Event} entities.
 * @author Pavel F.
 * @since 1.0
 */
public interface EventsService {

	/**
	 * Selects all events that happened defined time ago.
	 * @param value number of minutes.
	 * @param version version of object.
	 * @return never {@code null}.
	 * @throws IllegalArgumentException if {@code null} passed or 
	 * value is negative.
	 */
	public Stream<EventDTO> getEventsForLastMinutes(int value, Version version);
	
	/**
	 * Selects all events that have not done yet.
	 * @param version version of object.
	 * @return never {@code null}.
	 */
	public Stream<EventDTO> getUndoneEvents(Version version);
	
	/**
	 * Selects top N events.
	 * @param n top value e.g. 5 is top five.
	 * @param version version of object.
	 * @return never {@code null}.
	 */
	public Stream<TopEvent> getTopNEvents(int n, Version version);
	
	/**
	 * Saves given events.
	 * @param events to save.
	 * @param version of object to save.
	 * @throws IllegalArgumentException if {@code null} passed.
	 */
	public void saveAll(List<EventDTO> events, Version version);
	
}
