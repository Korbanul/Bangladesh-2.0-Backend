package com.bangladesh20.backend.Common.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private PageMetaData pagination;

    public static <T> ApiResponse<T> success(String message, T data, PageMetaData pagination) {
//The <T> before the return type: This "declares" that the method is generic.
//a generic method is a method that can work with any data type while still keeping that type safe.
//The ApiResponse<T>: This says the method returns an ApiResponse containing an object of type T.
        return ApiResponse.<T>builder(). //<T> is a Type Hint (or Explicit Type Argument). It tells the Builder exactly what kind of data the ApiResponse is going to hold before you even pass the data to it.
                success(true)
                .message(message)
                .data(data)
                .pagination(pagination)
                .build();
    }

}
