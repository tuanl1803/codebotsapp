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
package helloapp.cucumber.pom.botwritten.page.crudlist;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import helloapp.cucumber.pom.botwritten.factories.AdminPageFactory;
import helloapp.cucumber.pom.botwritten.page.crud.AdminUsersAdminPage;
import helloapp.cucumber.pom.botwritten.page.CrudListPage;
import helloapp.cucumber.utils.TypingUtils;
import cucumber.runtime.java.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import helloapp.entities.AdminEntity;
import java.util.Properties;
import java.util.Collection;
import helloapp.entities.AbstractEntity;

// % protected region % [Add any additional imports here] off begin
// % protected region % [Add any additional imports here] end

/**
 * AdminPage is a Page POM that is associated with the admin/users/admin url.
 *
 */
@Slf4j
@ScenarioScoped
public class UsersAdminListPage extends CrudListPage {

	// % protected region % [Add any additional fields here] off begin
	// % protected region % [Add any additional fields here] end

	private final AdminUsersAdminPage adminUsersAdminPage;



	@Inject
	public UsersAdminListPage(
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
			"admin/users/admin"
		);

		this.adminUsersAdminPage = new AdminUsersAdminPage(webDriver, properties, adminPageFactory);



		// % protected region % [Add any additional constructor logic here] off begin
		// % protected region % [Add any additional constructor logic here] end

		log.trace("Initialised {}", this.getClass().getSimpleName());
	}

	public AdminUsersAdminPage getCreatePage() {
		return adminUsersAdminPage;
	}

	// % protected region % [Add any additional methods here] off begin
	// % protected region % [Add any additional methods here] end
}
