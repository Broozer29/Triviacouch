package net.riezebos.triviacouch.util;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

class RandomTest {

	@Test
	void test() {
		System.out.println(ThreadLocalRandom.current().nextLong(1000, 1100));
	}

}
