package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void notNull() {
        assertNotNull(webTestClient);
    }

    @Test
    void createStudentWithFullName() {
        webTestClient
                .post().uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
{
                            "fullName": "Buster Man Mikkel",
                            "house": "Gryffindor",
                            "schoolYear": 7
                        }
                        """)
                .exchange()
        .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json("""
    {
                            "firstName": "Buster",
                            "middleName": "Man",
                            "lastName": "Mikkel",
                            "fullName": "Buster Man Mikkel",
                            "house": "Gryffindor",
                            "schoolYear": 7
                        }
    
    """)
                .jsonPath("$.id").exists();


//                .jsonPath("$.firstName").isEqualTo("Buster")
//                .jsonPath("$.middleName").isEqualTo("Man")
//                .jsonPath("$.lastName").isEqualTo("Mikkel")
//                .jsonPath("$.fullName").isEqualTo("Buster Man Mikkel")
//                .jsonPath("$.house").isEqualTo("Gryffindor")
//                .jsonPath("$.schoolYear").isEqualTo(7);

    }

}
