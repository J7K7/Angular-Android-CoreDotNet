import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { User } from 'src/app/interface/user';
import { UserService } from 'src/app/service/userService/user.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {

  user: User;

  title: string = 'Register';
  firstName: string;
  lastName: string;
  email: string;
  mobile: string;
  userName: string;
  password: string;
  gender: string = '1';
  localItem: string;

  constructor(private titleService:Title, private userService: UserService, private router: Router) { 
    this.localItem = localStorage.getItem("user") || "";
    if(this.localItem != ""){
      this.router.navigate(['']);
    }
  }

  ngOnInit(): void {
    this.titleService.setTitle(this.title);
  }

  onSubmit(){
    this.user = {
      FirstName: this.firstName,
      LastName: this.lastName,
      Email: this.email,
      MobileNo: this.mobile,
      UserName: this.userName,
      Password: this.password,
      Gender: parseInt(this.gender, 10),
    }
    console.log(`User = ${this.user}`);
    this.userService.RegisterUser(this.user).subscribe(
      (response) => {
        console.log(response);
        if(response == 'Register Successfully...!!!'){
          this.router.navigate(['/login']);
          this.firstName = "";
          this.lastName = "";
          this.email = "";
          this.mobile = "";
          this.userName = "";
          this.password = "";
          this.gender = "1";
        }
        else{
          Swal.fire("Registration Details !!" ,response, "error");
        }
      },
      (error: any) => {
        Swal.fire("Registration Problem !!", "Internal Server Problem !!", "error");
      }
    );
  }

  numberOnly(event: KeyboardEvent): boolean {
    const charCode = event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

}
