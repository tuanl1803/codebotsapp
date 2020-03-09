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
package helloapp.entities.enums;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum CleanEnum {
	@JsonProperty("Cleaned")
	CLEANED("Cleaned"),
	@JsonProperty("Dirty")
	DIRTY("Dirty"),
	// % protected region % [Add any additional ENUM literals here] off begin
	// % protected region % [Add any additional ENUM literals here] end
	;

	private final String literalValue;

	CleanEnum(String literalValue) {
		this.literalValue = literalValue;
	}

	public boolean equalsName(String name) {
		return this.literalValue.equals(name);
	}

	@Override
	public String toString() {
		return this.literalValue;
	}
}
