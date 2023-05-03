import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { DatePipe } from '@angular/common';
import { UserService } from 'src/app/service/userService/user.service';
import { HomeService } from 'src/app/service/homeService/home.service';
import { User } from 'src/app/interface/user';
import { UserSchedule } from 'src/app/interface/user-schedule';
import Swal from 'sweetalert2';
declare var $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {

  users: User[];
  userSchedules : UserSchedule[];

  user: User;
  userSchedule : UserSchedule;

  title: string = 'dashboard';
  maxDate: string;
  minDate: string;
  selectedDate: string;
  localItem: string;
  date:string;
  hospitalId: string;
  morningStartTime?: string;
  morningEndTime?: string;
  eveningStartTime?: string;
  eveningEndTime?: string;

  constructor(private titleService:Title, private userService: UserService, private homeService: HomeService) {
    
    this.localItem = localStorage.getItem("user") || "";
    
    this.user = JSON.parse(this.localItem);
    
    let date: Date = new Date();
    
    date.setDate(date.getDate() + 1);
    let datePipe: DatePipe = new DatePipe('en-US');
    
    this.minDate = datePipe.transform(date, 'YYYY-MM-dd') || "";
    
    date.setDate(date.getDate() + 1);
    datePipe = new DatePipe('en-US');
    
    this.maxDate = datePipe.transform(date, 'YYYY-MM-dd') || "";
    
    // this.onGetHospitalByDoctor();
    
    this.getHospitals();

    this.getSchedule();
  }

  ngOnInit(): void {
    this.titleService.setTitle(this.title);
  }

  getHospitals(){
    this.userService.GetHospitals(this.user.Id).subscribe(
      (response) => {
        console.log(response);
        this.users = response;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  onMorningSubmit(){
    this.userSchedule = {
      HospitalDoctorId : parseInt(this.hospitalId, 10),
      ScheduleDate : new Date(Number(Date.parse(this.selectedDate))),
      MorningStart : this.morningStartTime,
      MorningEnd : this.morningEndTime
    }

    this.userService.AddDoctorSchedule(this.userSchedule).subscribe(
      (response) => {
        console.log(response);
        if(response == "Inserted...!!!"){
          Swal.fire("Doctor" , "Schedule Updated Successfully !!", "success");
        }
        else{
          Swal.fire("Doctor" , response, "error");
        }
      },
      (error: any) => {
        Swal.fire("Doctor" , "Internal Server Error", "error");
      }
    );
    $('#Item1').modal('hide');
    this.selectedDate = "";
    this.morningStartTime = "";
    this.morningEndTime = "";
    this.getSchedule();
  }

  onEveningSubmit(){
    this.userSchedule = {
      HospitalDoctorId : parseInt(this.hospitalId, 10),
      ScheduleDate : new Date(Number(Date.parse(this.selectedDate))),
      EveningStart : this.eveningStartTime,
      EveningEnd : this.eveningEndTime
    }

    this.userService.AddDoctorSchedule(this.userSchedule).subscribe(
      (response) => {
        console.log(response);
        if(response == "Inserted...!!!"){
          Swal.fire("Doctor" , "Schedule Updated Successfully !!", "success");
        }
        else{
          Swal.fire("Doctor" , response, "error");
        }
      },
      (error: any) => {
        Swal.fire("Doctor" , "Internal Server Error", "error");
      }
    );
    $('#Item2').modal('hide');
    this.selectedDate = "";
    this.morningStartTime = "";
    this.morningEndTime = "";
    this.getSchedule();
  }

  getSchedule(){
    this.userService.getDoctorSchedule().subscribe(
      (response) => {
        console.log(response);
        this.userSchedules = response;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

}
