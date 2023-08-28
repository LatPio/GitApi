package com.example.GitApi.model.requestModels;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GitHubSearchResponse {
    private Integer total_count;
    private List<RepositoryModel> items;
}
