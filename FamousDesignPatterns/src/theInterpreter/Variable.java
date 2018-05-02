package theInterpreter;

import java.util.Map;

public class Variable implements Expression {

	private final String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	@Override
	public int interpret(Map<String, Expression> variables) {
		
		final Expression expression = variables.get(name);
		
		if (null == expression) {
			return 0;
		}
		
		return expression.interpret(variables);
	}

}
