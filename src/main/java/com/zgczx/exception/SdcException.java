package com.zgczx.exception;


import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.UserEnum;
import lombok.Getter;


/**
 * Created by Dqd on 2018/12/11.
 *
 * @author Dqd
 */
@Getter
public class SdcException extends RuntimeException {

    public Integer code;

    public String data;

    public SdcException(Integer code, String message){
        super(message);
        this.code =code;
    }
    public SdcException(ResultEnum resultEnum,String data) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.data = data;
    }

    public SdcException(UserEnum userEnum){
        super(userEnum.getMessage());
        this.code = userEnum.getCode();
    }
}
