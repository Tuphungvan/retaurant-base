package com.pvt.order.web.advice;

import com.pvt.order.web.rest.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class SuccessResponseAdvice implements ResponseBodyAdvice<Object> {
    private final ObjectMapper mapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String path = request.getURI().getPath();

        // endpoint documents
        if (path.startsWith("/v3/") || path.startsWith("/management") || path.startsWith("/swagger") || path.startsWith("/docs")) {
            return body;
        }
        // body đã đi qua exception handle
        if (body instanceof ApiResponse) return body;

        // bỏ qua no content
        if (response instanceof ServletServerHttpResponse selvRes)
            if (selvRes.getServletResponse().getStatus() == HttpStatus.NO_CONTENT.value())
                return body;

        // map ApiResponse
        Object resData = (body instanceof Page<?> page) ? ApiResponse.ofPage(page) : ApiResponse.of(body);

        // xử lý string response
        if (selectedConverterType.isAssignableFrom(StringHttpMessageConverter.class) || body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            try {
                return mapper.writeValueAsString(resData);
            } catch (JacksonException e) {
                log.error("Lỗi chuyển đổi ApiResponse", e);
                return body;
            }
        }
        return resData;
    }
}
