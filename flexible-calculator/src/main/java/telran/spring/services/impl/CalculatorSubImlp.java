package telran.spring.services.impl;

import static telran.spring.api.ApiConstants.*;

import org.springframework.stereotype.Service;

import telran.spring.services.interfaces.Calculator;

@Service(SUB)
public class CalculatorSubImlp implements Calculator {

	@Override
	public int calculate(int op1, int op2, String operation) {
		if(!operation.equals(SUB)) {
			throw new IllegalStateException("CalculatorSubIMPL implies onli operation subtract");
		}
		return op1 - op2;
	}

}
