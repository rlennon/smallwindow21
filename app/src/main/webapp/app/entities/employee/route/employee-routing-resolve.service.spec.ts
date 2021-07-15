jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEmployee, Employee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';

import { EmployeeRoutingResolveService } from './employee-routing-resolve.service';

describe('Service Tests', () => {
  describe('Employee routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EmployeeRoutingResolveService;
    let service: EmployeeService;
    let resultEmployee: IEmployee | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EmployeeRoutingResolveService);
      service = TestBed.inject(EmployeeService);
      resultEmployee = undefined;
    });

    describe('resolve', () => {
      it('should return IEmployee returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployee = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmployee).toEqual({ id: 123 });
      });

      it('should return new IEmployee if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployee = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEmployee).toEqual(new Employee());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployee = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmployee).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
