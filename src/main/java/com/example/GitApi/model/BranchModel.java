package com.example.GitApi.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BranchModel {

    private String name;
    private CommitModel commit;
}
