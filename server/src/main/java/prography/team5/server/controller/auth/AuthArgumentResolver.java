package prography.team5.server.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import prography.team5.server.service.auth.AuthService;
import prography.team5.server.service.auth.dto.Accessor;

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
        final String token = HeaderUtils.extractToken(authorizationHeader, TOKEN_TYPE);
        return authService.verifyUserFromToken(token);
    }
}
