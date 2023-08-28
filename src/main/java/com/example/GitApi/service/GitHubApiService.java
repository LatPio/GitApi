package com.example.GitApi.service;

import com.example.GitApi.clients.GitRepoClient;
import com.example.GitApi.model.responseModels.BranchResponseModel;
import com.example.GitApi.model.requestModels.GitHubSearchResponse;
import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GitHubApiService {

    private final GitRepoClient gitRepoClient;

    public List<RepoOwnerBranchesShaResponse> outResponse(String username){

        GitHubSearchResponse repoList = gitRepoClient.getRepoListByUser(username);

        return repoList.getItems().stream()
                .filter(f -> !f.getFork())
                .map(value -> RepoOwnerBranchesShaResponse.builder()
                        .repository_name(value.getName())
                        .owner_login(value.getOwner().getLogin())
                        .branches(
                                Arrays.stream(
                                        gitRepoClient.getRepoBranNames(value.getName(), username))
                                            .map(branchModel -> BranchResponseModel.builder()
                                            .name(branchModel.getName())
                                            .lastCommitSha(branchModel.getCommit().getSha())
                                            .build()
                                        ).collect(Collectors.toList())
                        )
                        .build()
                ).collect(Collectors.toList());




    }





}
