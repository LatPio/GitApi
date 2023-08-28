package com.example.GitApi.clients;

import com.example.GitApi.exeption.UserNotFoundException;
import com.example.GitApi.model.requestModels.BranchModel;
import com.example.GitApi.model.requestModels.GitHubSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GitRepoClient {

    @Value("${token}")
    private String token;

    private final String GITHUB_API_URL = "https://api.github.com/";
    private final WebClient.Builder webClientBuilder;

    public GitHubSearchResponse getRepoListByUser(String user){
        return webClientBuilder.build()
                .get()
                .uri(GITHUB_API_URL+"search/repositories?q=user:"+ user)
                .header("Authorization", getToken() )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value()==422,
                        clientResponse -> {throw new UserNotFoundException();
                        })
                .bodyToMono(GitHubSearchResponse.class).block();
    }

    public BranchModel[] getRepoBranNames(String repoName, String user){
        return webClientBuilder.build()
                .get()
                .uri(GITHUB_API_URL+"repos/"+ user + "/"+ repoName + "/branches")
                .header("Authorization", getToken() )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BranchModel[].class).block();
    }

    protected String getToken(){
        if(token.length()>1){
            return "Bearer "+token;
        } else {
        return null;
    }}
}
