import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEmployee, Employee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  s3ImageKey = '';
  employeeId = '';
  croppedImage!: File;
  imageAvailable = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [null, [Validators.required]],
    s3ImageKey: [],
  });

  constructor(protected employeeService: EmployeeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
      this.s3ImageKey = employee.s3ImageKey;
      this.employeeId = employee.id;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.s3ImageKey = `profile_${employee.id}.png`;
      employee.s3ImageKey = this.s3ImageKey;
      this.subscribeToSaveEmployeeResponse(this.employeeService.update(employee));
    } else {
      this.s3ImageKey = `profile_${Math.random()}.png`;
      employee.s3ImageKey = this.s3ImageKey;
      this.subscribeToSaveEmployeeResponse(this.employeeService.create(employee));
    }
  }

  setCroppedImage(croppedImage: File): void {
    this.croppedImage = croppedImage;
    this.imageAvailable = true;
  }

  protected subscribeToSaveEmployeeResponse(result: Observable<HttpResponse<IEmployee>>): void {
    result.pipe(finalize(() => this.onSaveEmployeeFinalize())).subscribe(
      () => this.onSaveEmployeeSuccess(),
      () => this.onSaveEmployeeError()
    );
  }

  protected onSaveEmployeeSuccess(): void {
    if (!this.imageAvailable) {
      this.previousState();
    } else {
      this.subscribeToSaveEmployeeImageResponse(this.employeeService.saveImage(this.s3ImageKey, this.croppedImage));
    }
  }

  protected onSaveEmployeeError(): void {
    // Api for inheritance.
  }

  protected onSaveEmployeeFinalize(): void {
    // this.isSaving = false;
  }

  protected subscribeToSaveEmployeeImageResponse(result: Observable<HttpResponse<boolean>>): void {
    result.pipe(finalize(() => this.onSaveEmployeeImageFinalize())).subscribe(
      () => this.onSaveEmployeeImageSuccess(),
      () => this.onSaveEmployeeImageError()
    );
  }

  protected onSaveEmployeeImageFinalize(): void {
    this.isSaving = false;
  }
  protected onSaveEmployeeImageSuccess(): void {
    this.previousState();
  }

  protected onSaveEmployeeImageError(): void {
    // Api for inheritance.
  }
  protected updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      s3ImageKey: employee.s3ImageKey,
    });
  }

  protected createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      s3ImageKey: this.editForm.get(['s3ImageKey'])!.value,
    };
  }
}
