package telran.spring.services.interfaces;

import java.util.Set;

public interface Calculator {
	int calculate(int op1, int op2, String operation);
	Set<String> getSupportedOperations();
}
