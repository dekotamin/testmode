package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Registration;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginWithActiveValidUser() {
        Registration validActiveUser = generateUser("active");
        $("[data-test-id=login] input").setValue(validActiveUser.getLogin());
        $("[data-test-id=password] input").setValue(validActiveUser.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldSuccessfulLoginWithBlockedValidUser() {
        Registration validActiveUser = generateUser("blocked");
        $("[data-test-id=login] input").setValue(validActiveUser.getLogin());
        $("[data-test-id=password] input").setValue(validActiveUser.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldInvalidLoginForActiveUser() {
        Registration user = generateInvalidLoginForUser();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldInvalidPasswordForActiveUser() {
        Registration user = generateInvalidPasswordForUser();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
}