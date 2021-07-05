import { IFile } from 'app/entities/file/file.model';
import { ISkill } from 'app/entities/skill/skill.model';

export interface IEmployee {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string;
  s3ImageKey?: string | null;
  files?: IFile[] | null;
  skills?: ISkill[] | null;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string,
    public s3ImageKey?: string | null,
    public files?: IFile[] | null,
    public skills?: ISkill[] | null
  ) {}
}

export function getEmployeeIdentifier(employee: IEmployee): number | undefined {
  return employee.id;
}
