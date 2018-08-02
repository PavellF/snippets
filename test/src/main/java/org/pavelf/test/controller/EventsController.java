package org.pavelf.test.controller;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.pavelf.test.controller.domain.EventDTO;
import org.pavelf.test.controller.domain.TopEvent;
import org.pavelf.test.controller.domain.Version;
import org.pavelf.test.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes some endpoints for {@link Event}.
 * @author Pavel F.
 * @since 1.0
 */
@RestController
public class EventsController {

	private EventsService eventsService;
	private ResourceLoader resourceLoader;
	
	/**
	 * Describes application specific Accept header. 
	 * For example application/vnd.yourapp whereas full header is
	 * application/vnd.yourapp.domain+json;version=1.0
	 * */
	public static final String JSON = "application/vnd.test.event+json";
	
	@Autowired
	public EventsController(EventsService eventsService,
			ResourceLoader resourceLoader) {
		this.eventsService = eventsService;
		this.resourceLoader = resourceLoader;
	}

	@GetMapping(produces = { JSON }, path = "/events/top/{n}")	
	public ResponseEntity<List<TopEvent>> getTopNEvents(
			@PathVariable("n") int n, 
			@RequestHeader(HttpHeaders.ACCEPT) Version version) {
		
		if (n < 1) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok(eventsService
				.getTopNEvents(n, version)
				.collect(Collectors.toList()));
		
	}
	
	@GetMapping(produces = { JSON }, path = "/events/undone")	
	public ResponseEntity<List<EventDTO>> getUndoneEvents(
			@RequestHeader(HttpHeaders.ACCEPT) Version version) {
		
		return ResponseEntity.ok(eventsService.getUndoneEvents(version)
				.collect(Collectors.toList()));
		
	}

	@GetMapping(produces = { JSON }, path = "/events/last")	
	public ResponseEntity<List<EventDTO>> getForLastTime(
			@RequestParam(name="forMinutes", defaultValue="60") int minutes, 
			@RequestHeader(HttpHeaders.ACCEPT) Version version) {
		
		if (minutes < 1) {
			return ResponseEntity.badRequest().build();
		}
		
		List<EventDTO> list = eventsService
				.getEventsForLastMinutes(minutes, version)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(list);
		
	}
	
	@GetMapping(produces = { "text/html" }, path = "/")	
	public String getDemoPage() throws IOException, URISyntaxException {
		
		Path path = Paths.get(getClass().getResource("/templates/index.html").toURI());
		return Files.readAllLines(path).stream().reduce("", (acc, val) -> {
			return acc.concat("\n").concat(val);
		});
		
	}
	
}
