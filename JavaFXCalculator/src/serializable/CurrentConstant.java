package serializable;

import java.io.Serializable;

public class CurrentConstant implements Serializable{

	private static final long serialVersionUID = -3646454424400913981L;
	private String constSign;
	private String constValue;
	
	public CurrentConstant(String constSign, String constValue){
		
		this.constSign = constSign;
		this.constValue = constValue;
	}
	
	public String getConstSign(){
		return this.constSign;
	} 
	
	public String getConstValue(){
		return this.constValue;
	}
}
