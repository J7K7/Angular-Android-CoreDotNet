import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { User } from 'src/app/interface/user';
import { UserService } from 'src/app/service/userService/user.service';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  user: User;

  title: string = 'Login';
  private imgUrl = environment.imgUrl;
  username: string;
  password: string;
  localItem: string;



  constructor(private titleService:Title, private userService: UserService, private router: Router){
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
      UserName: this.username,
      Password: this.password,
    }

    this.userService.LoginUser(this.user).subscribe(
      (response) => {
        console.log(response);
        if(response.Item1 == 'Doctor'){
          console.log(response);
          this.user = response.Item2;
          console.log("Images : " + response.Item2.UserImages);
          if(response.Item2.UserImages.length <= 0){
            this.user.userImage = this.imgUrl + '/Content/Images/healthcare.png';
          }
          else{
            this.user.userImage = this.imgUrl + '/Content/Images/' + response.Item2.UserImages[0].Name;
          }
          localStorage.setItem("user",JSON.stringify(this.user));
          this.router.navigate(['']);
          this.username = "";
          this.password= "";
          console.log(response.Item2);
          console.log(this.user.userImage);
        }
        else{
          Swal.fire("Login Details !!" ,"Enter Valid Details", "error");
        }
      },
      (error: any) => {
        Swal.fire("Login Problem !!", "Internal Server Problem !!", "error");
      }
    );
  }
}
