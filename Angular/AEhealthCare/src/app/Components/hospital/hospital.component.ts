import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { UserService } from 'src/app/service/userService/user.service';
import { User } from 'src/app/interface/user';

@Component({
  selector: 'app-hospital',
  templateUrl: './hospital.component.html',
})
export class HospitalComponent implements OnInit {

  users: User[];

  title: string = 'hospital';

  constructor(private titleService:Title, private userService: UserService) { 
    this.getHospitals();
  }

  ngOnInit(): void {
    this.titleService.setTitle(this.title);
  }

  getHospitals(){
    this.userService.GetHospitalsList().subscribe(
      (response) => {
        console.log(response);
        this.users = response;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
}
