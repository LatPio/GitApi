package com.example.GitApi.model;

import com.example.GitApi.model.requestModels.GitHubOwner;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record InRepos(
        @JsonAlias({"name"})
        String repositoryName,
        @JsonAlias({"owner"})
        GitHubOwner ownerLogin,
        @JsonProperty(value = "fork", access = JsonProperty.Access.WRITE_ONLY)
        Boolean fork
) {

}
