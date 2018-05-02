package util;

import java.util.Map;

public class HTMLElement {

	private String tagName;
	
	private Map<String,String> attributeValue;
	
	private String placeholder;
	
	public HTMLElement(String tagName, Map<String, String> attributeValue, String placeholder) {
		super();
		this.tagName = tagName;
		this.attributeValue = attributeValue;
		this.placeholder = placeholder;
	}
	
	public HTMLElement(String tagName, Map<String, String> attributeValue) {
		super();
		this.tagName = tagName;
		this.attributeValue = attributeValue;
	}
	
	public HTMLElement(String tagName) {
		super();
		this.tagName = tagName;
	}
	
	public HTMLElement(String tagName,String placeholder) {
		super();
		this.tagName = tagName;
		this.placeholder = placeholder;
	}
	
	@Override
	public String toString(){
		
		final StringBuffer str = new StringBuffer();
		
		str.append("<");
		str.append(tagName);
		
		if(attributeValue != null){
		
			attributeValue.forEach((key,value) -> {
			
				str.append(' ');
				str.append(key);
				str.append('=');
				str.append('\'');
				str.append(value);
				str.append('\'');
			
			});
		
		}
		
		if(placeholder == null || placeholder.isEmpty()){
			
			str.append('>');
			
		}else{
			
			str.append('>');
			str.append(placeholder);
			str.append("</");
			str.append(tagName);
			str.append('>');
			
		}
		
		return str.toString();
	}

	public String getTagName() {
		return tagName;
	}

	public Map<String, String> getAttributeValue() {
		return attributeValue;
	}

	public String getPlaceholder() {
		return placeholder;
	}
	
	public void setValue(String attribute, String value){
		this.attributeValue.put(attribute, value);
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setAttributeValue(Map<String, String> attributeValue) {
		this.attributeValue = attributeValue;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	
	public void removeAttribute(String attribute){
		this.attributeValue.remove(attribute);
	}
	
}
