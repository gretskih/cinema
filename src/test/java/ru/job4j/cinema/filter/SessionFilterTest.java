package ru.job4j.cinema.filter;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.job4j.cinema.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

/**
 * Тест - добавление пользователя в модель
 */
class SessionFilterTest {

    private final SessionFilter sessionFilter = new SessionFilter();
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain filterChain = mock(FilterChain.class);

    /**
     * Добавление в модель пользователя - Гость.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void whenRequestAnyPageWithNullUserThenSetUserGuest() throws ServletException, IOException {
        var expectedUser = new User();
        expectedUser.setFullName("Гость");
        var session = mock(HttpSession.class);
        var userCaptor = ArgumentCaptor.forClass(User.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        doNothing().when(request).setAttribute(any(), userCaptor.capture());

        sessionFilter.doFilter(request, response, filterChain);

        var actualUser = userCaptor.getValue();
        assertThat(actualUser.getFullName()).isEqualTo(expectedUser.getFullName());
    }

    /**
     * Добавление в модель пользователя - User.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void whenRequestAnyPageWithAnyUserThenSetValidUser() throws ServletException, IOException {
        var expectedUser = new User();
        expectedUser.setFullName("User");
        var session = mock(HttpSession.class);
        var userCaptor = ArgumentCaptor.forClass(User.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(expectedUser);
        doNothing().when(request).setAttribute(any(), userCaptor.capture());

        sessionFilter.doFilter(request, response, filterChain);

        var actualUser = userCaptor.getValue();
        assertThat(actualUser.getFullName()).isEqualTo(expectedUser.getFullName());
    }
}