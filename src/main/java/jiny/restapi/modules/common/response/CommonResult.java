package jiny.restapi.modules.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonResult {
    private boolean success;
    private int code;
    private String msg;
}
