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
  croppedImage = '';
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
      this.editForm.patchValue({
        s3ImageKey: this.s3ImageKey,
      });
      this.subscribeToSaveEmployeeResponse(this.employeeService.update(employee));
    } else {
      this.s3ImageKey = `profile_${Math.random()}.png`;
      this.editForm.patchValue({
        s3ImageKey: this.s3ImageKey,
      });
      this.subscribeToSaveEmployeeResponse(this.employeeService.create(employee));
    }
  }

  setCroppedImage(croppedImage: string): void {
    this.croppedImage = croppedImage;
  }

  protected subscribeToSaveEmployeeResponse(result: Observable<HttpResponse<IEmployee>>): void {
    result.pipe(finalize(() => this.onSaveEmployeeFinalize())).subscribe(
      res => this.onSaveEmployeeSuccess(res),
      () => this.onSaveEmployeeError()
    );
  }

  protected onSaveEmployeeSuccess(res: HttpResponse<IEmployee>): void {
    if (!this.croppedImage) {
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
      res => this.onSaveEmployeeImageSuccess(res),
      err => this.onSaveEmployeeImageError(err)
    );
  }

  protected onSaveEmployeeImageFinalize(): void {
    this.isSaving = false;
  }
  protected onSaveEmployeeImageSuccess(res: HttpResponse<boolean>): void {
    this.previousState();
  }

  protected onSaveEmployeeImageError(err: any): void {
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
