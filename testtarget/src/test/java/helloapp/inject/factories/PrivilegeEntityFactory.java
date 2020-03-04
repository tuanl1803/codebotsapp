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
package helloapp.inject.factories;

import helloapp.entities.PrivilegeEntity;
import com.google.inject.Injector;
import lombok.*;

// % protected region % [Apply any additional imports here] off begin
// % protected region % [Apply any additional imports here] end

public class PrivilegeEntityFactory extends BaseFactory<PrivilegeEntity> {

	// % protected region % [Apply any additional class fields here] off begin
	// % protected region % [Apply any additional class fields here] end

	public PrivilegeEntityFactory(
			// % protected region % [Apply any additional constructor logic here] off begin
			// % protected region % [Apply any additional constructor logic here] end
			@NonNull Injector injector
	) {
		super(
				// % protected region % [Apply any additional constructor arguments here] off begin
				// % protected region % [Apply any additional constructor arguments here] end
				PrivilegeEntity.class,
				injector
		);

		// % protected region % [Apply any additional constructor logic after the main body here] off begin
		// % protected region % [Apply any additional constructor logic after the main body here] end
	}
}
