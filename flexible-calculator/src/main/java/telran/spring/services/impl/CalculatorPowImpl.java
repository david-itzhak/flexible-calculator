package telran.spring.services.impl;

import static telran.spring.api.ApiConstants.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.IntBinaryOperator;

import org.springframework.stereotype.Service;

import telran.spring.services.interfaces.Calculator;

@Service
public class CalculatorPowImpl implements Calculator {

	Map<String, IntBinaryOperator> methods = new HashMap<>();

	private CalculatorPowImpl() {
		methods.put(POW, (a, b) -> (int)Math.pow(a, b));
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
