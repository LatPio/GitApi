package com.example.GitApi.model;

import com.example.GitApi.model.requestModels.CommitModel;
import com.fasterxml.jackson.annotation.JsonAlias;

public record OutBranch(
        @JsonAlias({"name"})
        String branchName,
        @JsonAlias({"commit"})
        CommitModel lastCommit
) {
}
