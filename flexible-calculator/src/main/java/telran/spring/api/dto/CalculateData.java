package telran.spring.api.dto;

public class CalculateData {
	
	public Integer op1;
	public Integer op2;
	public String operation;
	
	public CalculateData() {
	}

	public CalculateData(Integer op1, Integer op2, String operation) {
		this.op1 = op1;
		this.op2 = op2;
		this.operation = operation;
	}
}
