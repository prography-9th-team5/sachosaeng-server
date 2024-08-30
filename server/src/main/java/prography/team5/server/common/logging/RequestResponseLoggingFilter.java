package prography.team5.server.common.logging;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
                (HttpServletResponse) response);

        log.info("Request ID: {}, Request URL: {}", req.getRequestId(), req.getRequestURL());

        chain.doFilter(request, responseWrapper);

        final String responseBody = new String(responseWrapper.getContentAsByteArray());

        // 응답의 Content-Type 헤더를 확인하여 JSON인지 판별
        String contentType = responseWrapper.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            log.info("Request ID: {}, Response Status: {}, Response Body: {}", req.getRequestId(), responseWrapper.getStatus(), responseBody);
        } else {
            log.info("Request ID: {}, Response Status: {}", req.getRequestId(), responseWrapper.getStatus());
        }

        responseWrapper.copyBodyToResponse();
    }
}
