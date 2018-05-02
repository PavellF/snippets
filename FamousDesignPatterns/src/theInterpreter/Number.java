package theInterpreter;

import java.util.Map;

public class Number implements Expression {

	private final int number;
	
	public Number(final int number) {
		this.number = number;
	}

	@Override
	public int interpret(Map<String, Expression> variables) {
		return number;
	}

}
