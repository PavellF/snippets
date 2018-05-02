package theFactory.method;

import java.util.List;

public abstract class Entity {

	private int width;
	
	private int height;
	
	private int depth;
	
	private String label;
	
	private List content;
	
	public void receive() {
		System.out.println("Something has recieved.");
	}
	
	public void register() {
		System.out.println("Entity: width"+width+
				"height "+height+
				"depth "+depth);
	}
	
	public void send() {
		System.out.println("Something has just sended.");
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List getContent() {
		return content;
	}

	public void setContent(List content) {
		this.content = content;
	}
	
}
