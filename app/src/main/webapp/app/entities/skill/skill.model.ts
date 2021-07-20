import { ICategory } from 'app/entities/category/category.model';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface ISkill {
  id?: number;
  title?: string | null;
  description?: string | null;
  categories?: ICategory[] | null;
  employees?: IEmployee[] | null;
}

export class Skill implements ISkill {
  constructor(
    public id?: number,
    public title?: string | null,
    public description?: string | null,
    public categories?: ICategory[] | null,
    public employees?: IEmployee[] | null
  ) {}
}

export function getSkillIdentifier(skill: ISkill): number | undefined {
  return skill.id;
}
