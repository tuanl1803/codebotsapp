import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FishModel} from '../../../../../models/fish/fish.model';
import {
	Expand,
	OrderBy,
	PassableStateConfig,
	QueryOperation,
	QueryParams,
	Where
} from '../../../../../lib/services/http/interfaces';
import {Observable, Subject} from 'rxjs';
import {FishModelAudit, FishModelState} from '../../../../../models/fish/fish.model.state';
import {ModelProperty, ModelRelation} from '../../../../../lib/models/abstract.model';
import {Action, HeaderOption, LoadingType} from '../../../../../lib/components/collection/collection.component';
import {IconPosition} from '../../../../../lib/components/button/button.component';
import {FilterQuestion} from '../../../../../lib/components/collection/collection-filter.component';
import {FormGroup} from '@angular/forms';
import {Action as NgRxAction, Store} from '@ngrx/store';
import {AuthenticationService} from '../../../../../lib/services/authentication/authentication.service';
import {getFishCollectionCount,getFishCollectionModels,getFishModelAuditsByEntityId,getFishModelWithId} from '../../../../../models/fish/fish.model.selector';
import * as modelAction from '../../../../../models/fish/fish.model.action';
import {debounceTime, distinctUntilChanged, filter} from 'rxjs/operators';
import {createReactiveFormFromModel} from '../../../../../lib/models/model-utils';

enum CollectionActionEnum {
	Create = 'Create',
}

/**
 * Enum used to declare every custom item action that will be included into the underlying collection.
 *
 * Note that this is only for declaration. If the developer wants to use it, they must register it into the actions
 * property below.
 */
enum ItemActionEnum {
	View = 'View',
	Edit = 'Edit',
	Delete = 'Delete',
}


@Component({
  selector: 'cb-fish-list',
  templateUrl: './fish-list.component.html',
  styleUrls: ['./fish-list.component.scss']
})
export class FishListComponent implements OnInit, OnChanges {

 /**
	 * The name of the fields which are used in the search box
	 */
	private searchFields: string[] = [
		...FishModel.searchFields,
	];

	/**
	 * Where statements used in search
	 * Connected with 'or' operation
	 */
	private searchConditions: Where[] = [];

	/**
	 * Where statements used in filtering
	 * Connected with 'and' operation
	 */
	private filterConditions: Where[][] = [];

	/**
	 * List of fishs that will be managed by this CRUD tile. Note that this list is not complete, i.e. it
	 * does not represent all models in the database. Instead it represents a slice or page of the complete list.
	 */
	fishs: Observable<FishModel[]>;

	/**
	 * Entity audits to be fetched from the server.
	 */
	fishAudits$: Observable<FishModelAudit[]>;

	/**
	 * The total number of entities in the database.
	 */
	fishsCount: number = 0;

	/**
	 * List of all property names for this entity.
	 */
	modelProperties: ModelProperty[] = FishModel.getProps();

	/**
	 * List of all relations of the model
	 */
	modelRelations: { [name: string]: ModelRelation } = (() => {
		const modelRelations = FishModel.getRelations();
		modelRelations['tank'].hideElement = true;
		return modelRelations;
	})();

	/**
	 * The collection id used in the store
	 * Default to be the uuid of the tile, you could change this to custom id you want to share in different component
	 * But this must to be unique to avoid mess up the data
	 */
	@Input()
	collectionId: string = 'fish-list';

	/**
	 * Which type of pagination to be used in the collection.
	 */
	@Input()
	loadingType: LoadingType | string = LoadingType.PAGINATION;

	/**
	 * A page index indicates the start index of a "page", which default to 0.
	 */
	@Input()
	pageIndex: number = 0;

	/**
	 * How many items are included in this page.
	 */
	@Input()
	pageSize: number = 10;

	/**
	 * Default ordering of the items.
	 */
	@Input()
	orderBy: OrderBy[] = [
		{
			path: 'created',
			descending: true
		}
	];

	/**
	 * Id of appointment to filter the list
	 */
	@Input()
	tankId: string;

	/**
	 * Default conditions to be applied when the component first loaded.
	 */
	get defaultWheres(): Where[][] {
		return [
			[
				{
					operation: QueryOperation.EQUAL,
					path: 'tankId',
					value: this.tankId
				}
			]
		];
	}

	/**
	 * Default references to expand
	 * In CRUD tile, default to expand all the references
	 */
	private get defaultExpands(): Expand[] {
		// TODO fix the hard code s once the standard is confirmed
		let expands: Expand[] = Object.entries(FishModel.getRelations()).map(
			([key, entry]): Expand => {
				return {
					name: key,
					fields: ['id', entry.displayName],
				};
			}
		);
		return expands;
	}

	/**
	 * Where can be passed in as where statement in query
	 */
	@Input()
	where: Where[][] = [];

	/**
	 * The reference want to fetch from the server side
	 */
	@Input()
	expands: Expand[] = [];

