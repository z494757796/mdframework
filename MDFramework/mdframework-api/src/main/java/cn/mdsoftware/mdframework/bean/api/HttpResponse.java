package cn.mdsoftware.mdframework.bean.api;

import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 返回结构
 * @author Jax
 *
 * @param <T>
 */
public class HttpResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //保留,适应原有架构
    public HttpResponse(){

    }

    private Integer code = HttpResponseEnum.SUCCESS.code();
    private String message = HttpResponseEnum.SUCCESS.message();
    private T data;

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    //保留,适应原有架构
    public void setHttpResponseEnum(HttpResponseEnum obj) {
        this.code = obj.code();
        this.message = obj.message();
    }

    private HttpResponse(int status) {
        this.code = status;
    }

    private HttpResponse(int status, T data) {
        this.code = status;
        this.data = data;
    }

    private HttpResponse(int status, String msg, T data) {
        this.code = status;
        this.message = msg;
        this.data = data;
    }

    public HttpResponse(int status, String msg) {
        this.code = status;
        this.message = msg;
    }
    //使之不在 json 序列化当中
    @JsonIgnore
    public boolean isSuccess(){
        return this.code == HttpResponseEnum.SUCCESS.code();
    }

    public static <T> HttpResponse<T> createBySuccess(){
        return new HttpResponse<T>(HttpResponseEnum.SUCCESS.code());
    }

    public static <T> HttpResponse<T> createBySuccess(String msg){
        return new HttpResponse<T>(HttpResponseEnum.SUCCESS.code(),msg);
    }

    public static <T> HttpResponse<T> createBySuccess(T data){
        return  new HttpResponse<T>(HttpResponseEnum.SUCCESS.code(),HttpResponseEnum.SUCCESS.message(),data);
    }

    public static <T> HttpResponse<T> createBySuccess(String msg, T data){
        return new HttpResponse<T>(HttpResponseEnum.SUCCESS.code(),msg,data);
    }

    public static <T> HttpResponse<T> createByError(){
        return new HttpResponse<T>(HttpResponseEnum.SYSTEM_ERROR.code(),HttpResponseEnum.SYSTEM_ERROR.message());
    }

    public static <T> HttpResponse<T> createByErrorMessage(String errorMessage){
        return new HttpResponse<T>(HttpResponseEnum.SYSTEM_ERROR.code(),errorMessage);
    }

    public static <T> HttpResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage){
        return new HttpResponse<T>(errorCode,errorMessage);
    }

    public static <T> HttpResponse<T> createByErrorResponseEnum(HttpResponseEnum httpResponseEnum){
        return new HttpResponse<T>(httpResponseEnum.code(),httpResponseEnum.message());
    }

    @Override
    public String toString() {
        return "HttpResponse [code=" + code + ", message=" + message + ", data=" + data + "]";
    }
}
