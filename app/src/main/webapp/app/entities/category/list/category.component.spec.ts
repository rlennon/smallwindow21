import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CategoryService } from '../service/category.service';

import { CategoryComponent } from './category.component';

describe('Component Tests', () => {
  describe('Category Management Component', () => {
    let comp: CategoryComponent;
    let fixture: ComponentFixture<CategoryComponent>;
    let service: CategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CategoryComponent],
      })
        .overrideTemplate(CategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CategoryService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categories?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
