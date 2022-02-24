package de.bsi.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.bsi.minesweeper.model.Position;

/**
 * Demo class to talk about:
 * - Exceptions instead of Error codes
 * - Boilerplate code around checked Exceptions
 */
public class ErrorHandling {
	
	private boolean clientMethod() {
		int result = storeFile(null);
		if (result == 200)
			return true;
		if (result == 400)
			return false;
		if (result == 500)
			return false;
		return false;
	}
	
	/**
	 * Stores a file somewhere
	 * @param file
	 * @return 200 in case of success; 400 in case of invalid file; 500 in case of full disc. 
	 */
	private int storeFile(Object file) {
		// Storing file logic might:
		// return 400;
		// return 500;
		return 200;
	}
	
	private boolean anyError = true;
	
	private void storeFile2(Object file) {
		// storing file logic...
		if (anyError)
			throw new RuntimeException("Disc was full");
	}
	
	
	
	
	
	public void parseJson() {
		var mapper = new ObjectMapper();
		try {
			mapper.readValue("wrong json", Position.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
}
