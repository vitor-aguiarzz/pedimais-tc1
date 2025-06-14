package tests;

import page.EditarPedidoPage;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class EditarPedidoTest {

    private WebDriver driver;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        faker = new Faker();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Should edit order with valid data")
    public void shouldEditOrderWithValidData() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        page.preencherNome(faker.name().fullName());
        page.preencherCpf("12345678900");
        page.preencherTelefone(faker.phoneNumber().cellPhone());
        page.preencherEmail(faker.internet().emailAddress());
        page.preencherEndereco(faker.address().streetAddress());
        page.selecionarPizza(0, "Pizza Pepperoni");
        page.clicarSalvar();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertEquals("Pedido atualizado com sucesso!", alert.getText());
        alert.accept();
    }

    @Test
    @DisplayName("Should not edit order with invalid CPF")
    public void shouldNotEditOrderWithInvalidCpf() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        page.preencherNome(faker.name().fullName());
        page.preencherCpf("12345");
        page.preencherTelefone(faker.phoneNumber().cellPhone());
        page.preencherEmail(faker.internet().emailAddress());
        page.preencherEndereco(faker.address().streetAddress());
        page.selecionarPizza(0, "Pizza Pepperoni");
        page.clicarSalvar();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertTrue(alert.getText().contains("CPF inválido"));
        alert.accept();
    }
}
