package com.example.GitApi.service;

import com.example.GitApi.clients.GitRepoClient;
import com.example.GitApi.model.requestModels.*;
import com.example.GitApi.model.responseModels.BranchResponseModel;
import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubApiServiceTest {

    @InjectMocks
    private GitHubApiService underTest;

    @Mock
    private GitRepoClient gitRepoClient;


    @Test
    void outResponse() {
        //given
        when(gitRepoClient.getRepoListByUser("user")).thenReturn(sampleDataRepositories());
        when(gitRepoClient.getRepoBranNames("Repo", "user")).thenReturn(sampleBranch());
        List<RepoOwnerBranchesShaResponse> expected = List.of(sampleResponse());
        //when
        final var output =underTest.outResponse("user");
        //then
        assertThat(output).usingRecursiveAssertion().isEqualTo(expected);
    }


    private GitHubSearchResponse sampleDataRepositories(){
        return GitHubSearchResponse.builder()
                .total_count(1)
                .items(
                        List.of(RepositoryModel.builder()
                                .name("Repo")
                                        .fork(false)
                                        .owner(GitHubOwner.builder().login("user").build())
                                .build())
                )
                .build();
    }

    private BranchModel[] sampleBranch(){
        return new BranchModel[]{BranchModel.builder()
                .name("master")
                .commit(CommitModel.builder()
                        .sha("shasha")
                        .build())
                .build()};
    }

    private RepoOwnerBranchesShaResponse sampleResponse(){
        return RepoOwnerBranchesShaResponse.builder()
                .repository_name("Repo")
                .owner_login("user")
                .branches(List.of(BranchResponseModel.builder()
                        .name("master").lastCommitSha("shasha").build()))
                .build();
    }

}