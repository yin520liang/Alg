package alg.string;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import alg.string.pattern.BruteForce;

public class StringMain {
	
	public static void main(String[] args) throws IOException {
		String[] s = {"4PGC938", "1ICK750", "2IYE230", "1ICK758", "3CI0720", "10HV845"};
//		LSD lsd = new LSD();
//		lsd.sort2(s, 5);
		
//		MSD msd = new MSD();
//		msd.sort(s);
		
//		Quick3String q3 = new Quick3String();
//		q3.sort(s);
//		System.out.println(Arrays.toString(s));
		
		String txt = FileUtils.readFileToString(new File("resources/scatter.txt"));
		String patten = "web";
//		KMP kmp = new KMP();
//		int pos = kmp.search(txt, patten);
//		System.out.println(pos);
		BruteForce bf = new BruteForce();
		System.out.println(bf.search(txt, patten));
	}
	
}
