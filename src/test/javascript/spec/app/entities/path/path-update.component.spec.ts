import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubtitleSyncTestModule } from '../../../test.module';
import { PathUpdateComponent } from 'app/entities/path/path-update.component';
import { PathService } from 'app/entities/path/path.service';
import { Path } from 'app/shared/model/path.model';

describe('Component Tests', () => {
  describe('Path Management Update Component', () => {
    let comp: PathUpdateComponent;
    let fixture: ComponentFixture<PathUpdateComponent>;
    let service: PathService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubtitleSyncTestModule],
        declarations: [PathUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PathUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PathUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PathService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Path(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Path();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
