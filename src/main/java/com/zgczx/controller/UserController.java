package com.zgczx.controller;


import com.zgczx.VO.ResultVO;
import com.zgczx.constant.CookieConstant;
import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.form.StuInfoForm;
import com.zgczx.form.TeaInfoForm;
import com.zgczx.service.UserService;
import com.zgczx.utils.CookieUtil;
import com.zgczx.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: UserController
 * @Author: Jason
 * @Date: 2019/1/1 14:58
 * @Description:
 */
@RestController
@Slf4j
public class UserController {

    private  final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/login")
    public String login(@RequestParam("openid") String openid,
                            @RequestParam("role") Integer role,
                            HttpServletResponse response,
                            Map<String,Object> map){

        //1. openid 去和数据库里匹配
        if(role == 1){
            StuBase stuBase = userService.findStuBaseByOpenid(openid);
        }else if(role == 2){
            TeaBase teaBase = userService.findTeaBaseByOpenid(openid);
        }else{
            //身份信息非法
            throw new SdcException(1,"message");
        }

        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.EXPIRE;

        //2. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);

        return "";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        if (cookie != null){
            //2. 清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        return "" ;
    }

    /**
     *
     *  学生根据openid创建表单信息
     * @param stuInfoForm 学生信息表单信息
     * @param bindingResult 表单验证结果
     * @param stuOpenid 学生微信唯一凭证
     * @return ResultVO
     */
    @PostMapping("/registerStuBaseByOpenid")
    public ResultVO<?> registerStuBaseByOpenid(@Valid StuInfoForm stuInfoForm, BindingResult bindingResult,
                                @RequestParam("stuOpenid") String stuOpenid ){
        if(bindingResult.hasErrors()){
            log.error("【学生注册】参数不正确，stuInfoForm={}",stuInfoForm.toString());
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        if(StringUtils.isEmpty(stuOpenid)||stuOpenid.equals("undefined")){
            log.error("【学生注册】stuOpenid 为空,stuOpenid={}",stuOpenid);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),ResultEnum.PARAM_EXCEPTION.getMessage());
        }

        StuBase stuBase = userService.registerStuBaseByOpenid(stuOpenid,stuInfoForm);
        return ResultVOUtil.success(stuBase);
    }

    @PostMapping("/registerTeaBaseByOpenid")
    public ResultVO<?> registerTeaBaseByOpenid(@Valid TeaInfoForm teaInfoForm,BindingResult bindingResult,
                                               @RequestParam("teaOpenid") String teaOpenid){
        if(bindingResult.hasErrors()){
            log.error("【教师注册】参数不正确，teaInfoForm={}",teaInfoForm.toString());
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        if(StringUtils.isEmpty(teaOpenid)||("undefined").equals(teaOpenid)){
            log.error("【教师注册】teaOpenid 为空,teaOpenid={}",teaOpenid);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION.getCode(),ResultEnum.PARAM_EXCEPTION.getMessage());
        }

        TeaBase teaBase = userService.registerTeaBaseByOpenid(teaOpenid,teaInfoForm);

        return ResultVOUtil.success(teaBase);

    }


}