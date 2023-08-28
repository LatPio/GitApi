package com.example.GitApi.service;

import com.example.GitApi.clients.GitRepoClient;
import com.example.GitApi.model.GitHubSearchResponse;
import com.example.GitApi.model.RepoOwnerBranchesShaResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GitHubApiService {

    private final GitRepoClient gitRepoClient;

    public List<RepoOwnerBranchesShaResponse> outResponse(String username , HttpServletRequest request){

        GitHubSearchResponse repoList = gitRepoClient.getRepoListByUser(username);

        List<RepoOwnerBranchesShaResponse> outResponse =
                repoList.getItems().stream()
                        .filter(f -> !f.getFork())
                        .map(value -> RepoOwnerBranchesShaResponse.builder()
                                .repository_name(value.getName())
                                .owner_login(value.getOwner().getLogin())
                                .branches(List.of(gitRepoClient.getRepoBranNames(value.getName(), username)))
                                .build()
                        ).collect(Collectors.toList());

        return outResponse;




    }





}
