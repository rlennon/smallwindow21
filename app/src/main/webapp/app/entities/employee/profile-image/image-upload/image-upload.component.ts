import { Component, Output, EventEmitter } from '@angular/core';
import { ImageCroppedEvent } from 'ngx-image-cropper';

@Component({
  selector: 'jhi-profile-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss'],
})
export class ImageUploadComponent {
  imageChangedEvent: any = '';
  croppedImage: any = '';
  fileToSave: any;

  @Output() newCroppedImageEvent = new EventEmitter<File>();

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent): void {
    this.croppedImage = event.base64;

    this.fileToSave = this.base64ToFile(event.base64, this.imageChangedEvent.target.files[0].name);

    this.newCroppedImageEvent.emit(this.fileToSave);
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

  private base64ToFile(data: any, filename: string): File {
    const arr = data.split(',');
    // eslint-disable-next-line @typescript-eslint/prefer-regexp-exec
    const mime = arr[0].match(/:(.*?);/)![1];
    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);

    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }

    return new File([u8arr], filename, { type: mime });
  }
}
