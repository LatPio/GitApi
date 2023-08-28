package com.example.GitApi.clients;

import com.example.GitApi.exeption.UserNotFoundException;
import com.example.GitApi.model.BranchModel;
import com.example.GitApi.model.GitHubSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GitRepoClient {
    private final String GITHUB_API_URL = "https://api.github.com/";
    private final WebClient.Builder webClientBuilder;

    public GitHubSearchResponse getRepoListByUser(String user){
        GitHubSearchResponse response = webClientBuilder.build()
                .get()
                .uri(GITHUB_API_URL+"search/repositories?q=user:"+ user)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value()==422,
                        clientResponse -> {throw new UserNotFoundException();
                        })
                .bodyToMono(GitHubSearchResponse.class).block();
        return response;
    }

    public BranchModel[] getRepoBranNames(String repoName, String user){
        BranchModel[] response = webClientBuilder.build()
                .get()
                .uri(GITHUB_API_URL+"repos/"+ user + "/"+ repoName + "/branches")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BranchModel[].class).block();
        return response;
    }
}
