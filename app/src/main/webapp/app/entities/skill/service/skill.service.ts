import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISkill, getSkillIdentifier } from '../skill.model';

export type EntityResponseType = HttpResponse<ISkill>;
export type EntityArrayResponseType = HttpResponse<ISkill[]>;

@Injectable({ providedIn: 'root' })
export class SkillService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/skills');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(skill: ISkill): Observable<EntityResponseType> {
    return this.http.post<ISkill>(this.resourceUrl, skill, { observe: 'response' });
  }

  update(skill: ISkill): Observable<EntityResponseType> {
    return this.http.put<ISkill>(`${this.resourceUrl}/${getSkillIdentifier(skill) as number}`, skill, { observe: 'response' });
  }

  partialUpdate(skill: ISkill): Observable<EntityResponseType> {
    return this.http.patch<ISkill>(`${this.resourceUrl}/${getSkillIdentifier(skill) as number}`, skill, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSkillToCollectionIfMissing(skillCollection: ISkill[], ...skillsToCheck: (ISkill | null | undefined)[]): ISkill[] {
    const skills: ISkill[] = skillsToCheck.filter(isPresent);
    if (skills.length > 0) {
      const skillCollectionIdentifiers = skillCollection.map(skillItem => getSkillIdentifier(skillItem)!);
      const skillsToAdd = skills.filter(skillItem => {
        const skillIdentifier = getSkillIdentifier(skillItem);
        if (skillIdentifier == null || skillCollectionIdentifiers.includes(skillIdentifier)) {
          return false;
        }
        skillCollectionIdentifiers.push(skillIdentifier);
        return true;
      });
      return [...skillsToAdd, ...skillCollection];
    }
    return skillCollection;
  }
}
