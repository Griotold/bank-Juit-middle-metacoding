package shop.mtcoding.bank.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

import shop.mtcoding.bank.dto.user.UserReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.util.Optional;

import static shop.mtcoding.bank.dto.user.UserReqDto.*;
import static shop.mtcoding.bank.dto.user.UserRespDto.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(getClass());

    // 서비스에는 DTO를 받고 DTO를 응답한다.
    @Transactional
    public JoinRespDto join(JoinReqDto joinReqDto) {
        // 1. 동일 유저네임 존개 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()) {
            // 유저 네임 중복!
            throw new CustomApiException("동일한 username이 존재합니다.");
        }

        // 2. 패스워드 인코딩
        // userPs = user Persistence -> 영속 컨텍스트에 들어간 객체
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new JoinRespDto(userPS);

    }



}
