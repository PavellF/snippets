package objects;

import java.util.LinkedList;
import java.util.Queue;

public class FixedSizeQueue <T>{

	private Queue<T> queue;
	private int max;
	
	public FixedSizeQueue(int max){
		queue = new LinkedList<T>();
		this.max = max;
	}
	
	public boolean add(T smth){
		if(queue.size() < max){
			return queue.add(smth);
		}else{
			remove();
			return queue.add(smth);
		}
	}
	
	public void remove(){
		queue.poll();
	}
	
	
}
