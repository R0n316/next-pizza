package ru.alex.nextpizzaapi.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class JwtUtils {
    public static void setJwtToCookies(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getEmailFromJwtInCookies(Cookie[] cookies) {
        Optional<Cookie> jwt = Optional.ofNullable(cookies)
                .stream()
                .flatMap(Arrays::stream)
                .filter(c -> c.getName().equals("jwt"))
                .findFirst();
        assert jwt.isPresent();
        return jwt.get().getValue();
    }
}
