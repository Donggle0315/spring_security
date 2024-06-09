package com.example.springsecurity.OAuth2.service;

import com.example.springsecurity.OAuth2.entity.CustomOAuth2User;
import com.example.springsecurity.OAuth2.entity.UserOAuthEntity;
import com.example.springsecurity.OAuth2.repository.UserOAuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {
    private final UserOAuthRepository userOAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

//        try {
//            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        UserOAuthEntity userEntity = null;
        String userId = null;
        String email = "example@mail.com";

        if (oauthClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            userEntity = new UserOAuthEntity(userId, email, "kakao");
        }

        if (oauthClientName.equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id").substring(0, 14);
            email = responseMap.get("email");
            userEntity = new UserOAuthEntity(userId, email, "naver");
        }

        userOAuthRepository.save(userEntity);

        return new CustomOAuth2User(userId);
    }
}
