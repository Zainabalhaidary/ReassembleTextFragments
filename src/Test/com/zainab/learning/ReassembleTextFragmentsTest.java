package com.zainab.learning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReassembleTextFragmentsTest {

	/*
	 * 
	 * Imagine you hav;page of text. You value this text and have no hard or soft copies of it;;ou have 5 copies of the same page of te
	 * 
	 * */
	@Test
	void testReassemble() {
		String example1 = "Imagine you hav;page of text. You value this text and have no hard or soft copies of it;;ou have 5 copies of the same page of te";
		String result = ReassembleTextFragments.reassemble(example1);
		assertEquals("Imagine you have 5 copies of the same page of text. You value this text and have no hard or soft copies of it", result);
	}

}
