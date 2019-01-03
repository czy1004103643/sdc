package com.zgczx.controller;

import com.zgczx.config.ProjectUrlConfig;
import com.zgczx.constant.CookieConstant;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.service.UserService;
import com.zgczx.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
/**
 * 微信验证控制器
 *
 * @author Jason
 */
public class WeChatController {


    private final WxMpService wxMpService;

    private final ProjectUrlConfig projectUrlConfig;

    private final UserService userService;

    @Autowired
    WeChatController(WxMpService wxMpService,ProjectUrlConfig projectUrlConfig,UserService userService){
        this.wxMpService = wxMpService;
        this.projectUrlConfig = projectUrlConfig;
        this.userService = userService;
    }

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl,
                            @RequestParam("role") Integer role){

        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWeChatMpAuthorize() + "/wechat/userInfo?role="+role;
        String redirectUrl =  wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】 获取code，result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl,
                           @RequestParam("role") Integer role){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        WxMpUser wxMpUser = new WxMpUser();
        try{
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        }catch (WxErrorException e){
            log.error("【微信网页授权】 {}",e);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        String nickname = wxMpUser.getNickname();
        String headImgUrl = wxMpUser.getHeadImgUrl();

        // 首次创建用户信息
        if(role.equals(UserService.STU_ROLE)){
            // 创建学生账号
            userService.createStuBase(openid,nickname,headImgUrl);
        }else if(role.equals(UserService.TEA_ROLE)){
            // 创建教师账号
            userService.createTeaBase(openid,nickname,headImgUrl);
        }
        return "redirect:"+returnUrl+"?openid="+openid;

    }

    @GetMapping("/authorizeByOpenid")
    public String authorizeByOpenid(@PathParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {

        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWeChatMpAuthorize() + "/wechat/userInfoByOpenid";
        String redirectUrl =  wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl,"utf-8"));
        log.info("【微信网页授权 仅获取openid】 获取code，result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/userInfoByOpenid")
    public String userInfoByOpenid(@RequestParam("code") String code,
                                   @RequestParam("state") String returnUrl,
                                   HttpServletResponse response) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessTokenByOpenid;
        try {
            wxMpOAuth2AccessTokenByOpenid = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权 仅获取openid】 {}", e);
            throw new SdcException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessTokenByOpenid.getOpenId();

        Integer expire = CookieConstant.EXPIRE;

        //2. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,openid,expire);

        return "redirect:" + returnUrl;
    }
}
