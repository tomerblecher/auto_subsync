import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExtension, Extension } from 'app/shared/model/extension.model';
import { ExtensionService } from './extension.service';

@Component({
  selector: 'jhi-extension-update',
  templateUrl: './extension-update.component.html',
})
export class ExtensionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected extensionService: ExtensionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extension }) => {
      this.updateForm(extension);
    });
  }

  updateForm(extension: IExtension): void {
    this.editForm.patchValue({
      id: extension.id,
      name: extension.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const extension = this.createFromForm();
    if (extension.id !== undefined) {
      this.subscribeToSaveResponse(this.extensionService.update(extension));
    } else {
      this.subscribeToSaveResponse(this.extensionService.create(extension));
    }
  }

  private createFromForm(): IExtension {
    return {
      ...new Extension(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtension>>): void {
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
