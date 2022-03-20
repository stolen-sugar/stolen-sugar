package com.stolensugar.web;

import com.stolensugar.web.controller.Controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppApplicationTests {

	@Autowired
	private Controller controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
