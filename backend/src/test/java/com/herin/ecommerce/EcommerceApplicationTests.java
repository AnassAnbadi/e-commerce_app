package com.herin.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource; // <--- CET IMPORT EST CRUCIAL

@SpringBootTest
@TestPropertySource(properties = { // <--- Assurez-vous que cette annotation est bien là
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.flyway.enabled=false",
    "spring.liquibase.enabled=false",
    "STRIPE_SECRET_KEY=sk_test_dummy",
    "stripe.secret.key=sk_test_dummy",
    "JWT_SECRET=super_long_secret_key_for_tests_must_be_32_chars_long",
    "jwt.secret=super_long_secret_key_for_tests_must_be_32_chars_long"
})
class EcommerceApplicationTests {

    @Test
    void contextLoads() {
    }

}