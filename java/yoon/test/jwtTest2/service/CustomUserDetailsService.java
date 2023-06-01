package yoon.test.jwtTest2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yoon.test.jwtTest2.entity.Member;
import yoon.test.jwtTest2.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findById(username);

        if(member==null){
            throw new UsernameNotFoundException(username+"를 찾을 수 없습니다.");
        }

        return member;
    }
}
