package telran.spring;

import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.api.dto.CalculateData;
import telran.spring.services.interfaces.Calculator;
import static telran.spring.api.ApiConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
class FlexibleCalculatorApplicationTests {
	
	@Autowired
	MockMvc mock;
	
	@Autowired
	Map<String, Calculator> calculators;

	@Test
	void contextLoads() {
		assertNotNull(calculators);
	}
	
	@Test
	void addNormal() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		CalculateData calculateData = new CalculateData(10, 40, ADD);
		String json = mapper.writeValueAsString(calculateData);
		MockHttpServletResponse response = mock.perform(post(CALCULATOR).contentType(MediaType.APPLICATION_JSON).content(json)).andReturn().getResponse();
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		assertEquals(HttpStatus.OK, status);
		assertEquals("50", response.getContentAsString());
	}
}
