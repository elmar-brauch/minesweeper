package de.bsi.example;

import java.util.Optional;

/**
 * Demo class to talk about:
 * - Optional instead of returning null
 */
public class NeverReturnNull {
	
	private void printTextInUpperCase() {
		String text = nullOrText().toUpperCase();
		System.out.println(text);
	}
	
	private String nullOrText() {
		return null;
	}
	
	private Optional<String> text() {
		return Optional.ofNullable(nullOrText());
	}
	
	private void printTextInLowerCase() {
		Optional<String> optText = text();
		optText.ifPresent(text -> System.out.println(text.toLowerCase()));
	}
}
