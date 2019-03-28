package simplejava.concurrent.pattern;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSumTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	
	private int start;
	private int end;
	
	public ForkJoinSumTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int n = end - start + 1;
		if(n > 16) {
			int mid = (start + end) / 2;
			ForkJoinSumTask task1 = new ForkJoinSumTask(start, mid);
			ForkJoinSumTask task2 = new ForkJoinSumTask(mid + 1, end);
			task1.fork();
			task2.fork();			
			return task1.join() + task2.join();
			
		} else {
			System.out.printf("%s add from %d to %d\n", Thread.currentThread().getName(), start, end);
			int res = 0;
			for(int a = start; a <= end; ++ a)
				res += a;
			return res;
		}
	}
	
	
	
	public static void main(String[] args) {
		ForkJoinSumTask fjtask = new ForkJoinSumTask(1, 100);
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);
		int addResult = forkJoinPool.invoke(fjtask);
		System.out.println("addResult = " + addResult);   
	}

}
