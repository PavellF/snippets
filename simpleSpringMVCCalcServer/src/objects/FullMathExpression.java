package objects;

public class FullMathExpression {

	private String statement;
	private int complex;
	private long id;
	private String answer;
	
	public FullMathExpression(String statement,int complex,long id,String answer){
		this.statement = statement;
		this.complex = complex;
		this.id = id;
		this.answer = answer;
	}
	public FullMathExpression(){}

	public int getComplex() {
		return complex;
	}

	public void setComplex(int complex) {
		this.complex = complex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
}
