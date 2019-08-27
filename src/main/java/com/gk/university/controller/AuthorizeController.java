package com.gk.university.controller;

import com.gk.university.dto.AccessTokenDTO;
import com.gk.university.dto.GithubUser;
import com.gk.university.model.User;
import com.gk.university.provider.GithubProvider;
import com.gk.university.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        System.out.println("开始登录" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        System.out.println("开始获取token" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
        // 从github获取accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        // 从github获取用户信息
        System.out.println("获取token，开始获取user" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println("获取user" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
        if (githubUser != null) {
            // 登录成功
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userService.insertOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            System.out.println("完成登录" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
