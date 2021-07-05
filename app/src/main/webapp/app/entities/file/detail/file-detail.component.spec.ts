import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FileDetailComponent } from './file-detail.component';

describe('Component Tests', () => {
  describe('File Management Detail Component', () => {
    let comp: FileDetailComponent;
    let fixture: ComponentFixture<FileDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FileDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ file: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load file on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.file).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
