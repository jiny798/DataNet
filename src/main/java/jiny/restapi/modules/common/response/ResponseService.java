package jiny.restapi.modules.common.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    private void setSuccessData(CommonResult result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailData(CommonResult result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    public <T> SingleResult<T> getSingleResult(T data){
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessData(result);
        return result;
    }

    public <T> ListResult<T> getSingleResult(List<T> data){
        ListResult<T> result = new ListResult<>();
        result.setList(data);
        setSuccessData(result);
        return result;
    }

    public CommonResult getSuccessResult(){
        CommonResult result = new CommonResult();
        setSuccessData(result);
        return result;
    }

    public CommonResult getSuccessResult(int code,String msg){
        CommonResult result = new CommonResult();
        result.setSuccess(true);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public CommonResult getFailResult(){
        CommonResult result = new CommonResult();
        setFailData(result);
        return result;
    }
    public CommonResult getFailResult(int code,String msg){
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
