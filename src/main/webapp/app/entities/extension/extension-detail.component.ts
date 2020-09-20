import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtension } from 'app/shared/model/extension.model';

@Component({
  selector: 'jhi-extension-detail',
  templateUrl: './extension-detail.component.html',
})
export class ExtensionDetailComponent implements OnInit {
  extension: IExtension | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extension }) => (this.extension = extension));
  }

  previousState(): void {
    window.history.back();
  }
}
