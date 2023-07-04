package shop.mtcoding.bank.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto;
import shop.mtcoding.bank.handler.ex.CustomForbiddenException;
import shop.mtcoding.bank.service.AccountService;

import javax.validation.Valid;

import static shop.mtcoding.bank.dto.account.AccountReqDto.*;
import static shop.mtcoding.bank.dto.account.AccountRespDto.*;
import static shop.mtcoding.bank.service.AccountService.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountSaveReqDto accountSaveReqDto,
                                         BindingResult bindingResult,
                                         @AuthenticationPrincipal LoginUser loginUser) {
        AccountSaveRespDto accountSaveRespDto = accountService.register(accountSaveReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 등록 성공", accountSaveRespDto), HttpStatus.CREATED);
    }
    /**
     * V1 : userId를 쿼리파라미터로 받는 경우
     * userId와 로그인된 유저의 id가 일치하는지 검증이 필요하다.
     * */
    @GetMapping("/s/account/{userId}")
    public ResponseEntity<?> findUserAccountListV1(@PathVariable Long userId,
                                                   @AuthenticationPrincipal LoginUser loginUser) {
        if (userId != loginUser.getUser().getId()) {
            throw new CustomForbiddenException("권한이 없습니다");
        }
        AccountListRespDto accountListRespDto = accountService.accountList(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListRespDto), HttpStatus.OK);
    }

    /**
     * V2 : 인증이 필요하고 account 다 주세요!
     * URL 경로가 애매하다. URL만 보면 모든 회원의 계좌를 줘야할 것만 같다.
     */
    @GetMapping("/s/account")
    public ResponseEntity<?> findUserAccountListV2(@AuthenticationPrincipal LoginUser loginUser) {
        AccountListRespDto accountListRespDto = accountService.accountList(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListRespDto), HttpStatus.OK);
    }
    /**
     * V3 : 그냥 로그인한 유저의 계좌를 주세요
     * */
    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccountListV3(@AuthenticationPrincipal LoginUser loginUser) {
        AccountListRespDto accountListRespDto = accountService.accountList(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListRespDto), HttpStatus.OK);
    }

    @DeleteMapping("/s/account/{number}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long number,
                                           @AuthenticationPrincipal LoginUser loginUser) {
        accountService.deleteAccount(number, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 삭제 완료", null), HttpStatus.OK);
    }
}
