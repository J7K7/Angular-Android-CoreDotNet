import { Component, OnChanges, OnInit } from '@angular/core';
import { User } from 'src/app/interface/user';
import { Country } from 'src/app/interface/country';
import { State } from 'src/app/interface/state';
import { City } from 'src/app/interface/city';
import { Title } from '@angular/platform-browser';
import { UserService } from 'src/app/service/userService/user.service';
import { HomeService } from 'src/app/service/homeService/home.service';
import { UserAddress } from 'src/app/interface/user-address';
import { UserSpecialization } from 'src/app/interface/user-specialization';
import { Specialization } from 'src/app/interface/specialization';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';
declare var $: any;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {

  countries: Country[];
  states: State[];
  cities: City[];
  userSpecializations: Array<UserSpecialization> = [];
  specialization: Specialization[];

  user: User;
  usr: User;
  userAddress: UserAddress;
  userSpecialization: UserSpecialization;

  private imgUrl = environment.imgUrl;
  sr: number = 0;
  localItem: string;
  title: string = 'profile';
  file: File;
  filename: string;
  userAddressId: number = 0;
  firstName?: string;
  lastName?: string;
  email?: string;
  mobile?: string;
  userName?: string;
  gender: string = '1';
  password: string;
  rePassword: string;
  building: string;
  street: string;
  country: string;
  state: string;
  city: string;
  userImage: string;
  selectedSpecialization: string;

  constructor(private titleService:Title, private userService: UserService, private homeService: HomeService, private router: Router) { 
    
    this.localItem = localStorage.getItem("user") || "";
    this.user = JSON.parse(this.localItem);

    this.firstName = this.user.FirstName;
    this.lastName = this.user.LastName;
    this.email = this.user.Email;
    this.mobile = this.user.MobileNo;
    this.gender = this.user.Gender?.toString() || '1';
    this.userImage = this.user.userImage || '';

    this.homeService.GetCountries().subscribe(
      (response) => {
        this.countries = response;
        this.getUserAddress();
      },
      (error: any) => {
        console.log(error);
      }
    );

    this.onDoctorSpecializationView();
    this.onGetSpecialization();

  }

  ngOnInit(): void {
    this.titleService.setTitle(this.title);
  }

  numberOnly(event: KeyboardEvent): boolean {
    const charCode = event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  onUserUpdate(){
    this.usr = {
      Id: this.user.Id,
      FirstName: this.firstName,
      LastName: this.lastName,
      Email: this.email,
      MobileNo: this.mobile,
      Gender: parseInt(this.gender, 10),
    }
    this.userService.UserUpdate(this.usr).subscribe(
      (response) => {
        if(response.Item1 == "User Detail Changed....!!!"){
          this.user = response.Item2;
          localStorage.setItem("user",JSON.stringify(this.user));
          Swal.fire("Doctor" , "Profile Details Updated !!", "success");
        }
        else{
          this.firstName = this.user.FirstName;
          this.lastName = this.user.LastName;
          this.email = this.user.Email;
          this.mobile = this.user.MobileNo;
          this.gender = this.user.Gender?.toString() || '1';
          Swal.fire("Doctor" , response.Item1, "error");
        }
      },
      (error: any) => {
        this.firstName = this.user.FirstName;
        this.lastName = this.user.LastName;
        this.email = this.user.Email;
        this.mobile = this.user.MobileNo;
        this.gender = this.user.Gender?.toString() || '1';
        Swal.fire("Doctor", "Internal Server Problem !!", "error");
      }
    );
    
    $('#Item').modal('hide');
  }

  onChangePassword(){
    this.userService.ChnagePassword(this.user.Id, this.password, this.rePassword).subscribe(
      (response) => {
        if(response == "Password change successfully...!!!"){
          Swal.fire("Doctor" , "Password Changed Successfully !!", "success");
        }
        else{
          Swal.fire("Doctor" , response, "error");
        }
      },
      (error: any) => {
        Swal.fire("Doctor", "Internal Server Problem !!", "error");
      }
    );
    this.password = "" ;
    this.rePassword = "";
    $('#Item2').modal('hide');
  }

  getState(event: any){
    this.homeService.GetStates(event.target.value).subscribe(
      (response) => {
        this.states = response;
        this.cities = [];
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  getCity(event?: any){
    this.homeService.GetCities(event.target.value).subscribe(
      (response) => {
        this.cities = response;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  getUserAddress(){
    this.userService.UserAddress(this.user.Id).subscribe(
      (response) => {
        if(response != ""){
          this.building = response[0].Building;
          this.street = response[0].Street;
          this.country = response[0].City.State.Country.Id;
          this.state = response[0].City.State.Id;
          this.city = response[0].City.Id;
          this.userAddressId = response[0].Id;
          var event = {
            'target' : {
              'value' : this.country, 
            }
          };
          this.getState(event);
          var e = {
            'target' : {
              'value' : this.state,
            },
          };
          this.getCity(e);
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  onSubmit(){
    this.userAddress = {
      UserId: this.user.Id,
      Building: this.building,
      Street: this.street,
      CityId: parseInt(this.city, 10),
    };

    if(this.userAddressId == 0){
      this.userService.AddUserAddress(this.userAddress).subscribe(
        (response) => {
          if(response == 'Inserted...!!!'){
            Swal.fire("Doctor" , "Address Detail Updated !!", "success");
          } else{
            Swal.fire("Doctor" , "Check Address Details !!", "error");
          }
        },
        (error: any) => {
          Swal.fire("Doctor", "Internal Server Problem !!", "error");
        }
      );
    }
    else{
      this.userAddress.Id = this.userAddressId;
      this.userService.EditUserAddress(this.userAddress).subscribe(
        (response) => {
          if(response == 'Updated...!!!'){
            Swal.fire("Doctor" , "Address Detail Updated !!", "success");
          } else{
            Swal.fire("Doctor" , "Check Address Details !!", "error");
          }
        },
        (error: any) => {
          Swal.fire("Doctor", "Internal Server Problem !!", "error");
        }
      );
    }

  }

  onChange(event: any){
    this.file = event.target.files[0];
  }

  onUpdateProfile(){
    console.log(`File : ${this.file}`);
    this.userService.UserProfileUpdate(this.file, this.user.Id).subscribe(
      (response) =>{
        if(response.Item1 == "Updated...!!!"){
          this.filename = "";
          this.userImage = this.imgUrl + "/Content/Images/"+ response.Item2;
          this.localItem = localStorage.getItem("user") || "";
          this.user = JSON.parse(this.localItem);
          this.user.userImage = this.userImage;
          localStorage.setItem("user",JSON.stringify(this.user));
        }
      },
      (error: any) => {
        Swal.fire("Doctor" , "Internal server error !!", "error");
      }
    );
    window.location.reload();
  }

  onDoctorSpecializationView(){
    this.userService.DoctorSpecialization(this.user.Id).subscribe(
      (response) =>{
        for(let res of response){
          this.userSpecialization = {
            Id: ++this.sr,
            specialization: res.Specialization,
          }
          this.userSpecializations.push(this.userSpecialization);
        }
      },
      (error: any) =>{
        Swal.fire("Doctor" , "Internal server error !!", "error");
      }
    );
  }

  onGetSpecialization(){
    this.homeService.GetSpecialization().subscribe(
      (response) =>{
        console.log(response);
        this.specialization = response;
      },
      (error: any) =>{
        Swal.fire("Doctor" , "Internal server error !!", "error");
      }
    );
  }

  onUpdateSpecialization(){

    this.userSpecialization = {
      DoctorId: this.user.Id,
      SpecializationId: parseInt(this.selectedSpecialization, 10),
    };

    this.userService.AddDoctorSpecialization(this.userSpecialization).subscribe(
      (response) =>{
        window.location.reload();
      },
      (error: any) =>{
        Swal.fire("Doctor" , "Internal server error !!", "error");
      }
    );
  }

}
