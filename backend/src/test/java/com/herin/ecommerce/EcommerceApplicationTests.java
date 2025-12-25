// package com.herin.ecommerce;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.TestPropertySource;

// @SpringBootTest
// @TestPropertySource(properties = {
//     // 1. AJOUT DE "MODE=PostgreSQL" : Critique si vous avez des types spécifiques Postgres
//     "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
//     "spring.datasource.driverClassName=org.h2.Driver",
//     "spring.datasource.username=sa",
//     // 2. MOT DE PASSE NON VIDE : Évite les erreurs de lecture de la ligne
//     "spring.datasource.password=sa",
    
//     // Configuration Hibernate pour H2
//     "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
//     "spring.jpa.hibernate.ddl-auto=create-drop",
    
//     // Désactiver les migrations (Flyway/Liquibase)
//     "spring.flyway.enabled=false",
//     "spring.liquibase.enabled=false",

//     // Fausses clés pour les services tiers (Stripe/JWT)
//     "STRIPE_SECRET_KEY=sk_test_dummy_value_for_ci_cd",
//     "stripe.secret.key=sk_test_dummy_value_for_ci_cd",
    
//     "JWT_SECRET=super_long_secret_key_for_tests_must_be_32_chars_long",
//     "jwt.secret=super_long_secret_key_for_tests_must_be_32_chars_long"
// })
// class EcommerceApplicationTests {

//     @Test
//     void contextLoads() {
//         // Si le contexte charge, le test est réussi.
//     }

// }