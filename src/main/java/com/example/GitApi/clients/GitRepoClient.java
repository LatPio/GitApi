package com.example.GitApi.clients;

import com.example.GitApi.exeption.UserNotFoundException;
import com.example.GitApi.model.requestModels.BranchModel;
import com.example.GitApi.model.requestModels.GitHubSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class GitRepoClient {

    @Value("${token}")
    public String token;

    private final WebClient webClientBuilder;

    public GitRepoClient(WebClient.Builder builder, @Value("${url}") String baseUrl) {
        this.webClientBuilder = builder.baseUrl(baseUrl).build();
    }

    public GitHubSearchResponse getRepoListByUser(String user){
        return webClientBuilder
                .get()
                .uri("/search/repositories?q=user:"+ user)
                .header("Authorization", this.token )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value()==422,
                        clientResponse -> {throw new UserNotFoundException();
                        })
                .bodyToMono(GitHubSearchResponse.class).block();
    }

    public List<BranchModel> getRepoBranNames(String repoName, String user){
        return webClientBuilder
                .get()
                .uri("/repos/"+ user + "/"+ repoName + "/branches")
                .header("Authorization", this.token )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BranchModel>>() {}).block();
    }
}
