package com.example.GitApi.exeption;

import com.example.GitApi.exeption.config.GlobalErrorCode;

public class UserNotFoundException extends ErrorWrapper {

    public UserNotFoundException() {
        super(GlobalErrorCode.ERROR_GITHUB_USER_NOT_FOUND, "Exception: User Not Found in GitHub");
    }

    public UserNotFoundException(Integer status, String message) {
        super(status, message);
    }
}
