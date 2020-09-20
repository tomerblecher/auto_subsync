import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'path',
        loadChildren: () => import('./path/path.module').then(m => m.SubtitleSyncPathModule),
      },
      {
        path: 'extension',
        loadChildren: () => import('./extension/extension.module').then(m => m.SubtitleSyncExtensionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SubtitleSyncEntityModule {}
