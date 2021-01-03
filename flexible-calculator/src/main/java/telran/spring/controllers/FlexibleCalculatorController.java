package telran.spring.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.spring.api.dto.CalculateData;
import telran.spring.services.interfaces.Calculator;
import static telran.spring.api.ApiConstants.*;

@RestController
public class FlexibleCalculatorController {

	@Autowired
	List<Calculator> calculatorsBeans;

	Map<String, Calculator> calculators = new HashMap<>();

	@PostMapping(CALCULATOR)
	ResponseEntity<?> calculate(@RequestBody CalculateData calculateData) {
		Calculator calculator = calculators.get(calculateData.operation);
		if (calculator == null) {
			return ResponseEntity.badRequest().body("unknow operation");
		}
		try {
			return ResponseEntity.ok(calculator.calculate(calculateData.op1, calculateData.op2, calculateData.operation));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(501).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostConstruct
	private void displaySupportedOperations() {
		calculatorsBeans.forEach(calculator -> calculator.getSupportedOperations().forEach(operation -> calculators.put(operation, calculator)));
		System.out.println("Supported operations: " + calculators.keySet());
	}
}
