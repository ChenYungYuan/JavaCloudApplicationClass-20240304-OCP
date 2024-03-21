package com.ta.question01.test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class TestJavaStream {

	@Test
	void test() {
		System.out.println("測試");
		
		List<String> list =  Stream.of("AAA","BBB","CCC").collect(Collectors.toList());
		
		list.forEach(System.out::println);
	}

}
