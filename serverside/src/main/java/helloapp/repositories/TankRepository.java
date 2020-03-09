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
package helloapp.repositories;

import helloapp.entities.TankEntity;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import helloapp.entities.enums.*;
import java.time.*;
import java.util.Optional;
import java.util.List;

// % protected region % [Import any additional imports here] off begin
// % protected region % [Import any additional imports here] end

/**
 * Repository used to handle any data access operations against an entity.
 */
@Repository
public interface TankRepository extends AbstractRepository<TankEntity> {
	/**
	 * Return an entity or a list of entities that have the given attribute Name.
	 *
	 * @param name the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Name
	 */
	List<TankEntity> findByName(@NotNull String name);

	/**
	 * Return an entity or a list of entities that have the given attribute Width.
	 *
	 * @param width the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Width
	 */
	List<TankEntity> findByWidth(@NotNull Double width);

	/**
	 * Return an entity or a list of entities that have the given attribute Length.
	 *
	 * @param length the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Length
	 */
	List<TankEntity> findByLength(@NotNull Double length);

	/**
	 * Return an entity or a list of entities that have the given attribute Height.
	 *
	 * @param height the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Height
	 */
	List<TankEntity> findByHeight(@NotNull Double height);

	/**
	 * Return an entity or a list of entities that have the given attribute Last Cleaned.
	 *
	 * @param lastCleaned the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Last Cleaned
	 */
	List<TankEntity> findByLastCleaned(@NotNull OffsetDateTime lastCleaned);

	/**
	 * Return an entity or a list of entities that have the given attribute Clean.
	 *
	 * @param clean the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Clean
	 */
	List<TankEntity> findByClean(@NotNull CleanEnum clean);

	// % protected region % [Add any additional class methods here] off begin
	// % protected region % [Add any additional class methods here] end
}