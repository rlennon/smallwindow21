import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { EmployeeService } from '../service/employee.service';

@Component({
  selector: 'jhi-upload-files',
  templateUrl: './upload-files.component.html',
  styleUrls: ['./upload-files.component.scss'],
})
export class UploadFilesComponent implements OnInit {
  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';

  fileInfos?: Observable<any>;
  @Input()
  employeeId = '';

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.fileInfos = this.employeeService.getFiles(this.employeeId);
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  downloadFile(fileId: string, s3FileKey: string, s3FileType: string): void {
    this.employeeService.downloadFile(fileId, s3FileKey, s3FileType);
  }

  deleteFile(fileId: string): void {
    if (confirm('Are you sure to delete this file?')) {
      this.employeeService.deleteFile(fileId).subscribe(
        () => (this.fileInfos = this.employeeService.getFiles(this.employeeId)),
        () => {
          alert('could not delete');
          this.message = 'Could not delete the file!';
          this.currentFile = undefined;
        }
      );
    }
  }

  upload(): void {
    this.progress = 0;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.employeeService.upload(this.currentFile, this.employeeId).subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round((100 * event.loaded) / event.total);
              if (this.progress === 100) {
                this.currentFile = undefined;
              }
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
              this.fileInfos = this.employeeService.getFiles(this.employeeId);
            }
          },
          (err: any) => {
            this.progress = 0;

            if (err.error?.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Could not upload the file!';
            }
            this.currentFile = undefined;
          }
        );
      }

      this.selectedFiles = undefined;
    }
  }
}
