import { Component, Output, EventEmitter } from '@angular/core';
import { ImageCroppedEvent } from 'ngx-image-cropper';

@Component({
  selector: 'jhi-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss'],
})
export class ImageUploadComponent {
  imageChangedEvent: any = '';
  croppedImage: any = '';

  @Output() newCroppedImageEvent = new EventEmitter<string>();

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent): void {
    this.croppedImage = event.base64;
    this.newCroppedImageEvent.emit(this.croppedImage);
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
