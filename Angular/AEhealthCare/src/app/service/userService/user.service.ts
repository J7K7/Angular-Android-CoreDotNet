import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/interface/user';
import {UserAddress} from 'src/app/interface/user-address';
import {UserSpecialization} from 'src/app/interface/user-specialization';
import {UserSchedule} from 'src/app/interface/user-schedule';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  LoginUser(user: User): Observable<any> {
    return this.http.post(`${this.apiUrl}Users/Login`, user);
  }

  RegisterUser(user: User): Observable<any> {
    user.UserType = 3;
    return this.http.post(`${this.apiUrl}Users/RegisterDoctor`, user);
  }

  UserUpdate(user: User): Observable<any> {
    return this.http.put(`${this.apiUrl}Users/PutUpdateUser`, user);
  }

  ChnagePassword(UserId: number = 0, oldPassword: string, NewPassword: string): Observable<any>{
    let myParams = new HttpParams({ fromString: `UserId=${UserId}&oldPassword=${oldPassword}&NewPassword=${NewPassword}` });
    return this.http.get(`${this.apiUrl}Users/ChangePassword`, { params: myParams } );
  }

  UserAddress(UserId: number = 0): Observable<any> {
    let myParams = new HttpParams({ fromString: `UserId=${UserId}` });
    return this.http.get(`${this.apiUrl}UsersAddress/GetByUser`, { params: myParams });
  }

  AddUserAddress(userAddress: UserAddress): Observable<any> {
    return this.http.post(`${this.apiUrl}UsersAddress/Post`, userAddress);
  }

  EditUserAddress(userAddress: UserAddress): Observable<any> {
    return this.http.post(`${this.apiUrl}UsersAddress/Put`, userAddress);
  }

  UserProfileUpdate(file: File, UserId: number = 0): Observable<any> {
    let myParams = new HttpParams({ fromString: `UserId=${UserId}` });
    const formData = new FormData();
    formData.append("file", file!, file.name);
    return this.http.post(`${this.apiUrl}UserImages/PostProfileImage`, formData, {params : myParams });
  }

  DoctorSpecialization(UserId: number = 0): Observable<any> {
    let myParams = new HttpParams({ fromString: `userId=${UserId}` });
    return this.http.get(`${this.apiUrl}DoctorSpecializations/ViewDoctorSpecialization`, { params: myParams });
  }

  AddDoctorSpecialization(userSpecialization: UserSpecialization): Observable<any>{
    return this.http.post(`${this.apiUrl}DoctorSpecializations/AddDoctorSpecialization`, userSpecialization);
  }

  GetHospitalsList(): Observable<any> {
    return this.http.get(`${this.apiUrl}Users/GetHospitals`);
  }

  GetHospitals(Id: number = 0): Observable<any>{
    let myParams = new HttpParams({ fromString: `doctorId=${Id}` });
    return this.http.get(`${this.apiUrl}Users/GetHospitalsByDoctor`, { params : myParams });
  }

  AddDoctorSchedule(userSchedule: UserSchedule): Observable<any>{
    return this.http.post(`${this.apiUrl}DoctorSchedules/Post`, userSchedule);
  }

  getDoctorSchedule(Id: number = 0): Observable<any>{
    let myParams = new HttpParams({ fromString: `DoctorId=${Id}` });
    return this.http.get(`${this.apiUrl}DoctorSchedules/GetHospitalsByDoctor`, { params : myParams });
  }

}
