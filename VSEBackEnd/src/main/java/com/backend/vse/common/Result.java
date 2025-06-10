package com.backend.vse.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
    * 用法示例：
    *    Result<String> result = Result.success("操作成功");
    *    返回的Result对象：{ success: true, code: 0, msg: "success", data: "操作成功" }

    *    Result<Object> result = Result.fail(404, "资源未找到");
    *    返回的Result对象：{ success: false, code: 404, msg: "资源未找到", data: null }
    */
    private boolean success;
    private int code;
    private String msg;
    private T data; // 泛型类型 T，用于返回操作相关的数据，支持任意类型的数据

    public static <T> Result<T> success(T data) {
        return new Result<>(true, 200, "success", data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(false, code, msg, null);
    }
}
