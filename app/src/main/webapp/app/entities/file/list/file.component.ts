import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { IFile } from '../file.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { FileService } from '../service/file.service';
import { FileDeleteDialogComponent } from '../delete/file-delete-dialog.component';

@Component({
  selector: 'jhi-file',
  templateUrl: './file.component.html',
})
export class FileComponent implements OnInit {
  files?: IFile[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchFieldOptions = [
    { value: 's3FileKey', label: 'S 3 File Key' },
    { value: 'employee.email', label: 'Employee' },
  ];

  criteria = {
    searchData: '',
    searchField: 's3FileKey',
  };

  activeSearchData = '';
  activeSearchField = '';

  constructor(
    protected fileService: FileService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    if (this.activeSearchData) {
      const criteria = [{ key: `${this.activeSearchField}.contains`, value: `${this.activeSearchData}` }];
      this.fileService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          criteria,
        })
        .subscribe(
          (res: HttpResponse<IFile[]>) => {
            this.isLoading = false;
            this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
          },
          () => {
            this.isLoading = false;
            this.onError();
          }
        );
      return;
    }
    this.fileService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IFile[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  trackId(index: number, item: IFile): number {
    return item.id!;
  }

  delete(file: IFile): void {
    const modalRef = this.modalService.open(FileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.file = file;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  searchFiles(): void {
    this.activeSearchData = this.criteria.searchData;
    this.activeSearchField = this.criteria.searchField;
    this.page = 1;
    this.ngbPaginationPage = 1;
    this.loadPage();
  }

  clearSearch(): void {
    this.page = 0;
    this.criteria.searchData = '';
    this.activeSearchData = '';
    this.loadPage();
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IFile[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/file'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.files = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
