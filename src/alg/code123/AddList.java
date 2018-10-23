/**
 * 
 */
package alg.code123;

/**
 * <a href=
 * "http://www.code123.cc/758.html">题目：你有两个由单链表表示的数。每个结点代表其中的一位数字。数字的存储是逆序的，
 * 也就是说个位位于链表的表头。写一函数使这两个数相加并返回结果，结果也由链表表示。</a>
 * 
 * @title AddList
 */
public class AddList {
	
	public static void main(String[] args) {
		// 3-1-5
		// 5-9-2
		LinkedListNode l1 = new LinkedListNode(3, new LinkedListNode(1, new LinkedListNode(5, null)));
		LinkedListNode l2 = new LinkedListNode(5, new LinkedListNode(9, new LinkedListNode(2, null)));
		LinkedListNode res = add(l1, l2);
		output(res);
	}


	public static LinkedListNode add(LinkedListNode node1, LinkedListNode node2) {
		LinkedListNode p = node1;
		LinkedListNode q = node2;
		LinkedListNode head = new LinkedListNode(-1, null); // 空的头结点
		LinkedListNode prev = head;
		int carry = 0;
		while (p != null || q != null) {
			int val = carry;
			if (p != null)
				val += p.val;
			if (q != null)
				val += q.val;
			if (val >= 10) {
				prev.next = new LinkedListNode(val - 10, null);
				carry = 1;
			} else {
				prev.next = new LinkedListNode(val, null);
				carry = 0;
			}
			prev = prev.next;
			if (p != null)
				p = p.next;
			if (q != null)
				q = q.next;
		}
		// handle carry
		if (carry == 1) {
			prev.next = new LinkedListNode(1, null);
		}
		return head;
	}
	
	
	private static void output(LinkedListNode res) {
		LinkedListNode p = res.next;
		while(p != null) {
			System.out.print(p.val + "-");
			p = p.next;
		}
		System.out.println();
	}

}

class LinkedListNode {
	int val;
	LinkedListNode next;

	LinkedListNode(int val, LinkedListNode next) {
		this.val = val;
		this.next = next;
	}

}
