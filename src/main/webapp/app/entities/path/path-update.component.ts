import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPath, Path } from 'app/shared/model/path.model';
import { PathService } from './path.service';

@Component({
  selector: 'jhi-path-update',
  templateUrl: './path-update.component.html',
})
export class PathUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    path: [],
  });

  constructor(protected pathService: PathService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ path }) => {
      this.updateForm(path);
    });
  }

  updateForm(path: IPath): void {
    this.editForm.patchValue({
      id: path.id,
      name: path.name,
      path: path.path,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const path = this.createFromForm();
    if (path.id !== undefined) {
      this.subscribeToSaveResponse(this.pathService.update(path));
    } else {
      this.subscribeToSaveResponse(this.pathService.create(path));
    }
  }

  private createFromForm(): IPath {
    return {
      ...new Path(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      path: this.editForm.get(['path'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPath>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
