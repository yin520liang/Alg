/**
 * 
 */
package alg.os;

import java.util.Arrays;

/**
 *  银行家算法
 *  
 * https://blog.csdn.net/shouldnotappearcalm/article/details/69396257
 */
public class Banker {
	
	static final int PNUM = 10; // number of process
	
	static final int RNUM = 10; // number of resource
	
	private int[] available = new int[RNUM]; // 当前可用
	
	private int[][] allocation = new int[PNUM][RNUM]; // 已分配
	
	private int[][] need = new int[PNUM][RNUM]; // 尚需要
	
	private int[][] max = new int[PNUM][RNUM]; // 最多需要子元素
	
	
	public synchronized boolean allocate(int pid, int[] request) {
		if(!le(request, need[pid])) 
			throw new RuntimeException("Process request more resource than need, refuse");
		
		if(le(request, available)) {
			// 1. try allocate
			tryAllocate(pid, request);
			// 2. check if safe
			if(isSafeState()) {
				return true;
			} else {
				rollback(pid, request);
				return false;
			}
		}
		return false;
	}


	private void tryAllocate(int pid, int[] request) {
		add(allocation[pid], request);
		minus(available, request);
		minus(need[pid], request);
	}

	
	private boolean isSafeState() {
		boolean[] finish = new boolean[PNUM];
		Arrays.fill(finish, false);
		int[] free = Arrays.copyOf(available, available.length);
		while(finishNum(finish) < PNUM) {
			int pid = findRandom(free, finish);
			if(pid < 0)
				return false;
			finish[pid] = true;
			release(pid, free);
		}
		return false;
	}



	private int finishNum(boolean[] finish) {
		int res = 0;
		for(boolean f : finish) {
			res = f? res + 1 : res;
		}
		return res;
	}


	private int findRandom(int[] free, boolean[] finish) {
		for(int i = 0; i < PNUM; ++i) {
			if(!finish[i] && le(need[i], free)) {
				// candidate
				return i;
			}
		}
		return -1;
	}
	
	private int findMin(int[] free, boolean[] finish) {
		int minSqure = Integer.MAX_VALUE;
		int pid = -1;
		for(int i = 0; i < PNUM; ++i) {
			if(!finish[i] && le(need[i], free)) {
				// candidate
				int s = sum(need[i]);
				if( s < minSqure) {
					minSqure = s;
					pid = i;
				}
			}
		}
		return pid;
	}
	

	private void release(int pid, int[] free) {
		add(free, allocation[pid]);		
	}


	private void rollback(int pid, int[] request) {
		minus(allocation[pid], request);
		add(available, request);
		add(need[pid], request);
	}
	
	
	private boolean le(int[] a, int[] b) {
		for(int i = 0; i < a.length; ++i) {
			if(a[i] > b[i])
				return false;
		}
		return true;
	}

	public void add(int[] a, int[] b) {
		for(int i = 0; i < a.length; ++i) {
			a[i] += b[i];
		}
	}
	
	public void minus(int[] a, int[] b) {
		for(int i = 0; i < a.length; ++i) {
			a[i] -= b[i];
		}
	}
	
	public int sum(int[] a) {
		int res = 0;
		for(int aa : a) {
			res += aa;
		}
		return res;
	}
	
	
	public static void main (String[] args) {
		Banker banker = new Banker();
		if(banker.allocate(1, new int[] {})) {
			System.out.println("allocate success");
		} else {
			System.out.println("process need to wait...");
		}
	}
	

}
