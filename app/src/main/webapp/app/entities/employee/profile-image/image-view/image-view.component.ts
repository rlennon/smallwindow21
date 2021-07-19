import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-profile-image-view',
  templateUrl: './image-view.component.html',
  styleUrls: ['./image-view.component.scss'],
})
export class ViewComponent implements OnInit {
  showImage = false;
  imageReadyToLoad = false;
  imageLoaded = false;
  imageToShow: any = null;

  @Input()
  showHeading = false;
  @Input()
  s3ImageKey = '';
  @Input()
  employeeId = '';
  @Input()
  showDelete = false;

  constructor(protected employeeService: EmployeeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    if (this.s3ImageKey && this.employeeId) {
      this.imageReadyToLoad = true;
      this.getProfileImage();
    }
  }

  getProfileImage(): any {
    if (this.imageReadyToLoad && !this.imageLoaded) {
      this.imageLoaded = true;

      if (this.s3ImageKey.endsWith('.png')) {
        this.subscribeToFetchEmployeeImageResponse(this.employeeService.getImage(this.s3ImageKey));
      }
    }
  }

  deleteImage(): void {
    if (confirm('Are you sure to delete this image?')) {
      this.subscribeToDeleteEmployeeImageResponse(this.employeeService.deleteImage(this.employeeId));
    }
  }

  protected subscribeToFetchEmployeeImageResponse(result: Observable<string>): void {
    result.subscribe(res => this.onFetchEmployeeImageSuccess(res));
  }
  protected onFetchEmployeeImageSuccess(res: string): void {
    // this.imageToShow = `data:image/png;base64, ${res}`;
    this.imageToShow = res;
    this.showImage = true;
  }

  protected subscribeToDeleteEmployeeImageResponse(result: Observable<HttpResponse<boolean>>): void {
    result.subscribe(() => this.onDeleteEmployeeImageSuccess());
  }
  protected onDeleteEmployeeImageSuccess(): void {
    this.showImage = false;
  }
}
