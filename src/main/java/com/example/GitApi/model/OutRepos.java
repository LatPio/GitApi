package com.example.GitApi.model;

import com.example.GitApi.model.requestModels.GitHubOwner;

import java.util.List;

public record OutRepos(
        String repositoryName,
        GitHubOwner ownerLogin,
        List<OutBranch> branches
) {


}
