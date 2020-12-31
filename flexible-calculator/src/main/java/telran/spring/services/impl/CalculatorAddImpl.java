package telran.spring.services.impl;

import telran.spring.services.interfaces.Calculator;
import static telran.spring.api.ApiConstants.*;

import org.springframework.stereotype.Service;

@Service(ADD)
public class CalculatorAddImpl implements Calculator {

	@Override
	public int calculate(int op1, int op2, String operation) {
		if(!operation.equals(ADD)) {
			throw new IllegalStateException("CalculatorAddIMPL implies onli operation ADD");
		}
		return op1 + op2;
	}
}
