import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interface/user';
import { UserService } from 'src/app/service/userService/user.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
})
export class LayoutComponent implements OnInit {

  user: User;
  
  localItem: string;

  constructor(private router: Router, private userService: UserService) {
    this.localItem = localStorage.getItem("user") || "";

    if(this.localItem == ""){
      this.router.navigate(['login']);
    } else{
      this.user = JSON.parse(this.localItem);
    }
   }

  ngOnInit(): void {
  }

  onClick(){
    localStorage.setItem("user", "");
    this.router.navigate(['/login']);
  }

  // onProfileImage(){
  //   this.userService.GetUserProfile(this.user.Id).subscribe(
  //     (response) => {
  //       console.log(response);
  //     },
  //     (error: any) =>{
  //       console.log(error);
  //     }
  //   );
  // }

}
