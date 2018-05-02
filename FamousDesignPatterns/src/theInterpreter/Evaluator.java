package theInterpreter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Evaluator implements Expression {

	private Expression syntaxTree;
	
	public Evaluator(final String expression) {
		
		final Deque<Expression> expressionStack = new ArrayDeque<>();
		
        for (final String token : expression.split(" ")) {
        	
            if (token.equals("+")) {
            	
                final Expression subExpression = new Plus(expressionStack.pop(), expressionStack.pop());
                expressionStack.push(subExpression);
                
            } else if (token.equals("-")) {
            	
                final Expression right = expressionStack.pop();
                final Expression left = expressionStack.pop();
                final Expression subExpression = new Minus(left, right);
                expressionStack.push(subExpression);
                
            } else {
            		expressionStack.push(new Variable(token));
            }
                
        }
        
        syntaxTree = expressionStack.pop();
	}
	
	@Override
	public int interpret(Map<String, Expression> variables) {
		return syntaxTree.interpret(variables);
	}

}
