package es.lucene;

import java.io.IOException;

import org.apache.commons.io.Charsets;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.CharSequenceOutputs;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.FST.INPUT_TYPE;
import org.apache.lucene.util.fst.Util;

/**
 * Lucene FST使用
 * 
 * @author yang
 *
 */
public class FSTTest {

	public static void main(String[] args) throws IOException {
		// Input values (keys). These must be provided to Builder in Unicode sorted
		// order!
		String inputValues[] = { "cat", "dog", "dogs" };
		String outputValues[] = { "haha", "hoho", "wow" };

		// build minimal fst
		CharSequenceOutputs outputs = CharSequenceOutputs.getSingleton();
		Builder<CharsRef> builder = new Builder<CharsRef> (INPUT_TYPE.BYTE1, outputs);
		IntsRefBuilder scratchInts = new IntsRefBuilder();
		for (int i = 0; i < inputValues.length; i++) {
			builder.add(Util.toIntsRef(new BytesRef(inputValues[i]), scratchInts), new CharsRef(outputValues[i]));
		}
		FST<CharsRef> fst = builder.finish();

		// retrieve by key
		CharsRef value = Util.get(fst, new BytesRef("dog"));
		System.out.println(new String(value.chars)); // 7
	}

}
