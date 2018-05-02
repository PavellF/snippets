package util;

import java.util.HashMap;
import java.util.Map;

public class HTMLParser {

	private static HTMLParser parser = null;
	
	private char parenthesisOpen; 
	
	private char parenthesisClose;
	
	private Map<String,HTMLElement> mappingMap;
	
	private Map<String,String> knownAttributes;
	
	private final static String TAGNAME = "tagname";
	
	private HTMLParser(){
		
		this.parenthesisClose = ']';
		this.parenthesisOpen = '[';
		this.mappingMap = new HashMap<>();
		this.knownAttributes = new HashMap<>();
		
		this.mappingMap.put("underline", new HTMLElement("u"));
		this.mappingMap.put("strike", new HTMLElement("s"));
		this.mappingMap.put("small", new HTMLElement("small"));
		this.mappingMap.put("strong", new HTMLElement("strong"));
		this.mappingMap.put("mark", new HTMLElement("mark"));
		this.mappingMap.put("input", new HTMLElement("kbd"));
		this.mappingMap.put("italic", new HTMLElement("em"));
		this.mappingMap.put("keyword", new HTMLElement("b"));
		this.mappingMap.put("img", new HTMLElement("img"));
		this.mappingMap.put("hyperlink", new HTMLElement("a"));
		this.mappingMap.put("content", new HTMLElement("span"));
		
		this.knownAttributes.put("src", "src");
		this.knownAttributes.put("refers", "href");
		
	}
	
	public static HTMLParser get(){
		if(parser == null) {
			parser = new HTMLParser();
		}
		
		return parser;
	}
	
	public String getStringRepresentationOfAllMappingValues(){
		
		final StringBuffer str = new StringBuffer("A mapping values shown below: \n");
		
		mappingMap.forEach((key,value) -> {
			str.append(key);
			str.append(" -> ");
			str.append(value.toString());
			str.append("\n");
		});
		
		return str.toString();
	}
	
	public void addMappingValue(String key, HTMLElement value){
		mappingMap.put(key, value);
	}
	
	public boolean removeMappingValue(String key){
		
		if(mappingMap.containsKey(key)){
			
			if(mappingMap.remove(key) != null)
			return true;
			
		}
		
		return false;
	}

	public char getParenthesisOpen() {
		return parenthesisOpen;
	}

	public void setParenthesisOpen(char parenthesisOpen) {
		this.parenthesisOpen = parenthesisOpen;
	}

	public char getParenthesisClose() {
		return parenthesisClose;
	}

	public void setParenthesisClose(char parenthesisClose) {
		this.parenthesisClose = parenthesisClose;
	}

	public Map<String, HTMLElement> getMappingMap() {
		return mappingMap;
	}

	public void setMappingMap(Map<String, HTMLElement> mappingMap) {
		this.mappingMap = mappingMap;
	}
	
	public String escapeHTML(String html){
		
		final char[] array = html.toCharArray();
		final StringBuffer buff = new StringBuffer();
		
		for(int i=0; i<array.length; i++){
			
			char iChar = array[i];
			
			switch(iChar){
				case '<' : buff.append("&lt;"); continue;
				case '>' : buff.append("&gt;"); continue;
				case '"' : buff.append("&quot;"); continue;
				case '\'' : buff.append("&#39;"); continue;
				case '/' : buff.append("&#x2F;"); continue;
				case '`' : buff.append("&#x60;"); continue;
				case '=' : buff.append("&#x3D;"); continue;
				default : buff.append(iChar);
			}
		}
		return buff.toString();
	}
	
	private String specialChar(char html){
		
		switch(html){
			case '\n' : return  "<br>";
			case '<' : return  "&lt;";
			case '>' : return  "&gt;";
			case '"' : return  "&quot;";
			case '\'' : return  "&#39;";
			case '/' : return  "&#x2F;";
			case '`' : return  "&#x60;";
			case '=' : return  "&#x3D;";
			default : return String.valueOf(html);
		}
		
	}
	
	public String parse(String incoming){
		
		if(incoming.isEmpty()){
			return incoming;
		}
		
		final char[] sequence = incoming.toCharArray();
		final StringBuffer output = new StringBuffer();
		final int length = sequence.length;
		
		for(int i = 0; i<length; i++){
			
			char character = sequence[i];
			
			if(character == parenthesisOpen){
				int current = i;
				i = parseElement(output ,sequence, i);
				
				if(i == current){
					output.append(character);
				}
				
			}else{
				output.append(specialChar(character));
			}
			
		}
		
		return output.toString();
	}
	
