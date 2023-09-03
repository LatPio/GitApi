package com.example.GitApi.clients;

import com.example.GitApi.model.InRepos;
import com.example.GitApi.model.OutBranch;
import com.example.GitApi.model.requestModels.BranchModel;
import com.example.GitApi.model.requestModels.GitHubSearchResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class GitRepoClientTest {

    private GitRepoClient gitRepoClient;
    private MockWebServer server;

    @BeforeEach
    public void setup() {
        this.server = new MockWebServer();
        this.gitRepoClient = new GitRepoClient(WebClient.builder(), server.url("/").toString());
    }

    @Test
    void getRepoListByUser() {
        server.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 200")
                .addHeader("Content-Type", "application/json")
                .setBody("""
						{
							"totalCount": "1",
							"items": [
							    "name":"Repo1",
							    "fork":"false",
							    "owner": [
							        "login" : "user"
							    ]
							]
						}
						"""
                )
        );
        GitHubSearchResponse data = gitRepoClient.getRepoListByUser("user");
        assertThat(data.totalCount()).isEqualTo(1);
        assertThat(data.items().get(0).name()).isEqualTo("Repo1");
        assertThat(data.items().get(0).fork()).isEqualTo(false);
        assertThat(data.items().get(0).owner().login()).isEqualTo("user");

    }


    @Test
    void getRepoBranNames() {
        server.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 200")
                .addHeader("Content-Type", "application/json")
                .setBody("""
                            [
                               {
                                   "name": "master",
                                   "commit": {
                                       "sha": "c8a91dfb0"
                                   }
                               }
                           ]
                        """
                )
        );
        List<BranchModel> data = gitRepoClient.getRepoBranNames("test", "user");
        assertThat(data.get(0).name()).isEqualTo("master");
        assertThat(data.get(0).commit().sha()).isEqualTo("c8a91dfb0");
    }

    @Test
    void getRepoListByUserV2() {
        server.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 200")
                .addHeader("Content-Type", "application/json")
                .setBody("""
						[
						{
							"name": "Repo1",
							"fork": "false",
							"owner": {
							        "login" : "user"
							    }
							
						}
						]
						"""
                )
        );
        List<InRepos>  data = gitRepoClient.getRepoListByUserV2("user");
        assertThat(data.get(0).repositoryName()).isEqualTo("Repo1");
        assertThat(data.get(0).fork()).isEqualTo(false);
        assertThat(data.get(0).ownerLogin().login()).isEqualTo("user");

    }


    @Test
    void getRepoBranNamesV2() {
        server.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 200")
                .addHeader("Content-Type", "application/json")
                .setBody("""
                            [
                               {
                                   "name": "master",
                                   "commit": {
                                       "sha": "c8a91dfb0"
                                   }
                               }
                           ]
                        """
                )
        );
        List<OutBranch> data = gitRepoClient.getRepoBranNamesV2("test", "user");
        assertThat(data.get(0).branchName()).isEqualTo("master");
        assertThat(data.get(0).lastCommit().sha()).isEqualTo("c8a91dfb0");
    }

}