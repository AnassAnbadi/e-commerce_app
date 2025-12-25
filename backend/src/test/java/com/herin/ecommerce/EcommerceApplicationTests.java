package com.herin.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    // Force l'utilisation de H2 au lieu de Postgres pour ce test
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    
    // Désactive Flyway/Liquibase si présents pour éviter les erreurs de migration
    "spring.flyway.enabled=false",
    "spring.liquibase.enabled=false",

    // Injecte des fausses clés pour que l'app démarre sans planter
    "STRIPE_SECRET_KEY=sk_test_dummy_value",
    "stripe.secret.key=sk_test_dummy_value",
    "JWT_SECRET=super_long_secret_key_for_tests_must_be_32_chars_long",
    "jwt.secret=super_long_secret_key_for_tests_must_be_32_chars_long"
})
class EcommerceApplicationTests {

    @Test
    void contextLoads() {
        // Si ce test passe, cela veut dire que l'application 
        // sait démarrer correctement (Context Spring valide).
    }

}