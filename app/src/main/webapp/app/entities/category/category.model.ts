import { ISkill } from 'app/entities/skill/skill.model';

export interface ICategory {
  id?: number;
  categoryName?: string;
  skills?: ISkill[] | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public categoryName?: string, public skills?: ISkill[] | null) {}
}

export function getCategoryIdentifier(category: ICategory): number | undefined {
  return category.id;
}
