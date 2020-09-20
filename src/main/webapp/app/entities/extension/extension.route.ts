import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExtension, Extension } from 'app/shared/model/extension.model';
import { ExtensionService } from './extension.service';
import { ExtensionComponent } from './extension.component';
import { ExtensionDetailComponent } from './extension-detail.component';
import { ExtensionUpdateComponent } from './extension-update.component';

@Injectable({ providedIn: 'root' })
export class ExtensionResolve implements Resolve<IExtension> {
  constructor(private service: ExtensionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExtension> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((extension: HttpResponse<Extension>) => {
          if (extension.body) {
            return of(extension.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Extension());
  }
}

export const extensionRoute: Routes = [
  {
    path: '',
    component: ExtensionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'subtitleSyncApp.extension.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExtensionDetailComponent,
    resolve: {
      extension: ExtensionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'subtitleSyncApp.extension.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExtensionUpdateComponent,
    resolve: {
      extension: ExtensionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'subtitleSyncApp.extension.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExtensionUpdateComponent,
    resolve: {
      extension: ExtensionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'subtitleSyncApp.extension.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
