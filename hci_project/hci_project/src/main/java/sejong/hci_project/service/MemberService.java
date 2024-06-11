package sejong.hci_project.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sejong.hci_project.dto.*;
import sejong.hci_project.entity.Member;
import sejong.hci_project.entity.RefreshToken;
import sejong.hci_project.exception.*;
import sejong.hci_project.jwt.TokenProvider;
import sejong.hci_project.repository.MemberRepository;
import sejong.hci_project.repository.RefreshTokenRepository;
import sejong.hci_project.util.SecurityUtil;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;



    public Member join(MemberJoinDto member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateEmailException();
        }
        Member joinMember = new Member();

        member.setPassword(passwordEncode(member.getPassword()));
        BeanUtils.copyProperties(member, joinMember);


        return memberRepository.save(joinMember);
    }

    public MemberDto login(MemberLoginDto memberLoginDto) {
        Member findMember = LoginValidate(memberLoginDto);

        MemberDto loginedMember = new MemberDto();
        BeanUtils.copyProperties(findMember, loginedMember);

        return loginedMember;
    }

    private Member LoginValidate(MemberLoginDto memberLoginDto) {
        Optional<Member> findByEmail = memberRepository.findByEmail(memberLoginDto.getEmail());
        if (findByEmail.isEmpty()) {
            throw new NoSuchEmaillException();
        }
        Member findMember = findByEmail.get();
//        String encodedPassword = passwordEncode(memberLoginDto.getPassword());
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        if (!bCryptPasswordEncoder.matches(memberLoginDto.getPassword(), findMember.getPassword())) {
//            log.info(findMember.getPassword());
//            log.info(encodedPassword);
//            throw new WrongPasswordException();
//        }
        return findMember;
    }


    public String passwordEncode(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Transactional
    public JwtTokenDto jwtLogin(MemberLoginDto member) {
        UsernamePasswordAuthenticationToken authenticationToken = member.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtTokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    @Transactional(readOnly = true)
    public MemberDto getMemberDto(){

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Optional<Member> byId = memberRepository.findById(currentMemberId);
        MemberDto memberDto=new MemberDto();
        BeanUtils.copyProperties(byId.get(),memberDto);
        return memberDto;
    }
    @Transactional
    public JwtTokenDto reissue(JwtTokenDto tokenDto){
        if (!tokenProvider.validateToken(tokenDto.getRefreshToken())){
            throw new InvalidTokenException();
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenDto.getAccessToken());

        RefreshToken refreshToken= refreshTokenRepository.findByKey(authentication.getName()).orElseThrow(()->new LogOutTokenException());

        if (!refreshToken.getValue().equals(tokenDto.getRefreshToken())){
            throw new NotMatchedToken();
        }

        JwtTokenDto jwtTokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken=refreshToken.updateValue(jwtTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return jwtTokenDto;
    }


}
