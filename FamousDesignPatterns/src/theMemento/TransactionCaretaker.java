package theMemento;

import java.util.ArrayDeque;
import java.util.Deque;

public class TransactionCaretaker {

	private final Deque<TransactionMemento> stack = new ArrayDeque<>();
	
	public TransactionMemento getMemento() {
		return stack.pop();
	}
	
	public void addMemento(TransactionMemento memento) {
        stack.push(memento);
    }
	
}
