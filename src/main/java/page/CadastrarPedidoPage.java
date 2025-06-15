package page;

import org.openqa.selenium.*;
import java.util.List;

public class CadastrarPedidoPage {
    private WebDriver driver;

    private By nome = By.id("fullName");
    private By cpf = By.id("cpf");
    private By telefone = By.id("phone");
    private By email = By.id("email");
    private By endereco = By.id("address");
    private By botaoAdicionarPizza = By.id("add-pizza-btn");
    private By botaoCadastraPedido = By.xpath("//*[@id=\"cadastrar-form\"]/button[2]");
    private By comboBoxPizza = By.name("pizzas[]");
    private By segundaComboBoxPizza = By.xpath("//div[@id='pizzas-container']/div[2]/select");
    private By botaoRemoverPizza = By.cssSelector(".remove-pizza-btn");

    public CadastrarPedidoPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://tc1-pedi.vercel.app/cadastrar.html");
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

    public void adicionarNovaPizza() {
        driver.findElement(botaoAdicionarPizza).click();
    }

    public void cadastrarPedido() {
        driver.findElement(botaoCadastraPedido).click();
    }

    public void clicaComboBoxPizza() {
        driver.findElement(comboBoxPizza).click();
    }

    public void clicaSegundaComboBoxPizza() {
        driver.findElement(segundaComboBoxPizza).click();
    }

    public boolean campoEmailEhValido() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript("return arguments[0].checkValidity();", getEmailInput());
    }

    public boolean campoCpfEhValido() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript("return arguments[0].checkValidity();", getCpfInput());
    }

    public void selecionaPizzaPeperoni() {
        List<WebElement> pizzas = driver.findElements(comboBoxPizza);
        if (!pizzas.isEmpty()) {
            pizzas.get(0).sendKeys("Pizza Pepperoni");
        }
    }

    public void selecionaPizzaDaNona() {
        List<WebElement> pizzas = driver.findElements(segundaComboBoxPizza);
        if (!pizzas.isEmpty()) {
            pizzas.get(0).sendKeys("Pizza Da Nona");
        }
    }

    public void clicaBotaoRemoverSegundaPizza() {
        List<WebElement> botoesRemover = driver.findElements(botaoRemoverPizza);
        botoesRemover.get(1).click();
    }

    public void clicaBotaoRemoverPizza() {
        List<WebElement> botoesRemover = driver.findElements(botaoRemoverPizza);
        botoesRemover.get(0).click();
    }

    public int quantidadeDeCombosDePizza() {
        return driver.findElements(comboBoxPizza).size();
    }

    public WebElement getCpfInput() {
        return driver.findElement(cpf);
    }

    public WebElement getEmailInput() {
        return driver.findElement(email);
    }
}
