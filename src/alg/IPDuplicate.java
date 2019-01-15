/**
 * 
 */
package alg;

import java.util.Scanner;

/**
 * 查找重复ip
 */
public class IPDuplicate {
	
	
	public static void main(String[] args) {
		String[] ips = {
				"0.0.0.0",
				"192.168.0.1",
				"64.5.8.90",
				"0.0.0.0",
				"255.255.255.255"
		};
		duplicate(ips);
	}
	
	private static void duplicate(String[] ips) {
		int[] bits_pos = new int[64 * 1024 * 1024]; // 2^7 * 2^10 * 2 ^10 * 32 = 2^32 bits
		int[] bits_neg = new int[64 * 1024 * 1024];
		for(String ipStr : ips) {
			int ip = transfer2IP(ipStr);
			int[] bits = bits_pos;
			if(ip < 0) {
				ip = -ip;
				bits = bits_neg;
			}
			int bucket = ip >> 5; // ip / 32
			int res = ip & 31 ; // ip % 32
			if(bits[bucket] >>> res == 1) {
				System.out.println(ipStr);
			}
			bits[bucket] |= 0x01 << res;
		}		
	}


	private static int transfer2IP(String line) {
		String[] parts = line.split("\\.");
		int res = 0;
		res += Integer.parseInt(parts[0]) << 24;
		res += Integer.parseInt(parts[1]) << 16;
		res += Integer.parseInt(parts[2]) << 8;
		res += Integer.parseInt(parts[3]);
		return res;
	}
}
