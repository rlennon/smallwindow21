jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SkillService } from '../service/skill.service';
import { ISkill, Skill } from '../skill.model';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { SkillUpdateComponent } from './skill-update.component';

describe('Component Tests', () => {
  describe('Skill Management Update Component', () => {
    let comp: SkillUpdateComponent;
    let fixture: ComponentFixture<SkillUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let skillService: SkillService;
    let categoryService: CategoryService;
    let employeeService: EmployeeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SkillUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SkillUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      skillService = TestBed.inject(SkillService);
      categoryService = TestBed.inject(CategoryService);
      employeeService = TestBed.inject(EmployeeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Category query and add missing value', () => {
        const skill: ISkill = { id: 456 };
        const categories: ICategory[] = [{ id: 15061 }];
        skill.categories = categories;

        const categoryCollection: ICategory[] = [{ id: 69174 }];
        spyOn(categoryService, 'query').and.returnValue(of(new HttpResponse({ body: categoryCollection })));
        const additionalCategories = [...categories];
        const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
        spyOn(categoryService, 'addCategoryToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ skill });
        comp.ngOnInit();

        expect(categoryService.query).toHaveBeenCalled();
        expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(categoryCollection, ...additionalCategories);
        expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Employee query and add missing value', () => {
        const skill: ISkill = { id: 456 };
        const employees: IEmployee[] = [{ id: 21874 }];
        skill.employees = employees;

        const employeeCollection: IEmployee[] = [{ id: 22630 }];
        spyOn(employeeService, 'query').and.returnValue(of(new HttpResponse({ body: employeeCollection })));
        const additionalEmployees = [...employees];
        const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
        spyOn(employeeService, 'addEmployeeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ skill });
        comp.ngOnInit();

        expect(employeeService.query).toHaveBeenCalled();
        expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const skill: ISkill = { id: 456 };
        const categories: ICategory = { id: 19578 };
        skill.categories = [categories];
        const employees: IEmployee = { id: 79413 };
        skill.employees = [employees];

        activatedRoute.data = of({ skill });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(skill));
        expect(comp.categoriesSharedCollection).toContain(categories);
        expect(comp.employeesSharedCollection).toContain(employees);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const skill = { id: 123 };
        spyOn(skillService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ skill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: skill }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(skillService.update).toHaveBeenCalledWith(skill);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const skill = new Skill();
        spyOn(skillService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ skill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: skill }));
        saveSubject.complete();

        // THEN
        expect(skillService.create).toHaveBeenCalledWith(skill);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const skill = { id: 123 };
        spyOn(skillService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ skill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(skillService.update).toHaveBeenCalledWith(skill);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCategoryById', () => {
        it('Should return tracked Category primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategoryById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEmployeeById', () => {
        it('Should return tracked Employee primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEmployeeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedCategory', () => {
        it('Should return option if no Category is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedCategory(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Category for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedCategory(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Category is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedCategory(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });

      describe('getSelectedEmployee', () => {
        it('Should return option if no Employee is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedEmployee(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Employee for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedEmployee(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Employee is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedEmployee(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