	private int parseElement(StringBuffer string ,char[] sequence, int startOfTag){
		
		final int length = sequence.length;
		
		final StringBuffer tagname = new StringBuffer();
		final StringBuffer innerHTML = new StringBuffer();
		
		boolean isEndOfTagDefinition = false;
		boolean isEndOfElement = false;
		HTMLElement element = null;
		
		for(int i = startOfTag+1; i<length; i++){
			
			char character = sequence[i];
			
			if(character == parenthesisClose && !isEndOfTagDefinition){
				isEndOfTagDefinition = true;
				
				final String name = tagname.toString();
				
				Map<String,String> attributes = getAttributes(name);
				
				final String tagAlias = attributes.get(TAGNAME);
				
				element = mappingMap.get(tagAlias);
				
				if(element == null) return startOfTag;
				
				tagname.replace(0, tagname.length(), tagAlias);
				
				attributes.remove(TAGNAME);
				
				if(attributes.size() != 0 && element.getAttributeValue() == null){
				
					Map<String,String> parsedAttributes = new HashMap<>();
				
					attributes.forEach((key,val) -> {
					
						parsedAttributes.put(knownAttributes.get(key), val);
					
					});
				
					element.setAttributeValue(parsedAttributes);
				
				}
				
				continue;
			
			}else if (character == parenthesisClose && isEndOfTagDefinition){
			
				isEndOfElement = true;
				final int lengthOfTag = tagname.length();
				
				if(sequence[i-lengthOfTag] == parenthesisOpen){
					isEndOfElement = false;
					continue;
				}
				
				for(int e = 0; e<lengthOfTag; e++){
					
					if(sequence[i-e-1] !=  tagname.charAt(lengthOfTag-e-1)){
						isEndOfElement = false;
						break;
					}
					
				}
				
				if(isEndOfElement){
					
					final int htmlLength = innerHTML.length();
					
					innerHTML.delete(htmlLength-lengthOfTag-1, htmlLength+1);
					element.setPlaceholder(innerHTML.toString());
					
					string.append(element.toString());
					
					return i;
					
				}
			}
			
			if(!isEndOfTagDefinition) tagname.append(character);
			
			if(isEndOfTagDefinition) innerHTML.append(character);
		}
		
		return startOfTag;
	}
	
	private Map<String,String> getAttributes(String tagWithAttributes){
		
		final char[] sequence = tagWithAttributes.toCharArray();
		final Map<String,String> output = new HashMap<>();
		final int length = sequence.length;
		
		boolean isKeyFound = false;
		boolean isValueFound = false;
		boolean isTagnameFound = false;
		final StringBuffer key = new StringBuffer();
		final StringBuffer value = new StringBuffer();
		final StringBuffer tagname = new StringBuffer();
		
		for(int i = 0; i<length; i++){
			
			if(sequence[i] == '=' && isKeyFound && !isValueFound){
				isValueFound = true;
				continue;
			}
			
			if(sequence[i] == ' ' && !isValueFound && !isKeyFound){
				isKeyFound = true;
				isTagnameFound = true;
				continue;
			}else if(sequence[i] == ' ' && isValueFound && isKeyFound){
				isValueFound = false;
				
				output.put(key.toString(), value.toString());
				key.delete(0, length);
				value.delete(0, length);
				
				continue;
			}
			
			if(isValueFound && isKeyFound){
				value.append(sequence[i]);
			}
			
			if(!isValueFound && isKeyFound){
				key.append(sequence[i]);
			}
			
			if(!isTagnameFound){
				tagname.append(sequence[i]);
			}
		}
		
		if(key.length() != 0 && value.length() != 0){
			output.put(key.toString(), value.toString());
		}
		
		output.put(TAGNAME, tagname.toString());
		
		return output;
	} 
	
	public static void main(String... args){
		Map<String,String> pair = new HashMap<>();
		pair.put("src", "href");
		
		System.err.println(HTMLParser.get().getStringRepresentationOfAllMappingValues());
		System.err.println(new HTMLElement("img",pair).toString());
		
		Map<String,String> d = HTMLParser.get().getAttributes("img");
		
		d.forEach((k,v) -> {
			System.err.println(k+" -> "+v);
		});
		
		System.err.println(HTMLParser.get().parse("text at the start [img src=https://i.ytimg.com/vi/fArAxyUovTs/hqdefault.jpg?custom=true&w=336&h=188&stc=true&jpg444=true&jpgq=90&sp=68&sigh=qS7aSNlzlbl80iEso3U7FhOclUE][img][b]this is strong text [small]and small inside[small][b][italic] "));
		
		System.err.println(HTMLParser.get().escapeHTML("text at the start sdfsdfsf. <a href='asf'>sdvsdv</a>"));
		
		
		System.err.println('\n');
	}

}
