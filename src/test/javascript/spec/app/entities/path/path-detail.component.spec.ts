import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubtitleSyncTestModule } from '../../../test.module';
import { PathDetailComponent } from 'app/entities/path/path-detail.component';
import { Path } from 'app/shared/model/path.model';

describe('Component Tests', () => {
  describe('Path Management Detail Component', () => {
    let comp: PathDetailComponent;
    let fixture: ComponentFixture<PathDetailComponent>;
    const route = ({ data: of({ path: new Path(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubtitleSyncTestModule],
        declarations: [PathDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PathDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PathDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load path on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.path).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
