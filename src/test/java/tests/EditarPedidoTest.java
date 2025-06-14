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
        driver.get("https://tc1-pedi.vercel.app/editar.html");

        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
        cadastrarPedidoJs();
        driver.navigate().refresh();
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
        page.preencherCpf("16493614082");
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
        page.preencherCpf("11111111111"); // Tem 11 dígitos mas não é válido
        page.preencherTelefone(faker.phoneNumber().cellPhone());
        page.preencherEmail(faker.internet().emailAddress());
        page.preencherEndereco(faker.address().streetAddress());
        page.selecionarPizza(0, "Pizza Pepperoni");

        String validationMessage = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.getElementById('edit-cpf').validationMessage;");

        assertEquals("É preciso que o formato corresponda ao exigido.", validationMessage);
    }

    @Test
    @DisplayName("Should not edit order with empty name")
    public void shouldNotEditOrderWithEmptyName() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        page.preencherNome("");
        page.preencherCpf("16493614082");
        page.preencherTelefone(faker.phoneNumber().cellPhone());
        page.preencherEmail(faker.internet().emailAddress());
        page.preencherEndereco(faker.address().streetAddress());
        page.selecionarPizza(0, "Pizza Pepperoni");

        String validationMessage = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.getElementById('edit-fullName').validationMessage;");

        assertEquals("Preencha este campo.", validationMessage);
    }

    @Test
    @DisplayName("Should not edit order with empty address")
    public void shouldNotEditOrderWithEmptyAddress() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        page.preencherNome(faker.name().fullName());
        page.preencherCpf("16493614082");
        page.preencherTelefone(faker.phoneNumber().cellPhone());
        page.preencherEmail(faker.internet().emailAddress());
        page.preencherEndereco("");
        page.selecionarPizza(0, "Pizza Pepperoni");

        String validationMessage = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.getElementById('edit-address').validationMessage;");

        assertEquals("Preencha este campo.", validationMessage);
    }

    @Test
    @DisplayName("Should not edit order with invalid email")
    public void shouldNotEditOrderWithInvalidEmail() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        page.preencherNome(faker.name().fullName());
        page.preencherCpf("16493614082");
        page.preencherTelefone(faker.phoneNumber().cellPhone());
        page.preencherEmail("emailinvalido@a"); // sem domínio
        page.preencherEndereco(faker.address().streetAddress());
        page.selecionarPizza(0, "Pizza Pepperoni");


        String validationMessage = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.getElementById('edit-email').validationMessage;");

        assertTrue(validationMessage.toLowerCase().contains("email"));
    }

    @Test
    @DisplayName("should not edit order with invalid phone")
    public void shouldNotEditOrderWithInvalidPhone() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        page.preencherNome(faker.name().fullName());
        page.preencherCpf("16493614082");
        page.preencherTelefone("123"); // Telefone inválido
        page.preencherEmail(faker.internet().emailAddress());
        page.preencherEndereco(faker.address().streetAddress());
        page.selecionarPizza(0, "Pizza Calabresa");

        String validationMessage = (String) ((JavascriptExecutor) driver)
                .executeScript("const el = document.getElementById('edit-phone'); el.reportValidity(); return el.validationMessage;");

        assertEquals("É preciso que o formato corresponda ao exigido.", validationMessage);
    }

    @Test
    @DisplayName("Should hide form when delete order after editing")
    public void shouldHideFormWhenDeleteOrderAfterEditing() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        ((JavascriptExecutor) driver).executeScript("window.confirm = function(){ return true; };");
        page.clicarExluir();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        alert.accept();

        assertFalse(page.formularioEstaVisivel());
    }

    @Test
    @DisplayName("Should delete order successfully")
    public void shouldDeleteOrderSuccessfully() {
        EditarPedidoPage page = new EditarPedidoPage(driver);

        ((JavascriptExecutor) driver).executeScript("window.confirm = function(){ return true; };");

        page.clicarExluir();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());

        assertEquals("Pedido excluído com sucesso.", alert.getText());
        alert.accept();
    }

    @Test
    @DisplayName("should hide form when click cancel button")
    public void shouldHideFormWhenClickCancelButton() {
        EditarPedidoPage page = new EditarPedidoPage(driver);
        page.clicarEditarPrimeiroPedido();

        page.clicarCancelar();

        assertFalse(page.formularioEstaVisivel());
    }

    private void cadastrarPedidoJs() {
        String nome = faker.name().fullName();
        String cpf = "16493614082";
        String telefone = faker.phoneNumber().cellPhone();
        String email = faker.internet().emailAddress();
        String endereco = faker.address().streetAddress();

        String script = """
        localStorage.setItem("pedidos", JSON.stringify([{
            fullName: "%s",
            cpf: "%s",
            phone: "%s",
            email: "%s",
            address: "%s",
            pizzas: ["pepperoni"]
        }]));
        """.formatted(nome, cpf, telefone, email, endereco);

        ((JavascriptExecutor) driver).executeScript(script);
    }
}
