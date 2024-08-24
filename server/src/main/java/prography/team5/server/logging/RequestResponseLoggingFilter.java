package prography.team5.server.logging;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(
                (HttpServletResponse) response);


        log.info("Request ID: {}, Request URL: {}", req.getRequestId(), req.getRequestURL());

        chain.doFilter(request, responseWrapper);  // 다음 필터 또는 서블릿으로 요청을 전달

        String responseBody = responseWrapper.getCaptureAsString();

        log.info("Request ID: {}, Response Status: {}, Response Body: {}", req.getRequestId(),
                responseWrapper.getStatus(), responseBody);
    }

    // HttpServletResponseWrapper를 이용해 Response Body를 가로채기 위한 클래스
    private class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {

        private ByteArrayOutputStream capture;
        private ServletOutputStream output;
        private PrintWriter writer;

        public CustomHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
            super(response);
            capture = new ByteArrayOutputStream();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (output == null) {
                output = new CaptureOutputStream();
            }
            return output;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            if (writer == null) {
                writer = new PrintWriter(getOutputStream());
            }
            return writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (writer != null) {
                writer.flush();
            }
            if (output != null) {
                output.flush();
            }
        }

        public String getCaptureAsString() throws IOException {
            flushBuffer();
            return new String(capture.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
        }

        private class CaptureOutputStream extends ServletOutputStream {

            private ByteArrayOutputStream byteArrayOutputStream = capture;

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }

            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }
        }
    }
}

