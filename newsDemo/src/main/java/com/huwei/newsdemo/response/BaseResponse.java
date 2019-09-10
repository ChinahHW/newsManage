package com.huwei.newsdemo.response;

public class BaseResponse<T> {

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回的数据
     */
    private T data;

    public BaseResponse(){
        this.msg = "请求无法执行,请查看参数是否正确";
        this.code = 400;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功通用设置参数方法
     */
    public void success(){
        setMsg("ok");
        setCode(200);
    }

    /**
     * 但数据同一处理成功事件
     * @param t 小返回的数据
     */
    public void success(T t){
        setMsg("ok");
        setCode(200);
        setData(t);
    }
}
