import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPath, Path } from 'app/shared/model/path.model';
import { PathService } from './path.service';
import { PathComponent } from './path.component';
import { PathDetailComponent } from './path-detail.component';
import { PathUpdateComponent } from './path-update.component';

@Injectable({ providedIn: 'root' })
export class PathResolve implements Resolve<IPath> {
  constructor(private service: PathService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPath> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((path: HttpResponse<Path>) => {
          if (path.body) {
            return of(path.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Path());
  }
}

export const pathRoute: Routes = [
  {
    path: '',
    component: PathComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'subtitleSyncApp.path.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PathDetailComponent,
    resolve: {
      path: PathResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'subtitleSyncApp.path.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PathUpdateComponent,
    resolve: {
      path: PathResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'subtitleSyncApp.path.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PathUpdateComponent,
    resolve: {
      path: PathResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'subtitleSyncApp.path.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
