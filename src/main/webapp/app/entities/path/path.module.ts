import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubtitleSyncSharedModule } from 'app/shared/shared.module';
import { PathComponent } from './path.component';
import { PathDetailComponent } from './path-detail.component';
import { PathUpdateComponent } from './path-update.component';
import { PathDeleteDialogComponent } from './path-delete-dialog.component';
import { pathRoute } from './path.route';

@NgModule({
  imports: [SubtitleSyncSharedModule, RouterModule.forChild(pathRoute)],
  declarations: [PathComponent, PathDetailComponent, PathUpdateComponent, PathDeleteDialogComponent],
  entryComponents: [PathDeleteDialogComponent],
})
export class SubtitleSyncPathModule {}
