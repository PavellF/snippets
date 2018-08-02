package org.pavelf.test.service;

import java.util.Collection;
import org.springframework.core.io.Resource;

/**
 * Imports generic CSV file to memory.
 * @author Pavel F.
 * @since 1.0
 */
public interface CsvImporter {

	/**
	 * Performs import.
	 * @param pathToCsv file on disk.
	 * @param mapper map file to.
	 * @return never {@code null}.
	 * @throws IllegalArgumentException if {@code null} passed.
	 */
	public <T> Collection<T> doImport(Resource csv, Class<T> mapTo);
	
}