	/**
	 * The query parameters for the collection
	 */
	get queryParams(): QueryParams {
		return {
			pageIndex: this.pageIndex,
			pageSize: this.pageSize,
			orderBy: this.orderBy,
			where: [
				...this.where,
				...this.defaultWheres,
				...this.filterConditions,
				this.searchConditions,
			],
			expands: [
				...this.expands,
				...this.defaultExpands
			],
		};
	}

	@Input()
	set queryParams(queryParams: QueryParams) {
		this.pageIndex = queryParams.pageIndex;
		this.pageSize = queryParams.pageSize;
		this.orderBy = queryParams.orderBy;
		this.where = queryParams.where;
		this.expands = queryParams.expands;
	}

	/**
	 * Whether the current page is the last page or not
	 */
	get isLastPage(): boolean {
		return Math.max(Math.floor((this.fishsCount - 1) / this.pageSize), 0) === this.pageIndex;
	}

	/**
	 * List of all header options for the collection
	 */
	readonly headerOptions: HeaderOption[] = this.modelProperties.map(prop => {
		return {
			...prop,
			sortable: true,
			sourceDirectFromModel: true,
			valueSource: prop.name
		} as HeaderOption;
	}).filter(opt => opt.name !== 'id').filter(opt => !opt.doHide);

	/**
	 * Additional actions to be added as buttons on the menu of the collection.
	 */
	collectionActions: Action[] = [
		{
			label: CollectionActionEnum.Create,
			icon: 'create',
			iconPos: IconPosition.TOP,
			showIcon: true,
			isAdditional: false,
			disableOption: () => !this.canCreate()
		},

	];

	/**
	 * Additional actions to be added as buttons on each row of the collection.
	 */
	itemActions: Action[] = [
		{
			label: ItemActionEnum.Edit,
			icon: 'edit',
			iconPos: IconPosition.TOP,
			showIcon: true,
			isAdditional: false,
			disableOption: () => !this.canEdit()
		},
	];

	/**
	 * Additional actions to be added as buttons when any number of items are selected.
	 */
	multipleItemsActions: Action[] = [
	];

	/**
	 * The questions to be used in the filter
	 * TODO fix the enum value in java
	 */
	filterQuestions: FilterQuestion[] = [];

	/**
	 * The model to be created or edited depending on what the model currently is.
	 */
	targetModel: FishModel;

	/**
	 * The form group created from the target model
	 */
	modelFormGroup: FormGroup;


	constructor(
		private readonly store: Store<{ model: FishModelState }>,
		private authenticationService: AuthenticationService,
	) {

		this.fishs = this.store.select(getFishCollectionModels, this.collectionId);

	}

	ngOnInit() {

		this.store.dispatch(new modelAction.InitialiseFishCollectionState({
			queryParams: this.queryParams,
			collectionId: this.collectionId
		}));
	}

	ngOnChanges(changes: SimpleChanges): void {
		if(changes.hasOwnProperty('tankId') && this.tankId) {
			this.fetchAllModels();
		}
	}


	/**
	 * Triggered whenever a custom action against an item is clicked.
	 */
	onItemActionClicked(event: { actionName: string, payload?: any }) {

		if (event.actionName === ItemActionEnum.Edit) {

			this.fetchModel(event.payload.model.id);

		}

	}

	/**
	 * Triggered whenever a custom action against a collection is clicked.
	 */
	onCollectionActionClicked(event: { actionName: string, payload?: any }) {

		if (event.actionName === CollectionActionEnum.Create) {
			this.onCreateClicked();
		}

	}

	/**
	 * Triggered whenever a custom action against a selection of items is clicked.
	 */
	onMultipleItemsActionClicked(event: { actionName: string, payload?: any }) {

	}

	/**
	 * Triggered when the user clicks on the `Create new` button.
	 */
	onCreateClicked() {
		this.createModel();
	}

	/**
	 * Triggered when the `Create` or `Save` button is clicked in the child create/edit view.
	 */
	onCreateOrSaveClicked(event: { isCreate: boolean, payload?: { [s: string]: any } }) {

		const afterAction = [
			new modelAction.FetchFishModelsWithQuery({
				queryParams: this.queryParams,
				collectionId: this.collectionId
			},
			[
				new modelAction.CountFishModels()
			]
		)];

		if (event.isCreate) {
			let stateConfig: PassableStateConfig<FishModel> = {
				targetModel: this.targetModel,
				queryParams: this.queryParams,
				collectionId: this.collectionId
			};


			this.store.dispatch(new modelAction.CreateFishModel(
				stateConfig,
				afterAction
			));

			this.targetModel = null;

		} else {
			let stateConfig: PassableStateConfig<FishModel> = {
				targetModel: this.targetModel,
				collectionId: this.collectionId,
				updates: event.payload,
				queryParams: this.queryParams
			};


			this.store.dispatch(new modelAction.UpdateFishModel(
				stateConfig,
				afterAction
			));
		}

	}

