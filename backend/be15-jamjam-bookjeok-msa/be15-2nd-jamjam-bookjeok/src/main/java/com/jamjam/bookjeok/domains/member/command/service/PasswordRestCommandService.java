package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordModifyRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordResetLinkRequest;

public interface PasswordRestCommandService {

    String requestPasswordReset(PasswordResetLinkRequest passwordResetLinkRequest);

    void resetPassword(PasswordModifyRequest passwordModifyRequest);

}
