package yoon.test.jwtTest2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import yoon.test.jwtTest2.dto.MemberDto;
import yoon.test.jwtTest2.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String join(MemberDto dto){
        memberService.join(dto);
        return "main";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(MemberDto dto){
        return "main";
    }

}
