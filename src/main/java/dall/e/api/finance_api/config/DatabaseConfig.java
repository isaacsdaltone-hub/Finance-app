package dall.e.api.finance_api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "dall.e.api.finance_api.repository")
@EntityScan(basePackages = "dall.e.api.finance_api.entity")
@EnableTransactionManagement
public class DatabaseConfig {
}