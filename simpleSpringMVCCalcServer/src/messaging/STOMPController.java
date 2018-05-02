package messaging;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import exceptions.IncorrectExpressionException;
import objects.Answer;
import objects.Expression;
import objects.FullMathExpression;
import objects.Message;
import persistence.MathRepository;
import services.CalculatorService;
import util.AppUtilities;

@Controller
public class STOMPController {

	private static final Logger logger = Logger.getLogger(STOMPController.class);
	
	@Autowired
	private CalculatorService calculatorService;
	
	@Autowired
	private MathRepository mathRepository;

	@MessageMapping("/main")
	@SendToUser(destinations="/queue/output",broadcast=false)
	public Answer handleIncoming(Expression expression) {
		if(expression.getElements().size() <= 1){
			throw new IncorrectExpressionException("Expression is too short!");
		}
		
		FullMathExpression fullMathExpression = new FullMathExpression();
		fullMathExpression.setComplex(expression.getComplex());
		fullMathExpression.setStatement(AppUtilities.stringListToString(expression.getElements(), ""));
		
		Answer answer = 
				new Answer(calculatorService.getAnswer(expression.getElements(), expression.getMeasurement()));
		
		fullMathExpression.setAnswer(answer.getAnswer());
		
		mathRepository.save(fullMathExpression);
		return answer;
	}
	
	@MessageExceptionHandler
	@SendToUser(destinations="/queue/errors",broadcast=false)
	public Message handleMessagingException(Exception e) {
		logger.error("Error handling message: " + e.getMessage());
		return new Message(e.getMessage());
	}
	
	
}
