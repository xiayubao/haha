package com.lucene.demo;
import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexReaderTest {
	public static void main(String[] args) throws IOException {
		// 指定索引库位置
		Directory directory = FSDirectory.open(new File("/Users/xiayubao/Desktop/indexRepo"));
		// 创建读取索引对象IndexReader
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建查询索引对象
		IndexSearcher indexsearcher = new IndexSearcher(indexReader);
		// 创建查询,指定条件，查询数据量的限制
		Query query = new TermQuery(new Term("content", "spring"));
		// 执行查询，第一个参数是查询对象，第二个参数是查询结果返回的最大值
		TopDocs topDocs = indexsearcher.search(query, 10);
		// 查询结果的总记录数
		System.out.println("总记录数：" + topDocs.totalHits);

		// 遍历查询结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			//scoreDoc.doc属性就是document对象的ID
			int docID = scoreDoc.doc;
			//根据document的ID找到document对象
			Document doc = indexsearcher.doc(docID);
			// 获取文档的内容
			System.out.println("文件名：" + doc.get("name"));
			System.out.println("文件大小" + doc.get("size"));
			System.out.println("文件路径" + doc.get("path"));
			//System.out.println("文件内容" + doc.get("content"));

		}
		// 关闭资源indexReader对象
		indexReader.close();
	}

}
