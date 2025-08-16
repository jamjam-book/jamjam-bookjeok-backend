package com.jamjam.bookjeok.domains.member.query.dto;

import com.jamjam.bookjeok.domains.member.command.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private Long memberUid;
    private String memberId;
    private String memberName;
    private String phoneNumber;
    private String email;
    private String nickname;
    private boolean marketingConsent = true;
    private LocalDateTime createdAt;
    private String activityStatus;

}
