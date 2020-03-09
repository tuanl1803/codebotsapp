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
package helloapp.entities;

import helloapp.entities.enums.*;
import lombok.*;

import java.time.*;

// % protected region % [Import any additional imports here] off begin
// % protected region % [Import any additional imports here] end

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class FishEntity extends AbstractEntity {

	public FishEntity() {
		initialiseReferences();
	}

	private void initialiseReferences() {


		var TankFishTankOneMany = new EntityReference();
			TankFishTankOneMany.entityName = "Tank";
			TankFishTankOneMany.oppositeName = "Tank";
			TankFishTankOneMany.name = "FishTank";
			TankFishTankOneMany.optional = true;
			TankFishTankOneMany.type = "One";
			TankFishTankOneMany.oppositeType = "Many";

		References.add(TankFishTankOneMany);

		var SpeciesFishSpeciesOneMany = new EntityReference();
			SpeciesFishSpeciesOneMany.entityName = "Species";
			SpeciesFishSpeciesOneMany.oppositeName = "Species";
			SpeciesFishSpeciesOneMany.name = "FishSpecies";
			SpeciesFishSpeciesOneMany.optional = true;
			SpeciesFishSpeciesOneMany.type = "One";
			SpeciesFishSpeciesOneMany.oppositeType = "Many";

		References.add(SpeciesFishSpeciesOneMany);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Attributes
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// % protected region % [Modify attribute annotation for Name here] off begin
	@ToString.Include
	// % protected region % [Modify attribute annotation for Name here] end
	private String name;

	// % protected region % [Modify attribute annotation for Date of birth here] off begin
	@ToString.Include
	// % protected region % [Modify attribute annotation for Date of birth here] end
	private OffsetDateTime dateOfBirth;

	// % protected region % [Modify attribute annotation for Alive here] off begin
	@ToString.Include
	// % protected region % [Modify attribute annotation for Alive here] end
	private Boolean alive;

	// % protected region % [Modify attribute annotation for Born here] off begin
	@ToString.Include
	// % protected region % [Modify attribute annotation for Born here] end
	private BornEnum born;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Outgoing one-to-one
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Incoming one-to-one
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Outgoing one-to-many
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Incoming one-to-many
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private TankEntity tank;

	private SpeciesEntity species;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Outgoing many-to-many
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Incoming many-to-many
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// % protected region % [Add any additional class fields here] off begin
	// % protected region % [Add any additional class fields here] end

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Outgoing reference methods
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Incoming references methods
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Similar to {@link this#setTank(TankEntity, boolean)} but
	 * default to true for reverse add.
	 *
	 * @param entity the given TankEntity to be set to this entity
	 */
	public void setTank(@NonNull TankEntity entity) {
		setTank(entity, true);
	}

	/**
	 * Set or update the tank in this entity with single TankEntity.
	 *
	 * @param entity the given TankEntity to be set or updated to tank
	 * @param reverseAdd whether this entity should be set or updated to the given entity
	 */
	public void setTank(@NonNull TankEntity entity, boolean reverseAdd) {
		// % protected region % [Add any additional logic here before the main logic for setTank here] off begin
		// % protected region % [Add any additional logic here before the main logic for setTank here] end

		if (sameAsFormer(this.tank, entity)) {
			return;
		}

		if (this.tank != null) {
			this.tank.removeFishTank(this, false);
		}
		this.tank = entity;
		if (reverseAdd) {
			this.tank.addFishTank(this, false);
		}

		// % protected region % [Add any additional logic here after the main logic for setTank here] off begin
		// % protected region % [Add any additional logic here after the main logic for setTank here] end
	}

	/**
	 * Similar to {@link this#unsetTank(boolean)} but default to true.
	 */
	public void unsetTank() {
		this.unsetTank(true);
	}

	/**
	 * Remove Tank in this entity.
	 *
	 * @param reverse whether this entity should be removed from the given entity
	 */
	public void unsetTank(boolean reverse) {
		if (reverse && this.tank != null) {
			this.tank.removeFishTank(this, false);
		}
		this.tank = null;
	}
	/**
	 * Similar to {@link this#setSpecies(SpeciesEntity, boolean)} but
	 * default to true for reverse add.
	 *
	 * @param entity the given SpeciesEntity to be set to this entity
	 */
	public void setSpecies(@NonNull SpeciesEntity entity) {
		setSpecies(entity, true);
	}

	/**
	 * Set or update the species in this entity with single SpeciesEntity.
	 *
	 * @param entity the given SpeciesEntity to be set or updated to species
	 * @param reverseAdd whether this entity should be set or updated to the given entity
	 */
	public void setSpecies(@NonNull SpeciesEntity entity, boolean reverseAdd) {
		// % protected region % [Add any additional logic here before the main logic for setSpecies here] off begin
		// % protected region % [Add any additional logic here before the main logic for setSpecies here] end

		if (sameAsFormer(this.species, entity)) {
			return;
		}

		if (this.species != null) {
			this.species.removeFishSpecies(this, false);
		}
		this.species = entity;
		if (reverseAdd) {
			this.species.addFishSpecies(this, false);
		}

		// % protected region % [Add any additional logic here after the main logic for setSpecies here] off begin
		// % protected region % [Add any additional logic here after the main logic for setSpecies here] end
	}

	/**
	 * Similar to {@link this#unsetSpecies(boolean)} but default to true.
	 */
	public void unsetSpecies() {
		this.unsetSpecies(true);
	}

	/**
	 * Remove Species in this entity.
	 *
	 * @param reverse whether this entity should be removed from the given entity
	 */
	public void unsetSpecies(boolean reverse) {
		if (reverse && this.species != null) {
			this.species.removeFishSpecies(this, false);
		}
		this.species = null;
	}

	// % protected region % [Add any additional class methods  here] off begin
	// % protected region % [Add any additional class methods  here] end
}
