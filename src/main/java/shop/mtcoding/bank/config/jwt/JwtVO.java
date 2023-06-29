package shop.mtcoding.bank.config.jwt;
/**
 * SECRET은 노출되면 안된다. (환경변수, 파일 시스템)
 * 리프레시 토큰은 구현하지 않는다.
 * */
public interface JwtVO {
    // HS256 (대칭키)
    public static final String SECRET = "메타코딩";
    // 만료시간 : 7일
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER = "Authorization";
}
