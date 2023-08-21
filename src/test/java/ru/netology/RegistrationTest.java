package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1800x1100";
        open("http://localhost:9999/");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByAccountNumberDOMModificationTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldRegisterErrorEmptyFieldCityTest() {
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]"), Duration.ofSeconds(15)
            .shouldBe(visible);
    }
    
@Test
    void shouldRegisterErrorNotFindCityTest() {
        $("[placeholder='Город']").setValue("Мыски");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Доставка в выбранный город недоступна')]"), Duration.ofSeconds(15)
            .shouldBe(visible);
    }

        @Test
    void shouldRegisterErrorEmptyFieldNameTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]"), Duration.ofSeconds(15)
            .shouldBe(visible);
    }

        @Test
    void shouldRegisterErrorFieldPhoneNoMoreThirteenCharacterTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("+793456789012");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]"), Duration.ofSeconds(15)
            .shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorFieldPhoneNoPlusSignTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("79345678901");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]"), Duration.ofSeconds(15)
            .shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorCheckboxIsNotSetTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("+79345678901");
        //$("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Я соглашаюсь с условиями обработки')]"), Duration.ofSeconds(15)
            .shouldBe(visible);
    }
}
