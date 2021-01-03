package telran.spring;

import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.api.dto.CalculateData;
import telran.spring.controllers.FlexibleCalculatorController;
import telran.spring.services.interfaces.Calculator;
import static telran.spring.api.ApiConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
class FlexibleCalculatorApplicationTests {

	@Autowired
	MockMvc mock;

	@Autowired
	FlexibleCalculatorController controller;
	@Autowired
	Map<String, Calculator> calculators;

	// testing of the context
	// ==================================================================
	@Test
	void contextLoads() {
		assertNotNull(controller);
		assertNotNull(calculators);
	}
	
	// common test methods ==================================================================
	private void executeTest(String calcFunction, Integer firstParamValue, Integer secondParamValue, HttpStatus expectedStatus, String expectedResult) throws Exception {
		ResponseDTO responseDTO = new ResponseDTO(firstParamValue, secondParamValue, calcFunction);
		assertEquals(expectedStatus, responseDTO.status);
		if( expectedResult != null) {
			assertEquals(expectedResult, responseDTO.contentOfResponse);
		}
	}
	
	// testing of "add" functionality ==================================================================
	@Test
	void arithmeticsAddNormal() throws Exception { executeTest(ADD, 10, 40, OK, "50"); }
	@Test
	void arithmeticsAddMissedFirstParam() throws Exception { executeTest(ADD, null, 40, BAD_REQUEST, null); }
	@Test
	void arithmeticsAddMissedSecondParam() throws Exception { executeTest(ADD, 40, null, BAD_REQUEST, null); }
	@Test
	void arithmeticsAddMissedParams() throws Exception { executeTest(ADD, null, null, BAD_REQUEST, null); }
	
	// testing of "subtract" functionality ==================================================================
	@Test
	void arithmeticsSubNormal() throws Exception { executeTest (SUB, 10, 40, OK, "-30");}
	@Test
	void arithmeticsSubMissedFirstParam() throws Exception { executeTest(SUB, null, 40, BAD_REQUEST, null); }
	@Test
	void arithmeticsSubMissedSecondParam() throws Exception { executeTest(SUB, 40, null, BAD_REQUEST, null); }
	@Test
	void arithmeticsSubMissedParams() throws Exception { executeTest(SUB, null, null, BAD_REQUEST, null); }

	// testing of "multiply" functionality ==================================================================
	@Test
	void arithmeticsMulNormal() throws Exception { executeTest (MUL, 10, 40, OK, "400");}
	@Test
	void arithmeticsMulMissedFirstParam() throws Exception { executeTest(MUL, null, 40, BAD_REQUEST, null); }
	@Test
	void arithmeticsMulMissedSecondParam() throws Exception { executeTest(MUL, 40, null, BAD_REQUEST, null); }
	@Test
	void arithmeticsMulMissedParams() throws Exception { executeTest(MUL, null, null, BAD_REQUEST, null); }

	// testing of "divide" functionality ==================================================================
	@Test
	void arithmeticsDivNormal() throws Exception { executeTest (DIV, 80, 40, OK, "2");}
	@Test
	void arithmeticsDivMissedFirstParam() throws Exception { executeTest(DIV, null, 40, BAD_REQUEST, null); }
	@Test
	void arithmeticsDivMissedSecondParam() throws Exception { executeTest(DIV, 40, null, BAD_REQUEST, null); }
	@Test
	void arithmeticsDivMissedParams() throws Exception { executeTest(DIV, null, null, BAD_REQUEST, null); }

	// testing of "pow" functionality ==================================================================
	@Test
	void powNormal() throws Exception { executeTest (POW, 2, 3, OK, "8");}
	@Test
	void powMissedFirstParam() throws Exception { executeTest(POW, null, 40, BAD_REQUEST, null); }
	@Test
	void powMissedSeconsParam() throws Exception { executeTest(POW, 40, null, BAD_REQUEST, null); }
	@Test
	void powMissedParams() throws Exception { executeTest(POW, null, null, BAD_REQUEST, null); }
	
	// testing of "percent" functionality ==================================================================
	@Test
	void prcNormal() throws Exception { executeTest (PRC, 5, 100, OK, "5");}
	@Test
	void prcMissedFirstParam() throws Exception { executeTest(PRC, null, 40, BAD_REQUEST, null); }
	@Test
	void prcMissedSecondParam() throws Exception { executeTest(PRC, 40, null, BAD_REQUEST, null); }
	@Test
	void prcMissedParams() throws Exception { executeTest(PRC, null, null, BAD_REQUEST, null); }

	// testing of wrong operation's name ==================================================================
	@Test
	void wrongOperationsName() throws Exception { executeTest("abc", 10, 40, BAD_REQUEST, null); }
	
	class ResponseDTO {
		String contentOfResponse;
		HttpStatus status;

		ResponseDTO(Integer op1, Integer op2, String operation) throws Exception {
			MockHttpServletResponse response = getMocResponse(op1, op2, operation);
			status = getHttpStatus(response);
			contentOfResponse = response.getContentAsString();
		}
	}

	private String getJson(Integer op1, Integer op2, String operation) throws Exception {
		return new ObjectMapper().writeValueAsString(new CalculateData(op1, op2, operation));
	}

	private HttpStatus getHttpStatus(MockHttpServletResponse response) throws Exception {
		return HttpStatus.valueOf(response.getStatus());
	}
	private MockHttpServletResponse getMocResponse(Integer op1, Integer op2, String operation) throws Exception {
		return mock.perform(
				post(CALCULATOR).contentType(MediaType.APPLICATION_JSON).content(getJson(op1, op2, operation)))
				.andReturn().getResponse();
	}
}
