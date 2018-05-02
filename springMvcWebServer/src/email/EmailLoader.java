package email;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EmailLoader {

	private static EmailLoader emailLoader;
	private final Configuration cgf;
	
	private EmailLoader(){
		this.cgf = new Configuration();
	}
	
	public static EmailLoader get(){
		
		if(emailLoader == null){
			emailLoader = new EmailLoader();
		}
		
		return emailLoader;
	}
	
	public String getTemplate(String path, Map<String, String> vars){
		
		Writer stream = new StringWriter();
		
		try{
			
			Template template = cgf.getTemplate(path);
			template.setEncoding("UTF-8");
			template.process(vars, stream);
			
		}catch(IOException | TemplateException e){
			e.printStackTrace();
		}
		
		return stream.toString();
		
	}
	
}
