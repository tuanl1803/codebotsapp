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
package helloapp.controllers;

import helloapp.entities.FishnaticEntity;
import helloapp.services.FishnaticService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

// % protected region % [Add any additional imports here] off begin
// % protected region % [Add any additional imports here] end

/**
 * Controller used to handle all REST operations regarding Fishnatic
 */
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Fishnatics.")
@RestController
@RequestMapping("/api/fishnatic")
public class FishnaticController {

	private final FishnaticService fishnaticService;

	// % protected region % [Add any additional class fields here] off begin
	// % protected region % [Add any additional class fields here] end

	@Autowired
	public FishnaticController(
			// % protected region % [Add any additional constructor parameters here] off begin
			// % protected region % [Add any additional constructor parameters here] end
			FishnaticService fishnaticService
	) {
		this.fishnaticService = fishnaticService;

		// % protected region % [Add any additional constructor logic here] off begin
		// % protected region % [Add any additional constructor logic here] end
	}

	/**
	 * Return all the Fishnatics.
	 *
	 * @return all the Fishnatics
	 */
	@ApiOperation(
			value = "Returns a single page of Fishnatics",
			authorizations = {@Authorization(value = "bearerToken")}
	)
	@PreAuthorize("hasPermission('FishnaticEntity', 'read')")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<FishnaticEntity>> getAllWithPage(
			@ApiParam("The page to return.")
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@ApiParam("The size of the page to return.")
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
	) {
		List<FishnaticEntity> fishnatics = fishnaticService.findAllWithPage((page > 0) ? page - 1 : page, pageSize);

		// % protected region % [Add any custom logic before returning the entities here] off begin
		// % protected region % [Add any custom logic before returning the entities here] end

		return new ResponseEntity<>(fishnatics, HttpStatus.OK);
	}

	/**
	 * Return the Fishnatic that has the same id as the given id.
	 *
	 * @param id      The id of the FishnaticEntity to be returned
	 * @param expands The expand string to be used when expanding
	 * @return the Fishnatic that has the same id as the given id
	 */
	// % protected region % [Customise the security configuration here for the getWithId endpoint] off begin
	// % protected region % [Customise the security configuration here for the getWithId endpoint] end
	@ApiOperation(
			value = "Return a single Fishnatic as defined by the id provided.",
			authorizations = {@Authorization(value = "bearerToken")}
	)
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Fishnatic entity not found"),
	})
	@PreAuthorize("hasPermission('FishnaticEntity', 'read')")
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<FishnaticEntity> getById(
			@ApiParam("The UUID of the Fishnatic to return.")
			@PathVariable("id") UUID id
	) {
		Optional<FishnaticEntity> fishnaticEntity = fishnaticService.findById(id);

		// % protected region % [Add any final logic before returning the entity here] off begin
		// % protected region % [Add any final logic before returning the entity here] end

		if (fishnaticEntity.isPresent()) {
			return new ResponseEntity<>(fishnaticEntity.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Create/Update the given Fishnatic. If the entity has an id it will be updated, if not it will be created.
	 * The appropriate status codes will be set.
	 *
	 * @param fishnaticEntity The entity to save or update
	 */
	@ApiOperation(
			value = "Create a Fishnatic if id not exists. Update otherwise.",
			authorizations = {@Authorization(value = "bearerToken")}
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created the new Fishnatic."),
			@ApiResponse(code = 409, message = "Failed to create the new Fishnatic, duplicate record found")
	})
	@PreAuthorize("hasPermission('FishnaticEntity', 'create') or hasPermission('FishnaticEntity', 'update')")
	@PostMapping
	public ResponseEntity<FishnaticEntity> saveOrUpdateFishnatic(
			@ApiParam("The FishnaticEntity to create or update.")
			@RequestBody FishnaticEntity fishnaticEntity
	) {
		// % protected region % [Add any logic before save here] off begin
		// % protected region % [Add any logic before save here] end

		fishnaticEntity = fishnaticService.save(fishnaticEntity);

		return new ResponseEntity<>(fishnaticEntity, HttpStatus.OK);
	}

	/**
	 * Update the given Fishnatic. If the entity has an id it will be updated, if not it will be created.
	 * The appropriate status codes will be set.
	 *
	 * @param fishnaticEntity The entity to save or update
	 */
	@ApiOperation(
			value = "Create a Fishnatic if id not exists. Update otherwise.",
			authorizations = {@Authorization(value = "bearerToken")}
	)
	@PreAuthorize("hasPermission('FishnaticEntity', 'create') or hasPermission('FishnaticEntity', 'update')")
	@PutMapping
	public ResponseEntity<FishnaticEntity> updateFishnatic(
			@ApiParam("The FishnaticEntity to create or update.")
			@RequestBody FishnaticEntity fishnaticEntity
	) {
		// % protected region % [Add any logic before save here] off begin
		// % protected region % [Add any logic before save here] end

		fishnaticEntity = fishnaticService.save(fishnaticEntity);

		return new ResponseEntity<>(fishnaticEntity, HttpStatus.OK);
	}

	/**
	 * Delete the Fishnatic that has the same id as the given id.
	 *
	 * @param id The id of the FishnaticEntity to be deleted
	 * @return void HTTP status code will be set on success
	 */
	// % protected region % [Customise the security config here for the delete endpoint] off begin
	// % protected region % [Customise the security config here for the delete endpoint] end
	@ApiOperation(
			value = "Delete a single Fishnatic as defined by the id provided.",
			authorizations = {@Authorization(value = "bearerToken")}
	)
	@PreAuthorize("hasPermission('FishnaticEntity', 'delete')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(
			@ApiParam("The UUID of the Fishnatic to delete.")
			@PathVariable(value="id") UUID id
	) {
		// % protected region % [Add any additional logic before deleting the given entity] off begin
		// % protected region % [Add any additional logic before deleting the given entity] end

		fishnaticService.deleteById(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// % protected region % [Add any additional endpoints here] off begin
	// % protected region % [Add any additional endpoints here] end
}
