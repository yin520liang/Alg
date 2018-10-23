/**
 * 
 */
package alg.code123;

/**
 * <a href="http://www.code123.cc/763.html">如何只用一个数组实现三个栈</a>
 * <p>
 * 
 * @title ThreeStackOnOneArray
 */
public class ThreeStackOnOneArray {
	
	public static void main(String[] args) {
		MultiStack stack = new MultiStack(10);
		// 0: 1-2-3
		// 1: 5-6-4
		// 2: 7-8-9
		stack.push(1, 0);
		stack.push(2, 0);
		stack.push(5, 1);
		stack.push(6, 1);
		stack.push(7, 2);
		stack.push(3, 0);
		stack.push(4, 1);
		stack.push(8, 2);
		stack.push(9, 2);
		
		System.out.println(stack.pop(0));
		System.out.println(stack.pop(1));
		System.out.println(stack.pop(2));
		
		System.out.println(stack.pop(0));
		System.out.println(stack.pop(1));
		System.out.println(stack.pop(2));
	}


	
	static class MultiStack {
		Element[] e; // element
		int[] p = {-1, -1, -1}; // three pointers
		int[] freespace;
		int free = -1; // freespace pointer
		
		MultiStack(int capacity) {
			e = new Element[capacity];
			initFreeLink(capacity);
		}

		private void initFreeLink(int n) {
			freespace = new int[n];
			for(int i = 0; i < n - 1; ++i)
				freespace[i] = i + 1;
			freespace[n - 1] = -1;
			free = 0;		
		}


		void push(int v, int pi) {
			assert pi >= 0 && pi < 3 : "Invalid pi value: " + pi;
			if(free == -1)
				return; // full
			// allocate space
			int index = free;
			free = freespace[free];
			freespace[index] = -1;
			// push
			e[index]  = new Element(v, p[pi]);
			p[pi] = index;
		}
		
		int pop(int pi) {
			assert pi >= 0 && pi < 3 : "Invalid pi value: " + pi;
			if(p[pi] == -1)
				return -1; // empty
			int index = p[pi];
			Element get = e[index];
			p[pi] = get.next;
			// return space
			freespace[index] = free;
			free = index;
			return get.val;
		}

	}
	
	
	static class Element {
		int val;
		int next;
		
		Element(int val) {
			this.val = val;
		}
		Element(int val, int next) {
			this.val = val;
			this.next = next;
		}
	}

}
