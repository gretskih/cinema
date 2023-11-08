package ru.job4j.cinema.filter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

class AuthorizationFilterTest {

    @Test
    public void whenPostTicketWithValidUserThenCallNextFilter() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "Ivan");

        FilterChain filterChain = mock(FilterChain.class);
        when(request.getRequestURI()).thenReturn("/tickets");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");

        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        authorizationFilter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    public void whenRequestIndexPageWithNullUserThenCallNextFilter() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        MockHttpSession session = new MockHttpSession();

        FilterChain filterChain = mock(FilterChain.class);
        when(request.getRequestURI()).thenReturn("/");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("GET");

        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        authorizationFilter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }

    /*Проверить что ответ на страницу login*/
    @Test
    public void whenPostTicketWithNullUserThenGetLoginPage() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        MockHttpSession session = new MockHttpSession();

        FilterChain filterChain = mock(FilterChain.class);
        when(request.getRequestURI()).thenReturn("/tickets");
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");

        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        authorizationFilter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain, never()).doFilter(request, response);
    }
}