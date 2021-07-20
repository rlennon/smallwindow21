import { IEmployee } from 'app/entities/employee/employee.model';

export interface IFile {
  id?: number;
  s3FileKey?: string | null;
  s3FileType?: string | null;
  employee?: IEmployee | null;
}

export class File implements IFile {
  constructor(
    public id?: number,
    public s3FileKey?: string | null,
    public s3FileType?: string | null,
    public employee?: IEmployee | null
  ) {}
}

export function getFileIdentifier(file: IFile): number | undefined {
  return file.id;
}
