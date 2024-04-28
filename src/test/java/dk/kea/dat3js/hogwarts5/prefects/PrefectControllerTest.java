package dk.kea.dat3js.hogwarts5.prefects;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PrefectControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void notNull() {
        assertNotNull(webTestClient);
    }

    // prefect id's: 6, 7, 10, 11

    @Test
    @Order(2)
    void getAllPrefects() {
        webTestClient
                .get().uri("/prefects")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                     [{"id": 6}, {"id": 7}, {"id": 10}, {"id": 11}]
               """);

    }

    @Test
    @Order(3)
    void getPrefectById() {
        webTestClient
                .get().uri("/prefects/6")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                     {"id": 6}
               """);
    }

    @Test
    @Order(4)
    void getAllPrefectsInHouse() {
        webTestClient
                .get().uri("/prefects/house/Gryffindor")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                     [{"id": 6}, {"id": 7}, {"id": 10}]
               """);
    }

    @Test
    @Order(5)
    void addPrefect() {
        webTestClient
                .post().uri("/prefects/8")
                .exchange()
                .expectStatus().isCreated()
                .expectBody().json("""
                     {"id": 8}
               """);
    }

    @Test
    @Order(6)
    void addExistingPrefectToPrefect() {
        webTestClient
                .post().uri("/prefects/6")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void removePrefect() {
        webTestClient
                .delete().uri("/prefects/5")
                .exchange()
                .expectStatus().isOk();
    }
}