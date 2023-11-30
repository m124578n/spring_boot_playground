package com.example.todolist.util;

import com.example.todolist.exception.error.AuthException;
import com.example.todolist.model.User;
import com.example.todolist.repository.RedisRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String secret = "Mask Spring-Boot";
    public final long expDay = 7;
    private String issuer = "http://com.example.todolist";

    private RedisRepo redisRepo;

    @Autowired
    public JwtUtil(RedisRepo redisRepo) {
        this.redisRepo = redisRepo;
    }

    /**
     * 產生 Token
     */
    public String generateToken(User user) {
        // 設置日期
        LocalDateTime expTime = LocalDateTime.now().plusDays(expDay);
        Instant exp = expTime.atZone(ZoneId.systemDefault()).toInstant();

        // 初始化body
        Claims claims = Jwts.claims();

        // 設置主題
        claims.setSubject(user.getAccount());

        claims.put("id", user.getId());
        claims.put("account", user.getAccount());
        claims.put("name", user.getName());

        claims.setIssuer(issuer);
        claims.setExpiration(Date.from(exp));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 驗證 Token
     */
    public boolean checkToken(String token) throws Exception {

        try {
            // 傳入加密後的 token
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            isExpired(token);

            isExistInRedis(token);
            return true;
        } catch (Exception e) {
            throw new AuthException().AuthFail(e);
        }
    }

    /**
     * 取得 request 中的 token
     */
    public String getToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 取得token裡的id
     */
    public Long extractID(String token) {
        Object id = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("id");
        return Long.valueOf(id.toString());
    }


    /**
     *  取得token存放的資料
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 從token取得指定的值
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 驗證 Token 是否過期
     */
    public void isExpired(String token) throws Exception {
        // 取得到期日
        Date exp = extractClaim(token, Claims::getExpiration);
        if (exp.before(new Date())) {
            throw new AuthException().AuthExpired(new IllegalAccessException());
        }
    }

    /**
     * 檢查Token是否有在Redis中
     */
    public void isExistInRedis(String token) throws Exception {
        // 取出token中的使用者編號
        Long id = extractID(token);

        // 查詢redis
        String tokenInRedis = redisRepo.get(id.toString());

        // 比對token是否相同
        if (!tokenInRedis.equals(token)) {
            throw new IllegalAccessException("權杖失效");
        }

    }

}
