package ru.job4j.cinema.filter;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class AuthorizationFilterTest {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private MockHttpSession session = new MockHttpSession();
    private FilterChain filterChain = mock(FilterChain.class);
    private AuthorizationFilter authorizationFilter = new AuthorizationFilter();

    @Test
    public void whenPostTicketWithValidUserThenCallNextFilter() throws ServletException, IOException {
        session.setAttribute("user", "Ivan");
        when(request.getRequestURI()).thenReturn("/tickets");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");

        authorizationFilter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    public void whenRequestIndexPageWithNullUserThenCallNextFilter() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("GET");

        authorizationFilter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    public void whenPostTicketWithNullUserThenGetLoginPage() throws ServletException, IOException {
        String expectedRedirect = request.getContextPath() + "/users/login";
        var redirectCaptor = ArgumentCaptor.forClass(String.class);
        when(request.getRequestURI()).thenReturn("/tickets");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");

        doNothing().when(response).sendRedirect(redirectCaptor.capture());
        authorizationFilter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain, never()).doFilter(request, response);
        assertThat(redirectCaptor.getValue()).isEqualTo(expectedRedirect);
    }
}