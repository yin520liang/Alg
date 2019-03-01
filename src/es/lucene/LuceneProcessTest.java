package es.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
/**
 * {
 * 	"desc": "This is the text to be indexed",
 *  "location": [43, 128]
 * }
 * @author yang
 *
 */
public class LuceneProcessTest {

	public static void main(String[] args) throws IOException, ParseException {
		// Store
		Analyzer analyzer = new StandardAnalyzer();
		// To store an index on disk, use this instead:
		Directory directory = FSDirectory.open(Paths.get("resources/testindex"));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter iw = new IndexWriter(directory, config);
		Document doc = new Document();
		String text = "This is the text to be indexed";
		doc.add(new TextField("desc", text, Store.YES));
		doc.add(new IntPoint("location", 43, 128));
		iw.addDocument(doc);
		iw.close();

		// Search
		DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser("desc", analyzer);
	    Query query = parser.parse("text");
	    ScoreDoc[] hits = isearcher.search(query, 100).scoreDocs;
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
	         Document hitDoc = isearcher.doc(hits[i].doc);
	         System.out.println(hitDoc.get("desc"));
	    }
	    ireader.close();
	    directory.close();
	}

}
