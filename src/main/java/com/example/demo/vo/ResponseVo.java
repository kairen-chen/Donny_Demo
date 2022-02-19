package com.example.demo.vo;

public class ResponseVo {
    private String rtnCode;
    private String message;
    private Object data;

    public static ResponseVo build(){
        return new ResponseVo();
    }

    public ResponseVo rtnCode(String rtnCode){
        this.setRtnCode(rtnCode);
        return this;
    }

    public ResponseVo message(String message){
        this.setMessage(message);
        return this;
    }

    public ResponseVo data(Object data){
        this.setData(data);
        return this;
    }



    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
