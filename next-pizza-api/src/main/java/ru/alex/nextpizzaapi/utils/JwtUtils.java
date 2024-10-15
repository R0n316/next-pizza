package ru.alex.nextpizzaapi.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtils {
    public static void setJwtTokenToCookies(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt-token", token);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
