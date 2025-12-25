package com.herin.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    // 1. Configuration H2 (Base de données en mémoire pour le test)
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    
    // 2. Désactivation des outils de migration (inutiles en test unitaire)
    "spring.flyway.enabled=false",
    "spring.liquibase.enabled=false",

    // 3. Fausses clés pour satisfaire les @Value("${...}") dans vos Services
    // On met les deux formats (Variable d'env et propriété pointée) pour être sûr
    "STRIPE_SECRET_KEY=sk_test_dummy_key_for_testing_only",
    "stripe.secret.key=sk_test_dummy_key_for_testing_only",
    
    "JWT_SECRET=super_long_secret_key_for_tests_must_be_32_chars_long",
    "jwt.secret=super_long_secret_key_for_tests_must_be_32_chars_long",
    
    // 4. Autres variables potentielles
    "server.port=0" // Utilise un port aléatoire pour éviter les conflits
})
class EcommerceApplicationTests {

    @Test
    void contextLoads() {
        // Ce test vérifie simplement que le contexte Spring (les Beans)
        // arrive à démarrer sans erreur.
    }

}