import { Component, Input } from '@angular/core';
import { ImageCroppedEvent } from 'ngx-image-cropper';

@Component({
  selector: 'jhi-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss'],
})
export class ImageUploadComponent {
  imageChangedEvent: any = '';
  croppedImage: any = '';

  @Input()
  employeeId: any = '';

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent): void {
    alert(this.employeeId);
    this.croppedImage = event.base64;
  }
  imageLoaded(): void {
    /* show cropper */
  }
  cropperReady(): void {
    /* cropper ready */
  }
  loadImageFailed(): void {
    /* show message */
  }
}
