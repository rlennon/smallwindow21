import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EmployeeComponent } from './list/employee.component';
import { EmployeeDetailComponent } from './detail/employee-detail.component';
import { EmployeeUpdateComponent } from './update/employee-update.component';
import { EmployeeDeleteDialogComponent } from './delete/employee-delete-dialog.component';
import { EmployeeRoutingModule } from './route/employee-routing.module';
import { ImageCropperModule } from 'ngx-image-cropper';
import { ImageUploadComponent } from 'app/ProfileImage/image-upload/image-upload.component';
import { ViewComponent } from 'app/ProfileImage/image-view/image-view.component';

@NgModule({
  imports: [SharedModule, EmployeeRoutingModule, ImageCropperModule],
  declarations: [
    EmployeeComponent,
    EmployeeDetailComponent,
    EmployeeUpdateComponent,
    EmployeeDeleteDialogComponent,
    ImageUploadComponent,
    ViewComponent,
  ],
  entryComponents: [EmployeeDeleteDialogComponent],
})
export class EmployeeModule {}
