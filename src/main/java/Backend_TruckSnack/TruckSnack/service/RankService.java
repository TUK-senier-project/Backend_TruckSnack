package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.RankCategoryMapping;
import Backend_TruckSnack.TruckSnack.repository.temp.RankCategoryTemp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RankService {
    private final SellerRepository sellerRepository;
    public void category_rank(){
        /**
         * 카테고리 - 평점
         * 일단 매핑 - id , grade
         *
         */
        List<RankCategoryMapping> seller_list;
        List<RankCategoryTemp> rank_list;
        seller_list= sellerRepository.findByCategory(1);
        System.out.println("정렬전");
        seller_list.forEach(item -> {
            System.out.println(item.getId() +"  "+ item.getGrade());

        });
        System.out.println("정렬후");
        Collections.sort(seller_list, new Comparator<RankCategoryMapping>() {
            @Override
            public int compare(RankCategoryMapping o1, RankCategoryMapping o2) {
                return Double.compare(o1.getGrade() , o2.getGrade());
            }
        });

        seller_list.forEach(item -> {
            System.out.println(item.getId() +"  "+ item.getGrade());

        });



    }
}
