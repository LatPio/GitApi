package com.example.GitApi.controler;

import com.example.GitApi.model.OutRepos;
import com.example.GitApi.model.responseModels.RepoOwnerBranchesShaResponse;
import com.example.GitApi.service.GitHubApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/repos")
@AllArgsConstructor
public class GitApiController {

    private final GitHubApiService apiService;

    @GetMapping()
    public List<RepoOwnerBranchesShaResponse> getOutList(
            @RequestParam String userName){
        return apiService.outResponse(userName);
    }
    @GetMapping("/new")
    public List<OutRepos> getList(
            @RequestParam String userName){
        return apiService.outResponseList(userName);
    }




}
