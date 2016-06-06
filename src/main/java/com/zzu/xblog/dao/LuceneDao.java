package com.zzu.xblog.dao;

import com.zzu.xblog.model.Article;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.scene.input.KeyCode.T;

/**
 * Lucene全文检索
 */
@Component
public class LuceneDao {
    @Resource
    private BasicDataSource dataSource;

    /**
     * 搜索文章
     *
     * @param keyword
     * @return
     */
    public List<Article> searchArticle(String keyword) {
        List<Article> list = null;
        try {
            ResultSetHandler<List<Article>> h = new MyResultSetHandler<List<Article>>(keyword);
            QueryRunner run = new QueryRunner(dataSource);
            list = run.query("SELECT * FROM t_article", h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * resultSet处理类
     *
     * @param <T>
     */
    private class MyResultSetHandler<T> implements ResultSetHandler<T> {
        private Analyzer analyzer = null;
        private Directory directory = null;
        private IndexWriterConfig config = null;
        private IndexWriter iwriter = null;
        private String keyword = null;
        private String[] fields = {"title", "description", "content", "tag"};

        MyResultSetHandler(String keyword) throws Exception {
            this.keyword = keyword;
            analyzer = new StandardAnalyzer();
            directory = FSDirectory.open(Paths.get("F:/index"));
            config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            iwriter = new IndexWriter(directory, config);
        }

        public T handle(ResultSet rs) {
            try {
                while (rs.next()) {
                    Document doc = new Document();
                    doc.add(new Field("article_id", String.valueOf(rs.getInt("article_id")), TextField.TYPE_STORED));
                    doc.add(new Field("title", rs.getString("title"), TextField.TYPE_STORED));
                    doc.add(new Field("description", rs.getString("description"), TextField.TYPE_STORED));
                    doc.add(new Field("content", rs.getString("content"), TextField.TYPE_STORED));
                    doc.add(new Field("tag", rs.getString("tag"), TextField.TYPE_STORED));
                    doc.add(new Field("post_time", String.valueOf(rs.getDate("post_time")), TextField.TYPE_STORED));
                    doc.add(new Field("view_count", String.valueOf(rs.getDate("view_count")), TextField.TYPE_STORED));
                    doc.add(new Field("comment_count", String.valueOf(rs.getDate("comment_count")), TextField.TYPE_STORED));
                    doc.add(new Field("like_count", String.valueOf(rs.getDate("like_count")), TextField.TYPE_STORED));
                    iwriter.addDocument(doc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (iwriter != null) {
                        iwriter.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            return (T) search(keyword);
        }

        /**
         * 检索核心方法
         * @param keyword
         * @return
         */
        private List<Article> search(String keyword) {
            DirectoryReader ireader = null;
            IndexSearcher isearcher = null;
            List<Article> list = new ArrayList<>();
            List<ScoreDoc> hits = new ArrayList<>();
            List<Integer> helper = new ArrayList<>();

            try {
                ireader = DirectoryReader.open(directory);
                isearcher = new IndexSearcher(ireader);
                for(String field : fields) {
                    QueryParser parser = new QueryParser(field, analyzer);
                    Query query = parser.parse(keyword);
                    hits.addAll(Arrays.asList(isearcher.search(query, 1000, new Sort()).scoreDocs));
                }

                for (ScoreDoc hit : hits) {
                    Document hitDoc = isearcher.doc(hit.doc);
                    int articleId = Integer.parseInt(hitDoc.get("article_id"));

                    if(!helper.contains(articleId)) {
                        Article article = new Article();
                        article.setTitle(hitDoc.get("title"));
                        article.setDescription(hitDoc.get("description"));
                        article.setContent(hitDoc.get("content"));
                        article.setTag(hitDoc.get("tag"));
                        article.setArticleId(articleId);

                        helper.add(articleId);
                        list.add(article);
                    }
                    System.out.println(hit);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ireader != null) {
                        ireader.close();
                    }
                    if (directory != null) {
                        directory.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return list;
        }
    }
}
