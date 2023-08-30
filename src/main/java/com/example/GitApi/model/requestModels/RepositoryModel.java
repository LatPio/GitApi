package com.example.GitApi.model.requestModels;


public record RepositoryModel(
        String name,
        Boolean fork,
        GitHubOwner owner
) {
}