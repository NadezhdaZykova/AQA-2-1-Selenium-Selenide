
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void validniyTest() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Василий Ильич");
        form.$("[data-test-id=phone] input").setValue("+79279279272");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldDowbleSoname() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Римский-Корсаков Илья Янович");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void withoutName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void uncorrectedName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("5");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    public void phoneValidationWithValue() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Зыкова Надежда Владимировна");
        form.$("[data-test-id=phone] input").setValue("5");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void whithoutPhone() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Зыкова Надежда Владимировна");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void checkboxValidation() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Зыкова Надежда Владимировна");
        form.$("[data-test-id=phone] input").setValue("+79012345678");
        form.$("[role=button]").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

}
