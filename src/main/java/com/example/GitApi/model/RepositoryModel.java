package com.example.GitApi.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RepositoryModel {

    private String name;
    private Boolean fork;
    private GitHubOwner owner;
}
