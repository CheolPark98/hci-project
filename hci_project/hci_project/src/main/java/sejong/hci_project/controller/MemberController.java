package sejong.hci_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sejong.hci_project.dto.*;
import sejong.hci_project.entity.Member;
import sejong.hci_project.jwt.TokenProvider;
import sejong.hci_project.service.CustomUserDetailsService;
import sejong.hci_project.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/members/new")
    public ResponseEntity<Member> joinMember(@Valid @RequestBody MemberJoinDto member){
        return ResponseEntity.ok(memberService.join(member));
    }

    @PostMapping("/members/login")
    public ResponseEntity<MemberDto> LoginMember(@Valid @RequestBody MemberLoginDto member){
        return ResponseEntity.ok(memberService.login(member));
    }



//
//    @PostMapping("/members/login")
//    public ResponseEntity<JwtTokenDto> JwtLoginMember(@Valid @RequestBody MemberLoginDto member){
//        JwtTokenDto jwtTokenDto = memberService.jwtLogin(member);
//        String accessToken = jwtTokenDto.getAccessToken();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
//        //httpHeaders.setLocation(URI.create("/members/info"));
//       // return  new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
//        return ResponseEntity.ok().headers(httpHeaders).body(jwtTokenDto);
//    }

    @GetMapping("/members/info")
    public ResponseEntity<MemberDto> MemberInfo(){
        return ResponseEntity.ok(memberService.getMemberDto());
    }

    @PostMapping("/members/reissue")
    public ResponseEntity<JwtTokenDto> reissue(@RequestBody JwtTokenDto tokenDto){
        return ResponseEntity.ok(memberService.reissue(tokenDto));
    }
}
