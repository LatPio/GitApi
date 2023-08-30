package com.example.GitApi.model.responseModels;

import java.util.List;


public record RepoOwnerBranchesShaResponse(
        String repositoryName,
        String ownerLogin,
        List<BranchResponseModel> branches
) {
}