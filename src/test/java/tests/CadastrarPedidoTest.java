package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.CadastrarPedidoPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class CadastrarPedidoTest {

    private WebDriver driver;
    private Faker faker;
    private CadastrarPedidoPage cadastrarPedidoPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        faker = new Faker();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://tc1-pedi.vercel.app/editar.html");
        driver.manage().window().maximize();
        cadastrarPedidoPage = new CadastrarPedidoPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Deve cadastrar pedido com campos válidos corretamente")
    public void deveCadastrarPedidoComCamposValidos() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("16493614082");
        cadastrarPedidoPage.preencherTelefone(faker.phoneNumber().cellPhone());
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();
        cadastrarPedidoPage.cadastrarPedido();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertEquals("Pedido cadastrado com sucesso!", alert.getText());
        alert.accept();
    }

    @Test
    @DisplayName("Deve adicionar uma segunda pizza ao pedido com sucesso")
    public void deveAdicionarSegundaPizzaAoPedido() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("16493614082");
        cadastrarPedidoPage.preencherTelefone(faker.phoneNumber().cellPhone());
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.adicionarNovaPizza();
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();
        cadastrarPedidoPage.clicaSegundaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaDaNona();
        cadastrarPedidoPage.cadastrarPedido();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertEquals("Pedido cadastrado com sucesso!", alert.getText());
        alert.accept();
    }

    @Test
    @DisplayName("Não deve cadastrar pedido quando nome contém apenas números")
    public void naoDeveCadastrarPedidoQuandoNomeContemApenasNumeros() {
        cadastrarPedidoPage.preencherNome("12345678");
        cadastrarPedidoPage.preencherCpf("16493614082");
        cadastrarPedidoPage.preencherTelefone(faker.phoneNumber().cellPhone());
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();
        cadastrarPedidoPage.cadastrarPedido();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertNotEquals("Pedido cadastrado com sucesso!", alert.getText());
    }

    @Test
    @DisplayName("Não deve validar CPF quando contém letras")
    public void naoDeveValidarCpfQuandoContemLetras() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("ABCDEFGHIJK");
        cadastrarPedidoPage.preencherTelefone(faker.phoneNumber().cellPhone());
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();

        assertFalse(cadastrarPedidoPage.campoCpfEhValido());
    }

    @Test
    @DisplayName("Não deve validar CPF quando contém menos de 11 dígitos")
    public void naoDeveValidarCpfQuandoMenosDeOnzeDigitos() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("123456");
        cadastrarPedidoPage.preencherTelefone(faker.phoneNumber().cellPhone());
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();

        assertFalse(cadastrarPedidoPage.campoCpfEhValido());
    }

    @Test
    @DisplayName("Não deve validar CPF quando contém mais de 11 dígitos")
    public void naoDeveValidarCpfQuandoMaisDeOnzeDigitos() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("1234567890987654321");
        cadastrarPedidoPage.preencherTelefone(faker.phoneNumber().cellPhone());
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();

        assertFalse(cadastrarPedidoPage.campoCpfEhValido());
    }

    @Test
    @DisplayName("Não deve cadastrar pedido quando telefone contém letras")
    public void naoDeveCadastrarPedidoQuandoTelefoneContemLetras() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("16493614082");
        cadastrarPedidoPage.preencherTelefone("AAAA");
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();
        cadastrarPedidoPage.cadastrarPedido();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertNotEquals("Pedido cadastrado com sucesso!", alert.getText());
    }

    @Test
    @DisplayName("Não deve cadastrar pedido quando telefone contém menos de 11 dígitos")
    public void naoDeveCadastrarPedidoQuandoTelefoneMenosDeOnzeDigitos() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("16493614082");
        cadastrarPedidoPage.preencherTelefone("1234567890");
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();
        cadastrarPedidoPage.cadastrarPedido();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertNotEquals("Pedido cadastrado com sucesso!", alert.getText());
    }

    @Test
    @DisplayName("Não deve cadastrar pedido quando telefone contém mais de 11 dígitos")
    public void naoDeveCadastrarPedidoQuandoTelefoneMaisDeOnzeDigitos() {
        cadastrarPedidoPage.preencherNome(faker.name().fullName());
        cadastrarPedidoPage.preencherCpf("16493614082");
        cadastrarPedidoPage.preencherTelefone("1234567890987654321");
        cadastrarPedidoPage.preencherEmail(faker.internet().emailAddress());
        cadastrarPedidoPage.preencherEndereco(faker.address().streetAddress());
        cadastrarPedidoPage.clicaComboBoxPizza();
        cadastrarPedidoPage.selecionaPizzaPeperoni();
        cadastrarPedidoPage.cadastrarPedido();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        assertNotEquals("Pedido cadastrado com sucesso!", alert.getText());
    }


}
