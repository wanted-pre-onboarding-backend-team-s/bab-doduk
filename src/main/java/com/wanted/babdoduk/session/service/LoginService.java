package com.wanted.babdoduk.session.service;

import com.wanted.babdoduk.session.dto.LoginRequestDto;
import com.wanted.babdoduk.session.dto.LoginResultDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {

    public LoginResultDto login(LoginRequestDto loginRequestDto) {
        return null;
    }
}
