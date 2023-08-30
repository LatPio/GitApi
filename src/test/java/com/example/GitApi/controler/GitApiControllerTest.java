package com.example.GitApi.controler;


import com.example.GitApi.exeption.UserNotFoundException;
import com.example.GitApi.model.responseModels.BranchResponseModel;
import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import com.example.GitApi.service.GitHubApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GitApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GitHubApiService gitHubApiService;


    @Test
    void getOutList() {

        when(gitHubApiService.outResponse(anyString())).thenReturn(Collections.singletonList(sampleResponse()));


        webTestClient.get().uri(uriBuilder ->
                                    uriBuilder
                                            .path("api/v1/repos/new")
                                            .queryParam("userName", "userName")
                                            .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(RepoOwnerBranchesShaResponse.class);
    }

    @Test
    void getOutListWith404error() {
        when(gitHubApiService.outResponse(anyString())).thenThrow(new UserNotFoundException());

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("api/v1/repos")
                                .queryParam("userName")
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
        when(gitHubApiService.outResponse(anyString())).thenReturn(Collections.singletonList(sampleResponse()));

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

    private RepoOwnerBranchesShaResponse sampleResponse(){
        return new RepoOwnerBranchesShaResponse("Repo", "user", List.of(new BranchResponseModel("master", "shasha")));
    }

}