	/**
	 * Triggered whenever the `Cancel` of the create/edit child component is clicked.
	 */
	onCancelClicked() {

		this.targetModel = null;

	}

	/**
	 * Triggered when a header in the collection is clicked
	 */
	onCollectionSort($event: OrderBy[]) {

		this.orderBy = $event;


		let stateConfig: PassableStateConfig<FishModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchFishModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when a filter is applied
	 */
	onCollectionFilter($event: { isClean?: boolean, filterFormGroup: FormGroup }) {

		this.filterConditions = FishModel.convertFilterToCondition($event.filterFormGroup);


		let stateConfig: PassableStateConfig<FishModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchFishModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when type or hit the enter in the search box
	 * Do http request to fetch the search results
	 */
	onCollectionSearch(searchText: string) {

		this.pageIndex = 0;

		this.searchConditions = this.searchFields.map((field) => ({
			path: field,
			operation: QueryOperation.CONTAINS,
			value: searchText
		}));


		let stateConfig: PassableStateConfig<FishModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchFishModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for first page.
	 */
	onFirstPageRequested() {

		this.pageIndex = 0;


		let stateConfig: PassableStateConfig<FishModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchFishModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for previous page.
	 */
	onPrevPageRequested() {

		this.pageIndex -= 1;


		let stateConfig: PassableStateConfig<FishModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchFishModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for next page.
	 */
	onNextPageRequested() {

		this.pageIndex += 1;


		let stateConfig: PassableStateConfig<FishModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchFishModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for last page.
	 */
	onLastPageRequested() {

		this.pageIndex = Math.floor((this.fishsCount - 1) / this.pageSize);


		let stateConfig: PassableStateConfig<FishModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};

		this.store.dispatch(new modelAction.FetchFishModelsWithQuery(stateConfig));

	}

	/**
	 * Add the search function for each of the relations.
	 *
	 * TODO refactor this part of the code. Extract the search function and debounce function to a separate file
	 */
	private addSearchFunction(modelRelation: ModelRelation, modelSelector: any, action: new (...args: any[]) => NgRxAction) {
		modelRelation.searchFunction = new Subject<string>();
		modelRelation.collection = this.store.select(modelSelector, this.collectionId);
		modelRelation.searchFunction.pipe(
			debounceTime(500),
			distinctUntilChanged(),
			filter(value => value != null)
		).subscribe(
			(term: string) => {
				modelRelation.stateConfig.queryParams = {
					pageSize: this.pageSize,
					pageIndex: 0,
					where: [
						[
							{
								path: modelRelation.displayName,
								operation: QueryOperation.CONTAINS,
								value: term
							}
						]
					]
				};
				this.store.dispatch(new action(modelRelation.stateConfig));
			}
		);
	}

	/**
	 * Fetch model by id
	 * @param {string} modelId
	 */
	private fetchModel(modelId: string) {

		const stateConfig: PassableStateConfig<FishModel> = {
			targetModelId: modelId,
			queryParams: this.queryParams,
		};

		// % protected region % [Add additional processing for state configuration here] off begin
		// % protected region % [Add additional processing for state configuration here] end

		this.createReactiveForm();

		this.store.dispatch(new modelAction.FetchFishModel(stateConfig));
		this.store.select(getFishModelWithId, modelId).subscribe(model => {
			this.targetModel = model;
			if (this.targetModel) {
				this.modelFormGroup.patchValue(this.targetModel);
			}
		});
	}

	/**
	 * Fetch all models based on application id
	 */
	private fetchAllModels() {
		// Fetch activitied based on the model
		this.store.dispatch(new modelAction.FetchFishModelsWithQuery({
				queryParams: this.queryParams,
				collectionId: this.collectionId
			},
			[
				new modelAction.CountFishModels()
			]
		));

		this.store.select(getFishCollectionCount, this.collectionId).subscribe(
			(count) => this.fishsCount = count
		);
	}

	/**
	 * Create a new model
	 */
	private createModel() {
		this.createReactiveForm();
		this.targetModel = new FishModel({
			tankId: this.tankId
		});
		this.modelFormGroup.patchValue(this.targetModel);
	}

	/**
	 * Create the reactive form from the input model
	 */
	private createReactiveForm() {

		this.modelFormGroup = createReactiveFormFromModel(this.modelProperties, this.modelRelations, false);

	}

	/**
	 * Check if current user can create this entity
	 *
	 * @returns True if create is allowed for current user
	 */
	private canCreate(): boolean {

		const allowedGroups = [
			'ADMIN',
		];

		return this.authenticationService.isPermitted(allowedGroups);
	}

	/**
	 * Check if current user can edit this entity
	 *
	 * @returns True if create is allowed for current user
	 */
	private canEdit(): boolean {

		const allowedGroups = [
			'ADMIN',
		];

		return this.authenticationService.isPermitted(allowedGroups);
	}


}
