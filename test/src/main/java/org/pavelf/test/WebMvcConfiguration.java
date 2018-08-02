package org.pavelf.test;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.pavelf.test.controller.domain.EventDTO;
import org.pavelf.test.controller.domain.VersionImpl;
import org.pavelf.test.converters.VersionConverter;
import org.pavelf.test.domain.Event;
import org.pavelf.test.repository.EventsRepository;
import org.pavelf.test.service.CsvImporter;
import org.pavelf.test.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author Pavel F.
 * @since 1.0
 * */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	private CsvImporter csvImporter;
	private ResourceLoader resourceLoader;
	private EventsService eventsService;
	
	@Autowired
	public WebMvcConfiguration(CsvImporter csvImporter,
			ResourceLoader resourceLoader, EventsService eventsService) {
		this.csvImporter = csvImporter;
		this.resourceLoader = resourceLoader;
		this.eventsService = eventsService;
	}

	@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new VersionConverter());
    }
	
	@PostConstruct
    private void setupData() {
		final String path = "file:"+System.getProperty("user.dir")+"/test_case.csv";
        Resource csvFile = this.resourceLoader.getResource(path);
        final Collection<EventDTO> events = csvImporter.doImport(csvFile, EventDTO.class);
        this.eventsService.saveAll((List<EventDTO>) events, VersionImpl.getBy("1.0"));
    }
}
