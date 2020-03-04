/*
 * @bot-written
 * 
 * WARNING AND NOTICE
 * Any access, download, storage, and/or use of this source code is subject to the terms and conditions of the
 * Full Software Licence as accepted by you before being granted access to this source code and other materials,
 * the terms of which can be accessed on the Codebots website at https://codebots.com/full-software-licence. Any
 * commercial use in contravention of the terms of the Full Software Licence may be pursued by Codebots through
 * licence termination and further legal action, and be required to indemnify Codebots for any loss or damage,
 * including interest and costs. You are deemed to have accepted the terms of the Full Software Licence on any
 * access, download, storage, and/or use of this source code.
 * 
 * BOT WARNING
 * This file is bot-written.
 * Any changes out side of "protected regions" will be lost next time the bot makes any changes.
 */
package helloapp.cucumber.pom.botwritten.page.crud;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import helloapp.cucumber.pom.botwritten.page.CrudPage;
import helloapp.cucumber.utils.*;
import helloapp.entities.AbstractEntity;
import cucumber.runtime.java.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.interactions.Actions;
import helloapp.cucumber.pom.botwritten.factories.AdminPageFactory;
import helloapp.entities.FishnaticEntity;
import java.util.Properties;
import java.util.Collection;

// % protected region % [Add any additional imports here] off begin
// % protected region % [Add any additional imports here] end

/**
 * FishnaticPage is a Page POM that is associated with the admin/users/fishnatic url.
 *
 */
@Slf4j
@ScenarioScoped
public class AdminUsersFishnaticPage extends CrudPage {

	// % protected region % [Add any additional fields here] off begin
	// % protected region % [Add any additional fields here] end

	@FindBy(how = How.XPATH, using = "//input[@id='firstName-field']")
	private WebElement firstNameField;
	@FindBy(how = How.XPATH, using = "//input[@id='lastName-field']")
	private WebElement lastNameField;
	@FindBy(how = How.XPATH, using = "//input[@id='username-field']")
	private WebElement usernameField;
	@FindBy(how = How.XPATH, using = "//input[@id='password-field']")
	private WebElement passwordField;
	@FindBy(how = How.XPATH, using = "//ng-select[@id='gender-field']")
	private WebElement genderField;
	@FindBy(how = How.XPATH, using = "//input[@id='email-field']")
	private WebElement emailField;
	@FindBy(how = How.XPATH, using = "//input[@id='isArchived-field']")
	private WebElement isArchivedField;

	// Outgoing one-to-one

	// Incoming one-to-one

	// Outgoing one-to-many

	// Incoming one-to-many

	// Outgoing many-to-many

	// Incoming many-to-many

	// % protected region % [Add any additional class fields here] off begin
	// % protected region % [Add any additional class fields here] end

	@Inject
	public AdminUsersFishnaticPage(
			// % protected region % [Add any additional constructor parameters here] off begin
			// % protected region % [Add any additional constructor parameters here] end
			WebDriver webDriver,
			Properties properties,
			AdminPageFactory adminPageFactory
	) {
		super(
			// % protected region % [Add any additional constructor arguments here] off begin
			// % protected region % [Add any additional constructor arguments here] end
			webDriver,
			properties,
			adminPageFactory,
			"admin/users/fishnatic/create"
		);

		// % protected region % [Add any additional constructor logic here] off begin
		// % protected region % [Add any additional constructor logic here] end

		log.trace("Initialised {}", this.getClass().getSimpleName());
	}

	@Override
	protected void fillInEntityInformation(AbstractEntity abstractEntity)
	{
		var entity = (FishnaticEntity) abstractEntity;
		firstNameField.sendKeys(entity.getFirstName());
		lastNameField.sendKeys(entity.getLastName());
		usernameField.sendKeys(entity.getUsername());
		passwordField.sendKeys(entity.getPassword());
		DropdownUtils.selectOptionByName(webDriver, genderField, entity.getGender().getLiteralValue());
		emailField.sendKeys(entity.getEmail());
		if (entity.getIsArchived()) {
			isArchivedField.click();
		}

		saveButton.click();
	}

	// % protected region % [Add any additional methods here] off begin
	// % protected region % [Add any additional methods here] end
}