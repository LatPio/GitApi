package com.example.GitApi.controler;

import com.example.GitApi.model.RepoOwnerBranchesShaResponse;
import com.example.GitApi.service.GitHubApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/repos")
@AllArgsConstructor
public class GitApiController {

    private final GitHubApiService apiService;

    @GetMapping()
    public ResponseEntity<List<RepoOwnerBranchesShaResponse>> getOutList(
            @RequestParam String userName,
            HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(apiService.outResponse(userName , request));
    }




}
