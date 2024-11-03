package com.kaora.global.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest<T> {

    private T data;
    private String requestId;

    public boolean isValid() {
        return data != null;
    }
}
