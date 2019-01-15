/**
 * 
 */
package alg;

/**
 * 仿照redis的跳表实现：没有增加backward指针和span
 * 
 * @title SkipList
 */
public class SkipList {

	private int maxLevel = 0;
	
	private int length = 0;

	private static int MAX_LEVEL = 32; // 最大层数32

	private static final double P = 0.5; // 以1/2的概率选取下一层索引节点

	private SkNode head;
	
	
	public SkipList(int max) {
		MAX_LEVEL = max;
		head = new SkNode(null, 0, MAX_LEVEL);
	}
	

	public void create(String[] strs, int[] scores) {
		assert strs.length == scores.length;
		for (int i = 0; i < strs.length; ++i) {
			insert(strs[i], scores[i]);
		}
	}

	public void insert(String s, int score) {
		int level = randomLevel();
		if (level > maxLevel)
			maxLevel = level;
		SkNode node = new SkNode(s, score, level);
		SkNode p = head;
		for (int i = level - 1; i >= 0; --i) {
			while (p.forward[i] != null && p.forward[i].score < score)
				p = p.forward[i];
			SkNode next = p.forward[i];
			p.forward[i] = node;
			node.forward[i] = next;
		}
		length ++;

	}

	/**
	 *  随机设置新节点的层数：被选入第二层的概率是P，被选入第三层的概率是p*p, n层是p^(n-1)
	 */
	private int randomLevel() {
		int l = 1;
		while (l < MAX_LEVEL && Math.random() < P) {
			++l;
		}
		return l;
	}

	public String search(int score) {
		SkNode p = head;
		for (int i = maxLevel - 1; i >= 0; --i) {
			while (p.forward[i] != null && p.forward[i].score < score)
				p = p.forward[i];
		}
		p = p.forward[0];
		if (p != null && p.score == score)
			return p.obj;
		return null;
	}

	public void remove(int score) {
		SkNode p = head;
		for (int i = maxLevel - 1; i >= 0; --i) {
			while (p.forward[i] != null && p.forward[i].score < score)
				p = p.forward[i];

			if (p.forward[i] != null && p.forward[i].score == score) {
				SkNode next = p.forward[i].forward[i];
				p.forward[i] = next;
				-- length;
			}
		}
	}
	
	
	public static void main(String[] args) {
		// 14 elements
		String [] strs = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
		int[] scores = {1, 8, 9, 5, 3, 6, 7, 10, 80, 11, 66, 34, 12, 19};
		
		int n = strs.length;
		int level = 0;
		while(n > 0) {
			n = n >> 1;
			++ level;
		}
		
		SkipList slist = new SkipList(level);
		slist.create(strs, scores);
		System.out.println(slist.search(19));
		slist.print();
	}
	
	
	private void print() {
		SkNode p;
		for(int i = maxLevel - 1; i >= 0; --i) {
			p = head.forward[i];
			System.out.printf("Level %d:", i);
			while(p != null) {
				System.out.printf("-%4d", p.score);
				p = p.forward[i];
			}
			System.out.println();
		}
	}

	/**
	 *  跳表节点类
	 */
	class SkNode {
		int levels; // the length of forward array
		SkNode[] forward;
		int score;
		String obj;

		SkNode(String s, int score, int level) {
			this.obj = s;
			this.score = score;
			assert levels > 0 && levels < MAX_LEVEL : "Invalid ";
			this.levels = level;
			forward = new SkNode[level];
		}

		SkNode(String s, int score) {
			this.obj = s;
			this.score = score;
		}

		SkNode() {
		}

	}

}
