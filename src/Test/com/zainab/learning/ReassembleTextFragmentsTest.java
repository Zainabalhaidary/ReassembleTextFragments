package com.zainab.learning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReassembleTextFragmentsTest {

	@Test
	void testReassemble() {
		String example1 = "O draconia;conian devil! Oh la;h lame sa;saint!";
		String mergedLine = ReassembleTextFragments.reassemble(example1);
		assertEquals("O draconian devil! Oh lame saint!", mergedLine);
	}

}
