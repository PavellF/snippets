package org.pavelf.test.service.impl;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import org.pavelf.test.service.CsvImporter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * Basic implementation of {@code CsvImporter}.
 * @author Pavel F.
 * @since 1.0
 */
@Service
public class CsvImporterImpl implements CsvImporter {

	@Override
	public <T> Collection<T> doImport(Resource csv, Class<T> mapTo) {
		try {
			
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader()
	        		.withColumnSeparator(';').withNullValue(null);
	        CsvMapper mapper = new CsvMapper();
	        File file = csv.getFile();
	        MappingIterator<T> readValues = 
	          mapper.readerFor(mapTo)
	          .with(bootstrapSchema)
	          .readValues(file);
	        
	        return readValues.readAll();
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return Collections.emptyList();
	    }
		
	}

	

}
