package com.wanted.babdoduk.session.service;

import com.wanted.babdoduk.session.dto.AccessTokenReissuanceRequestDto;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReissueAccessTokenService {

    public AccessTokenReissuanceResponseDto reissueAccessToken(
            AccessTokenReissuanceRequestDto accessTokenReissuanceRequestDto
    ) {
        return null;
    }
}
