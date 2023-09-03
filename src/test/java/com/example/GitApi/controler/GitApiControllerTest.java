package com.example.GitApi.controler;


import com.example.GitApi.exeption.UserNotFoundException;
import com.example.GitApi.model.OutBranch;
import com.example.GitApi.model.OutRepos;
import com.example.GitApi.model.requestModels.CommitModel;
import com.example.GitApi.model.requestModels.GitHubOwner;
import com.example.GitApi.model.responseModels.BranchResponseModel;
import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import com.example.GitApi.service.GitHubApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
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
                                            .path("api/v1/repos")
                                            .queryParam("userName", "userName")
                                            .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(RepoOwnerBranchesShaResponse.class);
    }

    @Test
    void getList() {

        when(gitHubApiService.outResponseList(anyString())).thenReturn(sampleResponseV2());


        webTestClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("api/v1/repos/new")
                                .queryParam("userName", "userName")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<OutRepos>>() {});
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


    private List<OutRepos> sampleResponseV2(){
        List<OutRepos> output = new ArrayList<>();
        output.add(new OutRepos("Repo", new GitHubOwner("user"), sampleBranchV2()));
        return output;
    }
    private List<OutBranch> sampleBranchV2(){
        return List.of(new OutBranch("master", new CommitModel("shasha")));
    }


}