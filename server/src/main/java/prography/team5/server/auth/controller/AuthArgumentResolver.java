package prography.team5.server.auth.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import prography.team5.server.auth.service.AuthService;
import prography.team5.server.auth.service.dto.Accessor;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthService authService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(Accessor.class)
                && parameter.hasParameterAnnotation(AuthRequired.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
            throws Exception {
        final String authorizationHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        //todo: 어드민 인증
        final AuthRequired annotation = Objects.requireNonNull(parameter.getParameterAnnotation(AuthRequired.class));
        if(!annotation.required() && authorizationHeader == null) {
            return Accessor.createAnonymousAccessor();
        }
        final String token = HeaderUtils.extractToken(authorizationHeader, TOKEN_TYPE);
        final Accessor accessor = authService.verifyUserFromToken(token);
        log.info("Is accessor anonymous? {}", accessor.isAnonymous());
        return accessor;
    }
}
