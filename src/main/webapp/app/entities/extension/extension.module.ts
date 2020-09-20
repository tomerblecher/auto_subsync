import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubtitleSyncSharedModule } from 'app/shared/shared.module';
import { ExtensionComponent } from './extension.component';
import { ExtensionDetailComponent } from './extension-detail.component';
import { ExtensionUpdateComponent } from './extension-update.component';
import { ExtensionDeleteDialogComponent } from './extension-delete-dialog.component';
import { extensionRoute } from './extension.route';

@NgModule({
  imports: [SubtitleSyncSharedModule, RouterModule.forChild(extensionRoute)],
  declarations: [ExtensionComponent, ExtensionDetailComponent, ExtensionUpdateComponent, ExtensionDeleteDialogComponent],
  entryComponents: [ExtensionDeleteDialogComponent],
})
export class SubtitleSyncExtensionModule {}
