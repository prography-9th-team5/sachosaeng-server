package prography.team5.server.auth.service;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import prography.team5.server.auth.service.dto.EmailRequest;
import prography.team5.server.auth.service.dto.JoinRequest;
import prography.team5.server.user.domain.SocialType;

@Aspect
@Component
public class AppleAspect {

    private static final String APPLE_EMAIL = "@apple.scs";

    @Before(value = "execution(* prography.team5.server.auth.service.AuthService.joinNewUser(..)) && args(joinRequest, socialType)", argNames = "joinRequest,socialType")
    public void modifyEmailForAppleJoin(JoinRequest joinRequest, SocialType socialType) {
        if (socialType == SocialType.APPLE) {
            String appleEmail = joinRequest.email() + APPLE_EMAIL;
            joinRequest.updateEmail(appleEmail);
        }
    }

    @Before(value = "execution(* prography.team5.server.auth.service.AuthService.login(..)) && args(emailRequest, socialType, device)", argNames = "emailRequest,socialType,device")
    public void modifyEmailForAppleLogin(EmailRequest emailRequest, SocialType socialType, final String device) {
        if (socialType == SocialType.APPLE) {
            String appleEmail = emailRequest.email() + APPLE_EMAIL;
            emailRequest.updateEmail(appleEmail);
        }
    }
}
