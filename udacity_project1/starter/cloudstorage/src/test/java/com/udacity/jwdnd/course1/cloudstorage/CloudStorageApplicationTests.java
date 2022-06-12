package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private HomePage home;
	private LoginPage login;
	private SignupPage signup;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testAccessPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void testSignupAndLogin() {
		String firstName = "a", lastName = "b", username = "c", password = "d";

		WebDriverWait wait = new WebDriverWait(driver, 10);
		signup = new SignupPage(driver);
		login = new LoginPage(driver);
		home = new HomePage(driver);

		driver.get("http://localhost:" + this.port + "/signup");
		signup.signup(firstName, lastName, username, password);
		driver.get("http://localhost:" + this.port + "/login");
		wait.until(webDriver -> webDriver.findElement(By.name("username")));
		login.login(username, password);
		home.logout();
		wait.until(webDriver -> webDriver.findElement(By.name("username")));
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void testNoteFunction() {
		String firstName = "a", lastName = "b", username = "c", password = "d";
		String noteTitle = "created!", noteDescription = "created!";
		String editedTitle = "edited!", editedDescription = "edited!";
		WebDriverWait wait = new WebDriverWait(driver, 10);
		signup = new SignupPage(driver);
		login = new LoginPage(driver);
		home = new HomePage(driver);

		driver.get("http://localhost:" + this.port + "/signup");
		signup.signup(firstName, lastName, username, password);
		driver.get("http://localhost:" + this.port + "/login");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
		login.login(username, password);
		driver.get("http://localhost:" + this.port + "/home");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("upload-note-btn")));
		driver.findElement(By.id("upload-note-btn")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		home.createNote(noteTitle, noteDescription);

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-row-title")));
		String result = driver.findElement(By.id("note-row-title")).getText();
		Assertions.assertEquals(result, noteTitle);

		driver.findElement(By.id("note-edit")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		home.editNote(editedTitle, editedDescription);
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-row-title")));
		result = driver.findElement(By.id("note-row-title")).getText();
		Assertions.assertEquals(result, editedTitle);

		driver.findElement(By.id("note-delete")).click();
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
		boolean isExist;
		try {
			driver.findElement(By.id("note-row-title"));
			isExist = true;
		} catch (NoSuchElementException e) {
			isExist = false;
		}
		Assertions.assertFalse(isExist);
	}

	@Test
	public void testCredentialFunction() {
		String firstName = "a", lastName = "b", username = "c", password = "d";
		String credUrl = "google.com", credUsername = "created!", credPassword = "created!";
		String editedUrl = "yahoo.com", editedUsername = "changed!", editedPassword = "changed!";
		int size = 3;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		signup = new SignupPage(driver);
		login = new LoginPage(driver);
		home = new HomePage(driver);

		driver.get("http://localhost:" + this.port + "/signup");
		signup.signup(firstName, lastName, username, password);
		driver.get("http://localhost:" + this.port + "/login");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
		login.login(username, password);
		for (int i = 0; i < size; i++) {
			driver.get("http://localhost:" + this.port + "/home");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
			driver.findElement(By.id("nav-credentials-tab")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("upload-cred-btn")));
			driver.findElement(By.id("upload-cred-btn")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
			home.createCredential(credUrl, credUsername, credPassword);
		}

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("cred-row-url")));
		List<WebElement> resultList = driver.findElements(By.id("cred-row-url"));
		Assertions.assertEquals(resultList.size(), size);
		WebElement result = driver.findElement(By.id("cred-row-password"));
		Assertions.assertNotEquals(result.getText(), credPassword);

		resultList = driver.findElements(By.id("cred-edit"));
		resultList.get(0).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		String viewPassword = driver.findElement(By.id("credential-password")).getText();
		Assertions.assertNotEquals(viewPassword, credPassword);

		for (int i = 0; i < size; i++) {
			driver.get("http://localhost:" + this.port + "/home");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
			driver.findElement(By.id("nav-credentials-tab")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("cred-edit")));
			resultList = driver.findElements(By.id("cred-edit"));
			resultList.get(i).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
			home.editCredential(editedUrl, editedUsername, editedPassword);
		}

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("cred-row-url")));
		resultList = driver.findElements(By.id("cred-row-url"));
		for (int i = 0; i < size; i++) {
			Assertions.assertEquals(resultList.get(i).getText(), editedUrl);
		}

		for (int i = 0; i < size; i++) {
			driver.get("http://localhost:" + this.port + "/home");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
			driver.findElement(By.id("nav-credentials-tab")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("cred-delete")));
			result = driver.findElement(By.id("cred-delete"));
			result.click();
		}

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("upload-cred-btn")));

		boolean isExist;
		try {
			driver.findElement(By.id("cred-delete"));
			isExist = true;
		} catch (NoSuchElementException e) {
			isExist = false;
		}
		Assertions.assertFalse(isExist);
	}


}
