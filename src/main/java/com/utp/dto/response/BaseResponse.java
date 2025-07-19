package com.utp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private boolean success;
    private String message;
    
    public static BaseResponse success(String message) {
        return new BaseResponse(true, message);
    }
    
    public static BaseResponse error(String message) {
        return new BaseResponse(false, message);
    }
}
