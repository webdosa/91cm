package com.nineone.nocm.oauth;

import com.nineone.nocm.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String id;
    private String name;
    private String email;
    private String icon;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String id, String nameAttributeKey, String name,
                           String email, String icon){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.id = id;
        this.name = name;
        this.email = email;
        this.icon = icon;
    }

    public static OAuthAttributes Of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        for (String st : attributes.keySet()){
            log.info("key = {} \t value = {}",st,attributes.get(st));
        }
        if ("naver".equals(registrationId)){
            return ofNaver("id",attributes);
        }
        if ("kakao".equals(registrationId)){
            return ofKakao(userNameAttributeName,attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String id, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("nickname"))
                .email((String) response.get("email"))
                .icon((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey("code")
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .id((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .icon((String) attributes.get("profileImage"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .id((String) response.get("id"))
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .icon((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .icon(icon)
                .build();
    }
}
