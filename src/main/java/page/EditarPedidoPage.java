package page;

import org.openqa.selenium.*;
import java.util.List;

public class EditarPedidoPage {
    private WebDriver driver;

    private By form = By.id("edit-form");
    private By nome = By.id("edit-fullName");
    private By cpf = By.id("edit-cpf");
    private By telefone = By.id("edit-phone");
    private By email = By.id("edit-email");
    private By endereco = By.id("edit-address");
    private By botaoAdicionarPizza = By.id("add-edit-pizza-btn");
    private By botaoSalvar = By.cssSelector("#edit-form button[type='submit']");
    private By botaoCancelar = By.id("cancel-edit-btn");
    private By listaPizzas = By.cssSelector("#edit-pizzas-container select");
    private By botaoEditarPedido = By.cssSelector(".edit-btn");
    private By botaoExcluirPedido = By.cssSelector(".delete-btn");

    public EditarPedidoPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://tc1-pedi.vercel.app/editar.html");
    }

    public void clicarEditarPrimeiroPedido() {
        driver.findElement(botaoEditarPedido).click();
    }

    public void preencherNome(String nomeValor) {
        driver.findElement(nome).clear();
        driver.findElement(nome).sendKeys(nomeValor);
    }

    public void preencherCpf(String cpfValor) {
        driver.findElement(cpf).clear();
        driver.findElement(cpf).sendKeys(cpfValor);
    }

    public void preencherTelefone(String telefoneValor) {
        driver.findElement(telefone).clear();
        driver.findElement(telefone).sendKeys(telefoneValor);
    }

    public void preencherEmail(String emailValor) {
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(emailValor);
    }

    public void preencherEndereco(String enderecoValor) {
        driver.findElement(endereco).clear();
        driver.findElement(endereco).sendKeys(enderecoValor);
    }

    public void selecionarPizza(int index, String valorPizza) {
        List<WebElement> pizzas = driver.findElements(listaPizzas);
        if (index < pizzas.size()) {
            WebElement select = pizzas.get(index);
            select.sendKeys(valorPizza);
        }
    }

    public void adicionarNovaPizza() {
        driver.findElement(botaoAdicionarPizza).click();
    }

    public void clicarSalvar() {
        driver.findElement(botaoSalvar).click();
    }

    public void clicarCancelar() {
        driver.findElement(botaoCancelar).click();
    }

    public void clicarExluir() {
        driver.findElement(botaoExcluirPedido).click();
    }

    public boolean formularioEstaVisivel() {
        return driver.findElement(form).isDisplayed();
    }

    public int quantidadeDePizzasSelecionaveis() {
        return driver.findElements(listaPizzas).size();
    }
}
