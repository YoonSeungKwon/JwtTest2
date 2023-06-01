package yoon.test.jwtTest2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {

    private String id;

    private String password;

    private String name;
}
