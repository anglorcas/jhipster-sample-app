package io.github.jhipster.sample.web.filter;

import static org.mockito.Mockito.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

class SpaWebFilterTest {

    private final SpaWebFilter filter = new SpaWebFilter();

    @Test
    void shouldForwardToIndexHtmlForUnmappedPath() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/some/path");
        when(request.getRequestDispatcher("/index.html")).thenReturn(dispatcher);

        filter.doFilterInternal(request, response, chain);

        verify(dispatcher).forward(request, response);
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    void shouldNotForwardForApiPath() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/api/test");

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(request, never()).getRequestDispatcher(anyString());
    }

    @Test
    void shouldNotForwardForPathWithDot() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/app.js");

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(request, never()).getRequestDispatcher(anyString());
    }

    @Test
    void shouldNotForwardForManagementPath() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/management/health");

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(request, never()).getRequestDispatcher(anyString());
    }
}
