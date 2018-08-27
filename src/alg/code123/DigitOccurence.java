package alg.code123;

/**
 * 题目：求0~n数字中2出现的总次数.（如1234中2出现一次）
 * <p>
 * 思路：分别求个位，十位，百位……的出现次数相加。以百位为例，200~299有100个数，类似的十位有10个，个位一个。
 * <ul>
 * 分三种情况考虑：
 * <li>11034:高位从0~10覆盖所有200~299的情况，即11*100
 * <li>11234:高位从0~10覆盖所有200~299的情况；高位为11时，取决于低位数，即11*100+35
 * <li>11434:高位从0~11覆盖所有200~299的情况，即12*100.
 * </ul>
 * 同理可分析个位，千位等情况
 */
public class DigitOccurence {
	
	public static void main(String[] args) {
		DigitOccurence d = new DigitOccurence();
		System.out.println(d.count(130, 2));
	}
	
	public int count(int n, int a) {
		int count = 0;
		int base = 1;
		int high = n, low = 0;
		while(high > 0) {
			int tail = high % 10;
			high /= 10;
			if(tail == a) {
				count += high * base + low + 1;
			}else if (tail < a) {
				count += high * base;
			}else {
				count += (high + 1) * base;
			}
			low += tail * base;
			base *= 10;
		}
		return count;
	}

}
