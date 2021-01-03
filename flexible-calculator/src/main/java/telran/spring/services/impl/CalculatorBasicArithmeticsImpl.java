package telran.spring.services.impl;

import telran.spring.services.interfaces.Calculator;
import static telran.spring.api.ApiConstants.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import org.springframework.stereotype.Service;

@Service
public class CalculatorBasicArithmeticsImpl implements Calculator {

	Map<String, IntBinaryOperator> methods = new HashMap<>();

	private CalculatorBasicArithmeticsImpl() {
		methods.put(ADD, (a, b) -> a + b);
		methods.put(SUB, (a, b) -> a - b);
		methods.put(MUL, (a, b) -> a * b);
		methods.put(DIV, (a, b) -> a / b);
	}

	@Override
	public int calculate(int op1, int op2, String operation) {
		return methods.get(operation).applyAsInt(op1, op2);
	}

	@Override
	public Set<String> getSupportedOperations() {
		return methods.keySet();
	}
}
