package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.searchRepository.SellerSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    @Autowired
    private SellerSearchRepository sellerSearchRepository;

    public List<Seller> search(String keyword){
        List<Seller> sellers = new ArrayList<>();

        QueryBuilder query = (QueryBuilder) QueryBuilders
                .boolQuery()
                .should(QueryBuilders.matchQuery("BUSINESS_NAME", keyword));
        Iterable<Seller> results = sellerSearchRepository.search(query);
        results.forEach(sellers::add);
        return sellers;
    }
}
