package com.lucene.demo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexWriterTest {

	public static void main(String[] args) throws IOException {
		// 1.指定索引库位置
		Directory directory = FSDirectory.open(new File("/Users/xiayubao/Desktop/indexRepo"));
		// 创建一个 标准的分词器
		Analyzer analyzer = new StandardAnalyzer();
		// 第二个参数，分析器对象
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		// 2.创建写入索引IndexWriter对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// 3.获取源文档
		File srcFile = new File("/Users/xiayubao/Desktop/searchsource");

		File[] listFiles = srcFile.listFiles();

		for (File file : listFiles) {
			Document doc = new Document();
			// 获取文件名称
			String fileName = file.getName();
			// 创建文件名域
			// 第一个参数：域的名称
			// 第二个参数：域的内容
			// 第三个参数：是否存储
			Field nameFiled = new TextField("name", fileName, Store.YES);
			doc.add(nameFiled);

			// 获取文件大小
			long fileSize = FileUtils.sizeOf(file);
			Field sizeField = new TextField("size", fileSize + "", Store.YES);
			doc.add(sizeField);

			// 获取文件路径
			String filePath = file.getPath();
			Field PathField = new TextField("path", filePath, Store.YES);
			doc.add(PathField);

			// 获取文件内容
			String fileContent = FileUtils.readFileToString(file);
			Field contentField = new TextField("content", fileContent, Store.YES);
			doc.add(contentField);

			// 4.把文档写入索引库
			indexWriter.addDocument(doc);

		}
		// 5.关闭资源indexWriter
		indexWriter.close();

	}
}
