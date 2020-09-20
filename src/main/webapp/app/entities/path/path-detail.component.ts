import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPath } from 'app/shared/model/path.model';

@Component({
  selector: 'jhi-path-detail',
  templateUrl: './path-detail.component.html',
})
export class PathDetailComponent implements OnInit {
  path: IPath | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ path }) => (this.path = path));
  }

  previousState(): void {
    window.history.back();
  }
}
