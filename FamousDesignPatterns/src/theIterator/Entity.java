package theIterator;

public class Entity {

	private String title;
	private int weight;
	private int length;
	private int width;
	private int depth;
	
	public Entity(String title, int weight, int length, int width, int depth) {
		super();
		this.title = title;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.depth = depth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "Entity [title=" + title + ", weight=" + weight + ", length=" + length + ", width=" + width + ", depth="
				+ depth + "]";
	}
	
}
