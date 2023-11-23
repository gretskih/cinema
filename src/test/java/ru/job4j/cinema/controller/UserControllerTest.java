package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тесты для слоя: Контроллер
 * RequestMapping("/users")
 */
class UserControllerTest {

    private UserService userService;
    private UserController userController;

    /**
     * Инициализация mock заглушками полей userService, userController
     */
    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    /**
     * Получение страницы users/register
     * Метод String getRegistrationPage()
     */
    @Test
    public void whenRequestRegistrationThenGetRegistrationPage() {
        var view = userController.getRegistrationPage();
        assertThat(view).isEqualTo("users/register");
    }

    /**
     * Сохранение нового пользователя и перенаправление на страницу index.
     * Метод String register(Model model, @ModelAttribute User user)
     */
    @Test
    public void whenPostUserRegisterThenGetIndexPage() {
        User user = new User(2, "User", "user@user.us", "1111");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(user).isEqualTo(actualUser);
    }

    /**
     * Сохранение уже существующего пользователя и перенаправление на страницу errors/404.
     * Метод String register(Model model, @ModelAttribute User user)
     */
    @Test
    public void whenPostEmptyUserRegisterThenGetErrorPage() {
        User user = new User(2, "User", "user@user.us", "1111");
        when(userService.save(any())).thenReturn(Optional.empty());
        var expectedMessage = String.format("Пользователь с почтой %s уже существует.", user.getEmail());

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/register");
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    /**
     * Получение страницы users/login
     * Метод String getLoginPage()
     */
    @Test
    public void whenRequestLoginThenGetLoginPage() {
        var view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    /**
     * Валидация существующего пользователя, сохранение пользователя в сессии, перенаправление на страницу index.
     * Метод String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request)
     */
    @Test
    public void whenPostLoginUserThenGetVacanciesPage() {
        User user = new User(2, "User", "user@user.us", "1111");
        HttpServletRequest request = new MockHttpServletRequest();
        var emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        var passwordArgumentCaptor = ArgumentCaptor.forClass(String.class);
        when(userService.findByEmailAndPassword(emailArgumentCaptor.capture(), passwordArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualEmail = emailArgumentCaptor.getValue();
        var actualPassword = passwordArgumentCaptor.getValue();
        var actualUser = request.getSession().getAttribute("user");

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(actualEmail).isEqualTo(user.getEmail());
        assertThat(actualPassword).isEqualTo(user.getPassword());
        assertThat(actualUser).isEqualTo(user);
    }

    /**
     * Валидация отсутствующего пользователя, перенаправление на страницу users/login.
     * Метод String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request)
     */
    @Test
    public void whenPostErrorLoginUserThenGetLoginsPage() {
        String expectedMessage = "Почта или пароль введены неверно";
        User user = new User(2, "User", "user@user.us", "1111");
        HttpServletRequest request = new MockHttpServletRequest();
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    /**
     * Удаление данных о пользователе в сессии, перенаправление на страницу /users/login
     * Метод String logout(HttpSession session)
     */
    @Test
    public void whenRequestLogoutThenGetLoginsPage() {
        MockHttpSession session = new MockHttpSession();

        var view = userController.logout(session);
        var actualInvalid = session.isInvalid();

        assertThat(actualInvalid).isEqualTo(true);
        assertThat(view).isEqualTo("redirect:/users/login");
    }
}