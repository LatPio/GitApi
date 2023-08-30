package com.example.GitApi.model.requestModels;



public record BranchModel(
        String name,
        CommitModel commit
) {
}