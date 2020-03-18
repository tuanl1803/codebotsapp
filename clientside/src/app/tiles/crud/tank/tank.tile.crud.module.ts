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

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {CommonPipeModule} from '../../../lib/pipes/common.pipe.module';
import {CommonComponentModule} from '../../../lib/components/common.component.module';
import {TankService} from '../../../services/tank/tank.service';
import {TankTileCrudComponent} from './tank.tile.crud.component';
import {RoleGuard} from '../../../lib/guards/role.guard';

// % protected region % [Add any additional imports here] on begin
import { FishListComponent } from './custom-list/fish-list/fish-list.component';
// % protected region % [Add any additional imports here] end

const rolesAllowedToRead = [
	'FISHNATIC',
	'ADMIN',
	// % protected region % [Add any additional roles allowed to read here] off begin
	// % protected region % [Add any additional roles allowed to read here] end
];

const rolesAllowedToEdit = [
	'FISHNATIC',
	'ADMIN',
	// % protected region % [Add any additional roles allowed to edit here] off begin
	// % protected region % [Add any additional roles allowed to edit here] end
];

const rolesAllowedToCreate = [
	'FISHNATIC',
	'ADMIN',
	// % protected region % [Add any additional roles allowed to create here] off begin
	// % protected region % [Add any additional roles allowed to create here] end
];


const appRoutes: Routes = [
	// % protected region % [Configure routing for crud tile here] off begin
	{
		path: 'tank-crud',
		children: [
			{
				path: '',
				component: TankTileCrudComponent,
				canActivate: [
					RoleGuard,
				],
				data: {
					expectedRoles: rolesAllowedToRead
				},
			},
			{
				path: 'view/:id',
				component: TankTileCrudComponent,
				canActivate: [
					RoleGuard,
				],
				data: {
					expectedRoles: rolesAllowedToRead
				},
			},
			{
				path: 'create',
				component: TankTileCrudComponent,
				canActivate: [
					RoleGuard,
				],
				data: {
					expectedRoles: rolesAllowedToEdit
				},
			},
			{
				path: 'edit/:id',
				component: TankTileCrudComponent,
				canActivate: [
					RoleGuard,
				],
				data: {
					expectedRoles: rolesAllowedToCreate
				},
			}
		],
		
	},
	// % protected region % [Configure routing for crud tile here] end
];

@NgModule({
	declarations: [
		TankTileCrudComponent,
		// % protected region % [Add any additional declaration here] on begin
		FishListComponent,
		// % protected region % [Add any additional declaration here] end
	],
	imports: [
		CommonModule,
		CommonComponentModule,
		CommonPipeModule,
		RouterModule.forChild(appRoutes),
		// % protected region % [,Add any additional module imports here] on begin
		// % protected region % [Add any additional module imports here] end
	],
	exports: [
		TankTileCrudComponent,
		// % protected region % [Add any additional module exports here] off begin
		// % protected region % [Add any additional module exports here] end
	],
	providers: [
		TankService,
		// % protected region % [Add any additional providers here] off begin

		// % protected region % [Add any additional providers here] end
	],
	// % protected region % [Add any additional module configurations here] off begin
	// % protected region % [Add any additional module configurations here] end
})
export class TankTileCrudModule {
}
