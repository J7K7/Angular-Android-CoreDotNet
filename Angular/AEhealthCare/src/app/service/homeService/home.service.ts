import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  GetCountries(): Observable<any>{
    return this.http.get(`${this.apiUrl}Home/GetCountries`);
  }

  GetStates(Id: string): Observable<any>{
    let myParams = new HttpParams({ fromString: `countryId=${Id}` });
    return this.http.get(`${this.apiUrl}Home/GetStatesByCountryId`, { params : myParams });
  }

  GetCities(Id: number): Observable<any>{
    let myParams = new HttpParams({ fromString: `stateId=${Id}` });
    return this.http.get(`${this.apiUrl}Home/GetCityByStateId`, { params : myParams });
  }

  GetSpecialization(): Observable<any>{
    return this.http.get(`${this.apiUrl}Specializations/Get`);
  }

}
