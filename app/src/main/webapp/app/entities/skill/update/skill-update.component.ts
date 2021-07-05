import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISkill, Skill } from '../skill.model';
import { SkillService } from '../service/skill.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

@Component({
  selector: 'jhi-skill-update',
  templateUrl: './skill-update.component.html',
})
export class SkillUpdateComponent implements OnInit {
  isSaving = false;

  categoriesSharedCollection: ICategory[] = [];
  employeesSharedCollection: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    categories: [],
    employees: [],
  });

  constructor(
    protected skillService: SkillService,
    protected categoryService: CategoryService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skill }) => {
      this.updateForm(skill);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const skill = this.createFromForm();
    if (skill.id !== undefined) {
      this.subscribeToSaveResponse(this.skillService.update(skill));
    } else {
      this.subscribeToSaveResponse(this.skillService.create(skill));
    }
  }

  trackCategoryById(index: number, item: ICategory): number {
    return item.id!;
  }

  trackEmployeeById(index: number, item: IEmployee): number {
    return item.id!;
  }

  getSelectedCategory(option: ICategory, selectedVals?: ICategory[]): ICategory {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedEmployee(option: IEmployee, selectedVals?: IEmployee[]): IEmployee {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkill>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(skill: ISkill): void {
    this.editForm.patchValue({
      id: skill.id,
      title: skill.title,
      description: skill.description,
      categories: skill.categories,
      employees: skill.employees,
    });

    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      ...(skill.categories ?? [])
    );
    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing(
      this.employeesSharedCollection,
      ...(skill.employees ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, ...(this.editForm.get('categories')!.value ?? []))
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing(employees, ...(this.editForm.get('employees')!.value ?? []))
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }

  protected createFromForm(): ISkill {
    return {
      ...new Skill(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      categories: this.editForm.get(['categories'])!.value,
      employees: this.editForm.get(['employees'])!.value,
    };
  }
}
