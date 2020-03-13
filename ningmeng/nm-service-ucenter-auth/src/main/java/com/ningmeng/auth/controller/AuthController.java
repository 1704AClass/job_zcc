package com.ningmeng.auth.controller;

import com.ningmeng.api.auth.AuthControllerApi;
import com.ningmeng.auth.service.AuthService;
import com.ningmeng.framework.domain.ucenter.ext.AuthToken;
import com.ningmeng.framework.domain.ucenter.request.LoginRequest;
import com.ningmeng.framework.domain.ucenter.response.AuthCode;
import com.ningmeng.framework.domain.ucenter.response.JwtResult;
import com.ningmeng.framework.domain.ucenter.response.LoginResult;
import com.ningmeng.framework.exception.ExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.framework.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class AuthController implements AuthControllerApi {

    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Autowired
    private AuthService authService;

    @Override
    @PostMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest) {

        if(loginRequest==null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }
        //校验账号是否输入
        if("".equals(loginRequest.getUsername())){
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        //校验密码是否输入
        if("".equals(loginRequest.getPassword())){
            ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
        //如果登录成功    保存cookie
        AuthToken authToken = authService.login(loginRequest.getUsername(),loginRequest.getPassword(),clientId,clientSecret);
        String  access_token = authToken.getAccess_token();
        //将访问令牌存储到cookie
        this.saveCookie(access_token);
        return new LoginResult(CommonCode.SUCCESS,access_token);
    }

    private void saveCookie(String token){
        //添加cookie认证令牌，最后一个参数设置为false,表示允许浏览器获取
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, cookieMaxAge, false);
    }

    //退出登陆，删除redis，清除cookie
    @Override
    @PostMapping("/userlogout")
    public ResponseResult logout() {
        //取出身份令牌
        String uid = getTokenFormCookie();
        //删除redis中token
        authService.delToken(uid);
        //清除cookie
        clearCookie(uid);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //清除cookie
    private void clearCookie(String token){
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, 0, false);
    }


    //jwt令牌查询信息,从cooike中获取令牌
    @Override
    @GetMapping("/userjwt")
    public JwtResult userjwt() {
        String access_token=getTokenFormCookie();
        AuthToken authTokenn = authService.getUserToken(access_token);
        if (authTokenn==null){
            return new JwtResult(CommonCode.FAIL,null);
        }
        return new  JwtResult(CommonCode.SUCCESS,authTokenn.getJwt_token());
    }

    //从cooike中取出来
    public String getTokenFormCookie(){
        Map<String, String> cookieMap = CookieUtil.readCookie(request,"uid");
        String access_token = cookieMap.get("uid");
        return access_token;
    }

}
