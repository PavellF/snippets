package org.pavelf.test.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Tuple;

import org.pavelf.test.controller.domain.EventDTO;
import org.pavelf.test.controller.domain.TopEvent;
import org.pavelf.test.controller.domain.Version;
import org.pavelf.test.domain.Event;
import org.pavelf.test.repository.EventsRepository;
import org.pavelf.test.service.EventsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basic implementation of {@link EventsService}.
 * @author Pavel F.
 * @since 1.0
 */
@Service
public class EventsServiceImpl implements EventsService {

	private EventsRepository eventsRepository;
	
	private final Function<Event, EventDTO> mapper = (Event e) -> {
		return EventDTO.builder()
				.withAuthResponse(e.getAuthResponse())
				.withFormId(e.getFormId())
				.withGroup(e.getGroup())
				.withId(e.getId().toString())
				.withOrganisationId(e.getOrganisationId())
				.withSessionKey(e.getSessionKey())
				.withSubtype(e.getSubtype())
				.withTime(e.getTime().getEpochSecond())
				.withType(e.getType())
				.withUrl(e.getUrl())
				.withYmdh(Date.from(e.getYmdh()))
				.withCode(e.getCode())
				.build();
	};
	
	public EventsServiceImpl(EventsRepository eventsRepository) {
		this.eventsRepository = eventsRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Stream<EventDTO> getEventsForLastMinutes(
			int value, Version version) {
		
		if (version == null || value < 1) {
			throw new IllegalArgumentException(
					"Null or negative is not allowed.");
		}
		
		return eventsRepository
				.selectForLastMinutes(Instant.now().minus(value, ChronoUnit.MINUTES))
				.stream()
				.map(mapper);
	}

	@Override
	@Transactional(readOnly = true)
	public Stream<EventDTO> getUndoneEvents(Version version) {
		if (version == null) {
			throw new IllegalArgumentException("Null is not allowed.");
		}
		
		return eventsRepository.selectUndoneForms().stream().map(mapper);
	}

	@Override
	@Transactional(readOnly = true)
	public Stream<TopEvent> getTopNEvents(int n, Version version) {
		if (version == null || n < 1) {
			throw new IllegalArgumentException(
					"Null or negative is not allowed.");
		}

		Pageable p = PageRequest.of(0, n);
		
		return eventsRepository.selectTop(p).stream().map((Tuple t) -> {
			String id = t.get(0, String.class);
			Long times = t.get(1, Long.class);
			
			return TopEvent.builder().withCount(times).withId(id).build();
		});
	}

	@Override
	@Transactional()
	public void saveAll(List<EventDTO> events, Version version) {
		if (version == null || events == null) {
			throw new IllegalArgumentException("Null is not allowed.");
		}	
		
		eventsRepository.saveAll((Iterable<Event>) events.stream()
				.map((EventDTO e) -> {
			Event toSave = new Event();
			
			try {
				toSave.setId(UUID.fromString(e.getId()));
			} catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				toSave.setId(UUID.randomUUID());
			}
			
			toSave.setAuthResponse(e.getAuthResponse());
			toSave.setFormId(e.getFormId());
			toSave.setGroup(e.getGroup());
			toSave.setOrganisationId(e.getOrganisationId());
			toSave.setSessionKey(e.getSessionKey());
			toSave.setSubtype(e.getSubtype());
			
			Long timeVal = e.getTime();
			
			if (timeVal == null) {
				toSave.setTime(null);
			} else {
				/*long millis = timeVal % 1000;
				long second = (timeVal / 1000) % 60;
				long minute = (timeVal / (1000 * 60)) % 60;
				long hour = (timeVal / (1000 * 60 * 60)) % 24;*/

				toSave.setTime(Instant.ofEpochSecond(timeVal));
			}
			
			toSave.setType(e.getType());
			toSave.setUrl(e.getUrl());
			toSave.setCode(e.getCode());
			
			Date time = e.getYmdh();
			
			if (time == null) {
				toSave.setYmdh(null);
			} else {
				toSave.setYmdh(time.toInstant());
			}
			
			return toSave;
		}).collect(Collectors.toList()));
		
	}

	

}
