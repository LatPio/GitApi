package com.example.GitApi.clients;

import com.example.GitApi.model.requestModels.BranchModel;
import com.example.GitApi.model.requestModels.GitHubSearchResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static junit.framework.TestCase.assertNotNull;

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
							"total_count": "0",
							"items": []
						}
						"""
                )
        );
        GitHubSearchResponse data = gitRepoClient.getRepoListByUser("test");
        assertNotNull(data);
        assertThat(data.getTotal_count()).isEqualTo(0);
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
                                   },
                                   "protected": false
                               }
                           ]
                        """
                )
        );

        BranchModel[] data = gitRepoClient.getRepoBranNames("test", "test");
        assertNotNull(data);
        assertThat(data[0].getName()).isEqualTo("master");
    }
}