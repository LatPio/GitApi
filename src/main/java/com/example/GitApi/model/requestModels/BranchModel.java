package com.example.GitApi.model.requestModels;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BranchModel {

    private String name;
    private CommitModel commit;
}
