package Backend_TruckSnack.TruckSnack.config;

import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.searchRepository.SellerSearchRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"Backend_TruckSnack.TruckSnack.repository"})
@EnableElasticsearchRepositories(basePackages = {"Backend_TruckSnack.TruckSnack.repository.searchRepository"})
public class AppConfig {
}
