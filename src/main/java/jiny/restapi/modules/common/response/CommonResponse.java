package jiny.restapi.modules.common.response;

public enum CommonResponse {
    SUCCESS(0,"성공하였습니다."),
    FAIL(-1,"실패하였습니다.");

    int code;
    String msg;

    CommonResponse(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
