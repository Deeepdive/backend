package deepdive.backend.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfo {

    private Integer id;
    private String email;
    private String picture;

}
