package yoon.test.jwtTest2.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yoon.test.jwtTest2.dto.MemberDto;
import yoon.test.jwtTest2.entity.Member;
import yoon.test.jwtTest2.jwt.JwtTokenProvider;
import yoon.test.jwtTest2.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public void join(MemberDto dto){
        System.out.println(passwordEncoder.encode(dto.getPassword()));
        Member member = Member.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .role("USER")
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public String login(String id, String password){
        Member member = memberRepository.findById(id);

        if(member == null)
            throw new IllegalArgumentException(id + "를 찾을 수 없습니다.");

        if(!passwordEncoder.matches(password, member.getPassword()))
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");

        return jwtTokenProvider.createToken(member.getUsername(), member.getPassword());
    }

}
