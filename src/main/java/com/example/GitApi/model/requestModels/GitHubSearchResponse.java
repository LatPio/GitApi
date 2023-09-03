package com.example.GitApi.model.requestModels;

import java.util.List;

public record GitHubSearchResponse(
        Integer totalCount,
        List<RepositoryModel> items
) {
}