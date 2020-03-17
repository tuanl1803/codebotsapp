import {ApplicationRef, Component, forwardRef, Injector, Input, OnInit, ViewContainerRef} from '@angular/core';
import {AbstractInputComponent, InputClassPrefix} from '../abstract.input.component';
import {IModalDialog, ModalDialogService} from 'ngx-modal-dialog';
import {Subject} from 'rxjs';
import {first} from 'rxjs/operators';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

/**
 * ControlValueAccessor for the Textfield Component
 * Use the ControlValueAccessor to make the component behave like native input element
 */
const CB_CUSTOM_REFERENCE_FILTER_VALUE_ACCESSOR: any = {
	provide: NG_VALUE_ACCESSOR,
	useExisting: forwardRef(() => CustomReferenceFilterComponent),
	multi: true
};

@Component({
	selector: 'cb-custom-reference-filter',
	templateUrl: './custom-reference-filter.component.html',
	styleUrls: ['./custom-reference-filter.component.scss'],
	providers: [CB_CUSTOM_REFERENCE_FILTER_VALUE_ACCESSOR],
})
export class CustomReferenceFilterComponent extends AbstractInputComponent implements OnInit, ControlValueAccessor {

	/**
	 * Whether the component is an input or an input group
	 */
	protected classPrefix = InputClassPrefix.INPUT;

	/**
	 * The type of the component, which would be used in the css class of the dom
	 */
	protected componentType = 'custom-reference';

	/**
	 * Modal Component to display afterward
	 */
	@Input()
	modalComponent: IModalDialog;

	@Input()
	viewRef: ViewContainerRef;

	/**
	 * Actual value
	 */
	@Input()
	value;

	/**
	 * Value to display in the text field
	 */
	@Input()
	valueToDisplay;

	/**
	 * Default constructor for the textfield
	 */
	constructor(
		protected injector: Injector,
		private  modalService: ModalDialogService,
		private defaultViewRef: ViewContainerRef,
	) {

		super(injector);
	}

	ngOnInit() {

	}


	showCustomFilter() {
		const viewRefToDisplay = this.viewRef ? this.viewRef : this.defaultViewRef;

		const dataToModify: any = {};

		const valueChangeSubject: Subject<any> = new Subject();

		valueChangeSubject.pipe(first()).subscribe((valueChange) => {
			if (valueChange.value && valueChange.valueToDisplay) {
				this.valueToDisplay = valueChange.valueToDisplay;
				this.value = valueChange.value;
				this.controlValueAccessorChangeFn(this.value);
			}
		});

		this.modalService.openDialog(viewRefToDisplay, {
			data: {
				dataToModify: dataToModify,
				valueChangeSubject: valueChangeSubject,
			},
			settings: {
				closeButtonClass: 'close theme-icon-close',
				modalClass: 'modal-container modal fade ngx-modal'
			},
			childComponent: this.modalComponent,
			placeOnTop: false,

			title: `Search ${this.name}`,
		});
	}

	registerOnChange(fn: (value: any) => void) {
		this.controlValueAccessorChangeFn = fn;
	}

	registerOnTouched(fn: any): void {
		this.onTouched = fn;
	}

	setDisabledState(isDisabled: boolean): void {
		this.isDisabled = isDisabled;
	}

	writeValue(obj: any): void {
		this.value = obj;
	}
}
