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

import helloapp.entities.listeners.SpeciesEntityListener;
import helloapp.serializers.SpeciesSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.envers.Audited;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.UUID;

// % protected region % [Import any additional imports here] off begin
// % protected region % [Import any additional imports here] end

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Audited
@ApiModel(description = "A species of fish")
@ExcludeSuperclassListeners
@EntityListeners({SpeciesEntityListener.class, AuditingEntityListener.class})
@JsonSerialize(using = SpeciesSerializer.class)
@Table(
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name"}, name = "name"),
	}
)
public class SpeciesEntity extends AbstractEntity {
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Attributes
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// % protected region % [Modify attribute annotation for Name here] off begin
	@NotNull
	@Column(name = "name")
	@ApiModelProperty(notes = "The Name of this entity.")
	@ToString.Include
	// % protected region % [Modify attribute annotation for Name here] end
	private String name;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Outgoing references
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// % protected region % [Update the annotation for Fish species here] off begin
	@ApiModelProperty(notes = "The Fish entities Fish species that are related to this entity.")
	@OneToMany(mappedBy = "species", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	// % protected region % [Update the annotation for Fish species here] end
	private Set<FishEntity> fishSpecies = new HashSet<>();

	@Transient
	private Set<UUID> fishSpeciesIds = new HashSet<>();

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Incoming references
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// % protected region % [Add any additional class fields here] off begin
	// % protected region % [Add any additional class fields here] end

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Outgoing reference methods
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
	 * Similar to {@link this#addFishSpecies(FishEntity, boolean)} but
	 * default to true for reverse add.
	 *
	 * @param entity the given FishEntity to be added to this entity
	 */
	public void addFishSpecies(@NotNull FishEntity entity) {
		addFishSpecies(entity, true);
	}

	/**
	 * Add a new FishEntity to fishSpecies in this entity.
	 *
	 * @param entity the given FishEntity  to be added to this entity
	 * @param reverseAdd whether this entity should be added to the given entity
	 */
	public void addFishSpecies(@NonNull FishEntity entity, boolean reverseAdd) {
		if (!this.fishSpecies.contains(entity)) {
			fishSpecies.add(entity);
			if (reverseAdd) {
				entity.setSpecies(this, false);
			}
		}
	}

	/**
	 * Similar to {@link this#addFishSpecies(Collection, boolean)} but
	 * default to true for reverse add.
	 *
	 * @param entities the given collection of FishEntity to be added to this entity
	 */
	public void addFishSpecies(@NotNull Collection<FishEntity> entities) {
		addFishSpecies(entities, true);
	}

	/**
	 * Add a new collection of FishEntity to Fish species in this entity.
	 *
	 * @param entities the given collection of FishEntity to be added to this entity
	 * @param reverseAdd whether this entity should be added to the given entities
	 */
	public void addFishSpecies(@NonNull Collection<FishEntity> entities, boolean reverseAdd) {
		entities.forEach(entity -> addFishSpecies(entity, reverseAdd));
	}

	/**
	 * Similar to {@link this#removeFishSpecies(FishEntity, boolean)} but
	 * default to true for reverse add.
	 *
	 * @param entity the given FishEntity to be set to this entity
	 */
	public void removeFishSpecies(@NotNull FishEntity entity) {
		this.removeFishSpecies(entity, true);
	}

	/**
	 * Remove the given FishEntity from this entity.
	 *
	 * @param entity the given FishEntity to be removed from this entity
	 * @param reverse whether this entity should be removed from the given entity
	 */
	public void removeFishSpecies(@NotNull FishEntity entity, boolean reverse) {
		if (reverse) {
			entity.unsetSpecies(false);
		}
		this.fishSpecies.remove(entity);
	}

	/**
	 * Similar to {@link this#removeFishSpecies(Collection, boolean)} but
	 * default to true for reverse remove.
	 *
	 * @param entities the given collection of FishEntity to be removed to this entity
	 */
	public void removeFishSpecies(@NotNull Collection<FishEntity> entities) {
		this.removeFishSpecies(entities, true);
	}

	/**
	 * Remove the given collection of FishEntity from  to this entity.
	 *
	 * @param entities the given collection of FishEntity to be removed to this entity
	 * @param reverseAdd whether this entity should be added to the given entities
	 */
	public void removeFishSpecies(@NonNull Collection<FishEntity> entities, boolean reverseAdd) {
		entities.forEach(entity -> this.removeFishSpecies(entity, reverseAdd));
	}

	/**
	 * Similar to {@link this#setFishSpecies(Collection, boolean)} but
	 * default to true for reverse add.
	 *
	 * @param entities the given collection of FishEntity to be set to this entity
	 */
	public void setFishSpecies(@NotNull Collection<FishEntity> entities) {
		setFishSpecies(entities, true);
	}

	/**
	 * Replace the current entities in Fish species with the given ones.
	 *
	 * @param entities the given collection of FishEntity to replace the old ones
	 * @param reverseAdd whether this entity should be added to the given entities
	 */
	public void setFishSpecies(@NotNull Collection<FishEntity> entities, boolean reverseAdd) {

		this.unsetFishSpecies();
		this.fishSpecies = new HashSet<>(entities);
		if (reverseAdd) {
			this.fishSpecies.forEach(fishSpeciesEntity -> fishSpeciesEntity.setSpecies(this, false));
		}
	}

	/**
	 * Similar to {@link this#unsetFishSpecies(boolean)} but
	 * default to true for reverse unset
	 */
	public void unsetFishSpecies() {
		this.unsetFishSpecies(true);
	}

	/**
	 * Remove all the entities in Fish species from this entity.
	 * @param doReverse whether this entity should be removed from the given entities
	 */
	public void unsetFishSpecies(boolean doReverse) {
		if (doReverse) {
			this.fishSpecies.forEach(fishSpeciesEntity -> fishSpeciesEntity.unsetSpecies(false));
		}
		this.fishSpecies.clear();
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Incoming references methods
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// % protected region % [Add any additional class methods  here] off begin
	// % protected region % [Add any additional class methods  here] end
}
