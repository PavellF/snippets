package theInterpreter;

import java.util.Map;

public class Minus implements Expression {

	private final Expression left;
	private final Expression right;
	
	public Minus(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int interpret(Map<String, Expression> variables) {
		return left.interpret(variables) - right.interpret(variables);
	}

}
