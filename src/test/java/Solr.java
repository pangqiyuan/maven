
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class Solr {
    @Test
    public void solr() throws Exception{
         final String URL="http://localhost:8080/solr/core1";
        HttpSolrClient build = new HttpSolrClient.Builder(URL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
//        SolrInputDocument document = new SolrInputDocument();
//        document.setField("id","mm");
//        document.setField("name","可以");
//        build.add(document);
//        build.commit();
        SolrQuery entries = new SolrQuery();
//        entries.set("q","*:*");
        entries.setQuery("name:可以");
//        entries.addSort("",Ord)
        QueryResponse query = build.query(entries);
        SolrDocumentList results = query.getResults();
        long numFound = results.getNumFound();
        System.out.println(numFound);
        for(SolrDocument solrDocument:results){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("name"));
        }
    }

}
