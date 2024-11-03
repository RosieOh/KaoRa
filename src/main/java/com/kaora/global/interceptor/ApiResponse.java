package com.kaora.global.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;

    public static <T> ApiResponse<T> onSuccess(T data) {
        return new ApiResponse<>(data, true, "응답 성공!");
    }

    public static <T> ApiResponse<T> onFailure(String message) {
        return new ApiResponse<>(null, false, message);
    }
}
