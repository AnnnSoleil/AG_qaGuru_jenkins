package tests;

import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

@Story("Registration form")
public class RegistrationWithPageObjectTests extends TestBase {

    @Test
    @DisplayName("Successful Registration")
    void successfulRegistrationTest() {
        step("Open registration page", () ->
                registrationPage.openPage());
        step("Fill registration form", () -> {
            registrationPage
                    .setFirstName("Alex")
                    .setLastName("Egorov")
                    .setEmail("alex@egorov.com")
                    .setGender("Other")
                    .setUserNumber("1234567890")
                    .setDateOfBirth("30", "July", "2008");
            $("#subjectsInput").setValue("Math").pressEnter();
            $("#hobbiesWrapper").$(byText("Sports")).click();
            $("#uploadPicture").uploadFromClasspath("img/1.png");
            $("#currentAddress").setValue("Some address 1");
            $("#state").click();
            $("#stateCity-wrapper").$(byText("NCR")).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText("Delhi")).click();
            $("#submit").click();
        });
        step("Check registration form results data", () -> {
            step("Check registration form results component appears", () -> { // or move to pageobject step
                $(".modal-dialog").should(appear);
                $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            });
            registrationPage.checkResult("Student Name", "Alex Egorov")
                    .checkResult("Student Email", "alex@egorov.com");
        });
    }

    @Test
    @DisplayName("Broken Registration")
    void brokenRegistrationTest() {
        step("Open registration page", () ->
                registrationPage.openPage());

        step("Fill registration form", () -> {
            registrationPage
                    .setFirstName("Alex")
                    .setLastName("Egorov")
                    .setGender("Other")
                    .setUserNumber("1234567890")
                    .setEmail("alex111@egorov.com");
            $("#submit").click();
        });

        step("Check registration form results data", () -> {
            step("Check registration form results component appears", () -> { // or move to pageobject step
                $(".modal-dialog").should(appear);
                $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            });

            registrationPage.checkResult("Student Name", "Alex Egorov")
                    .checkResult("Student Email", "alex111@egorov.com");
        });
    }
}