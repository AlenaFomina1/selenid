package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestCard {
    public String generateDate(int planningDate) {
        return LocalDate.now().plusDays(planningDate).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldTestCard(){
        String planningDate = generateDate(4);
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79111111111");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(18));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}
