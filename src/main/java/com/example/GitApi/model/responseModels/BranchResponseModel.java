package com.example.GitApi.model.responseModels;



public record BranchResponseModel(
        String name,
        String lastCommitSha
) {
}