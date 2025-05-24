/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Utils;

/**
 *
 * @author Rashid
 */
public class Response {
    String message;
    int status;
    Object object;
    private int code;
    private Object data;
    private StatusCode statusCode;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Response(String message, int status, Object object) {
        this.message = message;
        this.status = status;
        this.object = object;
    }
    
    public Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public Response(StatusCode status, String message) {
        this.statusCode = status;
        this.message = message;
        this.data = null;
    }
    
    public Response(StatusCode status, Object data) {
        this.statusCode = status;
        this.data = data;
        this.message = null;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Object getObject() {
        return object;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
    
    public StatusCode getStatusCode() {
        return statusCode;
    }
    
}
