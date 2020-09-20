import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPath } from 'app/shared/model/path.model';

type EntityResponseType = HttpResponse<IPath>;
type EntityArrayResponseType = HttpResponse<IPath[]>;

@Injectable({ providedIn: 'root' })
export class PathService {
  public resourceUrl = SERVER_API_URL + 'api/paths';

  constructor(protected http: HttpClient) {}

  create(path: IPath): Observable<EntityResponseType> {
    return this.http.post<IPath>(this.resourceUrl, path, { observe: 'response' });
  }

  update(path: IPath): Observable<EntityResponseType> {
    return this.http.put<IPath>(this.resourceUrl, path, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPath>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPath[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
