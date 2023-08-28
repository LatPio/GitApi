package com.example.GitApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RepoOwnerBranchesShaResponse {

    private String repository_name;
    private String owner_login;
    private List<BranchModel> branches;
}
