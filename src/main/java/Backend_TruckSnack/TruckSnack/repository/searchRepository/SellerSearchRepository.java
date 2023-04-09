package Backend_TruckSnack.TruckSnack.repository.searchRepository;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface SellerSearchRepository extends ElasticsearchRepository<Seller, Long> {
    Iterable<Seller> search(QueryBuilder query);
}
