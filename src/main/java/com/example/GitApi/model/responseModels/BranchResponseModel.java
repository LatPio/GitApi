package com.example.GitApi.model.responseModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BranchResponseModel {
    private String name;
    private String lastCommitSha;
}
