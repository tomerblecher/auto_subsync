import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPath } from 'app/shared/model/path.model';
import { PathService } from './path.service';

@Component({
  templateUrl: './path-delete-dialog.component.html',
})
export class PathDeleteDialogComponent {
  path?: IPath;

  constructor(protected pathService: PathService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pathService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pathListModification');
      this.activeModal.close();
    });
  }
}
