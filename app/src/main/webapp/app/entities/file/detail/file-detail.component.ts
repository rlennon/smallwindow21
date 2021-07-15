import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFile } from '../file.model';

@Component({
  selector: 'jhi-file-detail',
  templateUrl: './file-detail.component.html',
})
export class FileDetailComponent implements OnInit {
  file: IFile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ file }) => {
      this.file = file;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
