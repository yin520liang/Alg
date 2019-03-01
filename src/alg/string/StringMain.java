package alg.string;

import java.util.Arrays;

public class StringMain {
	
	public static void main(String[] args) {
		String[] s = {"4PGC938", "1ICK750", "2IYE230", "1ICK758", "3CI0720", "10HV845"};
//		LSD lsd = new LSD();
//		lsd.sort2(s, 5);
		
//		MSD msd = new MSD();
//		msd.sort(s);
		
		Quick3String q3 = new Quick3String();
		q3.sort(s);
		System.out.println(Arrays.toString(s));
	}
	
}
