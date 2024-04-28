package dk.kea.dat3js.hogwarts5.prefects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PrefectControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void notNull() {
        assertNotNull(webTestClient);
    }

    // 5, 6, 7, 11

    @Test
    void getAllPrefects() {
        webTestClient
                .get().uri("/prefects")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                     [{"id": 5}, {"id": 6}, {"id": 7}, {"id": 11}]
               """);

    }

    @Test
    void getPrefectById() {
        webTestClient
                .get().uri("/prefects/5")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                     {"id": 5}
               """);
    }

    @Test
    void getAllPrefectsInHouse() {
        webTestClient
                .get().uri("/prefects/house/Gryffindor")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                     [{"id": 5}, {"id": 6}, {"id": 7}]
               """);
    }

    @Test
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
    void addExistingPrefectToPrefect() {
        webTestClient
                .post().uri("/prefects/5")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void removePrefect() {
        webTestClient
                .delete().uri("/prefects/5")
                .exchange()
                .expectStatus().isOk();
    }
}