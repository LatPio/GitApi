package com.example.GitApi.service;

import com.example.GitApi.clients.GitRepoClient;
import com.example.GitApi.model.InRepos;
import com.example.GitApi.model.OutBranch;
import com.example.GitApi.model.OutRepos;
import com.example.GitApi.model.requestModels.*;
import com.example.GitApi.model.responseModels.BranchResponseModel;
import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

    @Test
    void outResponseList() {
        //given
        when(gitRepoClient.getRepoListByUserV2("user")).thenReturn(sampleDataRepositoriesV2());
        when(gitRepoClient.getRepoBranNamesV2("Repo", "user")).thenReturn(sampleBranchV2());
        List<OutRepos> expected = sampleResponseV2();
        //when
        final var output =underTest.outResponseList("user");
        //then
        assertThat(output).usingRecursiveAssertion().isEqualTo(expected);
    }


    private GitHubSearchResponse sampleDataRepositories(){
        return new GitHubSearchResponse(1 ,
                        List.of(new RepositoryModel("Repo", false, new GitHubOwner("user")))
                );
    }

    private List<InRepos> sampleDataRepositoriesV2(){
        List<InRepos> output = new ArrayList<>();
        output.add(new InRepos("Repo", new GitHubOwner("user"),false));
        output.add(new InRepos("RepoForked", new GitHubOwner("user"),true));

        return output;
    }

    private List<BranchModel> sampleBranch(){
        return List.of(new BranchModel("master", new CommitModel("shasha")));
    }
    private List<OutBranch> sampleBranchV2(){
        return List.of(new OutBranch("master", new CommitModel("shasha")));
    }

    private RepoOwnerBranchesShaResponse sampleResponse(){
        return new RepoOwnerBranchesShaResponse("Repo", "user", List.of(new BranchResponseModel("master", "shasha")));
    }

    private List<OutRepos> sampleResponseV2(){
        List<OutRepos> output = new ArrayList<>();
        output.add(new OutRepos("Repo", new GitHubOwner("user"), sampleBranchV2()));
        return output;
    }

}