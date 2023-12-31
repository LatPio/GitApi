package com.example.GitApi.service;

import com.example.GitApi.clients.GitRepoClient;
import com.example.GitApi.model.InRepos;
import com.example.GitApi.model.OutRepos;
import com.example.GitApi.model.responseModels.BranchResponseModel;
import com.example.GitApi.model.requestModels.GitHubSearchResponse;
import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GitHubApiService {

    private final GitRepoClient gitRepoClient;

    public List<RepoOwnerBranchesShaResponse> outResponse(String username){


        GitHubSearchResponse repoList = gitRepoClient.getRepoListByUser(username);

        return repoList.items().stream()
                .filter(f -> !f.fork())
                .map(value ->
                        new RepoOwnerBranchesShaResponse(
                                value.name(),
                                value.owner().login(),
                                gitRepoClient.getRepoBranNames(value.name(), username)
                                    .stream()
                                    .map(branchModel ->
                                            new BranchResponseModel(
                                                    branchModel.name(),
                                                    branchModel.commit().sha())
                                        )
                                    .collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());
    }

    public List<OutRepos> outResponseList(String username){


        List<InRepos> repoList = gitRepoClient.getRepoListByUserV2(username);

        return repoList.stream()
                .filter(f->!f.fork())
                .map(value -> new OutRepos(
                        value.repositoryName(),
                        value.ownerLogin(),
                        new ArrayList<>(gitRepoClient.getRepoBranNamesV2(value.repositoryName(), username))
        )
                ).collect(Collectors.toList());
    }

}
