package simplejava.concurrent.cas;

import java.util.concurrent.atomic.AtomicReference;

public class CASTest {
	
	public static void main(String[] args) {
		
	}

}

/**
 * Stack base on CAS to synchronize.
 *
 */
class ConcurrentStack<T> {
	
	private AtomicReference<Node<T>> top;
	
	public void push(T t) {
		Node<T> newNode = new Node<>(t);
		for(;;) {
			Node<T> cur = top.get();
			newNode.next = cur;
			if(top.compareAndSet(cur, newNode))
				break;
		}
	}
	
	public T pop() {
		Node<T> oldNode, nextNode;
		for(;;) {
			oldNode = top.get();
			if(oldNode == null)
				return null;
			
			nextNode = oldNode.next;
			if(top.compareAndSet(oldNode, nextNode))
				return oldNode.item;
		}
	}
	
	static class Node<T> {
		T item;
		Node<T> next;
		
		Node(T item, Node<T> next) {
			this.item = item;
			this.next = next;
		}
		
		Node(T item) {
			this(item, null);
		}
		
	}
	
}


class ConcurrentList<E> {
	private AtomicReference<Node<E>> head = new AtomicReference<>(new Node<E>(null, null));
	private AtomicReference<Node<E>> tail = head;
	
	public void add(E item) {
		Node<E> newNode = new Node<>(item, null);
		for(;;) {
	 		Node<E> curTail = tail.get();
			Node<E> next = curTail.next.get();
			if(next == null) {
				if(curTail.next.compareAndSet(null, newNode)) {
					tail.compareAndSet(curTail, newNode);
					return;
				}
			}else {
				tail.compareAndSet(curTail, next);
			}
		}
	}
	
	
	static class Node<E> {
		E item;
		AtomicReference<Node<E>> next;
		
		Node(E item, Node<E> next) {
			this.item = item;
			this.next = new AtomicReference<>(next);
		}
	}
}
