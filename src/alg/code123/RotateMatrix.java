/**
 * 
 */
package alg.code123;

/**
 * 题目：一张图像表示成NxN的矩阵，图像中每个像素是4个字节，写一个函数把图像旋转90度。 你能原地进行操作吗？(即不开辟额外的存储空间) <br>
 * <a href="http://www.code123.cc/746.html">解答</a> <br>
 * 思路：逆时针旋转90度，可以先绕对角线翻转，再交换i和n-i-1行. 逆时针/顺时针交换180
 * 
 * @title RotateMatrix
 */
public class RotateMatrix {

	public static void main(String[] arg) {
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
		RotateMatrix rm = new RotateMatrix();
		rm.output(a, 4);
//		rm.rotate90Inversed(a, 4);
		rm.rotate180(a, 4);
		rm.output(a, 4);
		
	}

	/**
	 * 顺时针/逆时针选准180：先交换i和n-i-1行，再交换j和n-1-j列
	 */
	public void rotate180(int[][] a, int n) {
		for (int i = 0; i < n / 2; ++i) {
			for (int j = 0; j < n; ++j) {
				swap(a, i, j, n - 1 - i, j);
			}
		}
		for (int j = 0; j < n / 2; ++j) {
			for (int i = 0; i < n; ++i) {
				swap(a, i, j, i, n - 1 - j);
			}
		}
	}

	/**
	 * 顺时针90：对角交换，交换j和n-i-j列
	 */
	public void rotate90Clockwise(int[][] a, int n) {
		for (int i = 0; i < n; ++i) {
			for (int j = i + 1; j < n; ++j) {
				swap(a, i, j, j, i);
			}
		}
		for (int j = 0; j < n / 2; ++j) {
			for (int i = 0; i < n; ++i) {
				swap(a, i, j, i, n - 1 - j);
			}
		}
	}

	/**
	 * 逆时针90：对角交换，交换i和n-1-i行
	 */
	public void rotate90Inversed(int[][] a, int n) {
		for (int i = 0; i < n; ++i) {
			for (int j = i + 1; j < n; ++j) {
				swap(a, i, j, j, i);
			}
		}
		for (int i = 0; i < n / 2; ++i) {
			for (int j = 0; j < n; ++j) {
				swap(a, i, j, n - 1 - i, j);
			}
		}
	}

	private void swap(int[][] a, int i, int j, int _i, int _j) {
		int tmp = a[i][j];
		a[i][j] = a[_i][_j];
		a[_i][_j] = tmp;
	}
	
	
	private void output(int[][] a, int n) {
		for(int i=0; i<n; ++i){
	        for(int j=0; j<n; ++j)
	            System.out.print(a[i][j] + " ");
	        System.out.println();
	    }
		 System.out.println();
	}

}
