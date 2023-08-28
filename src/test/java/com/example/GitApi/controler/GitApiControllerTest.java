package com.example.GitApi.controler;


import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GitApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${name}")
    private String userName;

    @Test
    void getOutList() {

        webTestClient.get().uri(uriBuilder ->
                                    uriBuilder
                                            .path("api/v1/repos")
                                            .queryParam("userName", userName)
                                            .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(RepoOwnerBranchesShaResponse.class);
    }

    @Test
    void getOutListWith404error() {

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("api/v1/repos")
                                .queryParam("userName", "")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo("404")
                .jsonPath("$.message").isEqualTo("Exception: User Not Found in GitHub")
        ;
    }


    @Test
    void getOutListWith406error() {

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("api/v1/repos")
                                .build())
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectHeader().valueEquals("Interceptor", "Not Supported Accept header")
                .expectBody()
                .jsonPath("$.status").isEqualTo("406")
                .jsonPath("$.message").isEqualTo("Exception: application/xml not supported")
        ;
    }

}