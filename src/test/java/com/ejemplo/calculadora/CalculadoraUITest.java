package com.ejemplo.calculadora;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CalculadoraUITest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Configurar el driver de Chrome y las opciones
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // Si estamos ejecutando en CI, habilitar el modo sin cabeza
        if (System.getenv("CI") != null) {
            options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--user-data-dir=/tmp/chrome-user-data");
        }
        
        // Inicializar el driver con las opciones
        driver = new ChromeDriver(options);
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Navegar a la URL de la calculadora
        driver.get("http://localhost:8080/api/calculadora/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSumar() {
        // Localizar los elementos del formulario
        WebElement num1Input = driver.findElement(By.name("num1"));
        WebElement num2Input = driver.findElement(By.name("num2"));
        WebElement operacion = driver.findElement(By.name("operacion"));
        WebElement boton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Ingresar los valores
        num1Input.sendKeys("5");
        num2Input.sendKeys("3");
        operacion.sendKeys("Sumar");
        boton.click();

        // Esperar y verificar el resultado
        WebElement resultado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        Assertions.assertTrue(resultado.getText().contains("Resultado: 8"));
    }

    @Test
    public void testRestar() {
        // Localizar los elementos del formulario
        WebElement num1Input = driver.findElement(By.name("num1"));
        WebElement num2Input = driver.findElement(By.name("num2"));
        WebElement operacion = driver.findElement(By.name("operacion"));
        WebElement boton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Ingresar los valores
        num1Input.sendKeys("5");
        num2Input.sendKeys("3");
        operacion.sendKeys("Restar");
        boton.click();

        // Esperar y verificar el resultado
        WebElement resultado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        Assertions.assertTrue(resultado.getText().contains("Resultado: 2"));
    }

    @Test
    public void testMultiplicar() {
        // Localizar los elementos del formulario
        WebElement num1Input = driver.findElement(By.name("num1"));
        WebElement num2Input = driver.findElement(By.name("num2"));
        WebElement operacion = driver.findElement(By.name("operacion"));
        WebElement boton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Ingresar los valores
        num1Input.sendKeys("5");
        num2Input.sendKeys("3");
        operacion.sendKeys("Multiplicar");
        boton.click();

        // Esperar y verificar el resultado
        WebElement resultado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        Assertions.assertTrue(resultado.getText().contains("Resultado: 15"));
    }
}


