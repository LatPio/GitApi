package com.example.GitApi.exeption;

import com.example.GitApi.exeption.config.GlobalErrorCode;

public class HeaderNotSupportedException extends ErrorWrapper {

    public HeaderNotSupportedException() {
        super(GlobalErrorCode.ERROR_HEADER_NOT_SUPPORTED, "Exception: application/xml not supported");
    }

    public HeaderNotSupportedException(Integer status, String message) {
        super(status, message);
    }
}
