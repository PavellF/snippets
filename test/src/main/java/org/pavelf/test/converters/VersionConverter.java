package org.pavelf.test.converters;

import org.pavelf.test.controller.domain.Version;
import org.pavelf.test.controller.domain.VersionImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.InvalidMediaTypeException;

/**
 * Converter for {@link Version}.
 * @author Pavel F.
 * @since 1.0
 * */
public class VersionConverter implements Converter<String, Version> {

	@Override
	public Version convert(String source) {
		
		Version version = null;
		
		try {
			//try to derive version from header:
			version = VersionImpl.valueOf(source);
			
		} catch (InvalidMediaTypeException imte) {
			//trying to derive from plain string value
			version = VersionImpl.getBy(source);
			
		}
		
		if (version == null) {
			throw new NullPointerException("Could not resolve version.");
		}
		
		return version;
	}

	
}
