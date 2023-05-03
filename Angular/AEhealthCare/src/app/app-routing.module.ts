import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './Components/login/login.component';
import { LayoutComponent } from './layout/layout.component';
import { RegisterComponent } from './Components/register/register.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { ProfileComponent } from './Components/profile/profile.component';
import { HospitalComponent } from './Components/hospital/hospital.component';

const routes: Routes = [
  { 
    path: '', 
    component: LayoutComponent,
    children: [{
      path: '',
      component: DashboardComponent,
      outlet: "r2"
      }
    ]
  },
  { 
    path: 'dashboard', 
    component: LayoutComponent,
    children: [{
      path: '',
      component: DashboardComponent,
      outlet: "r2"
      }
    ]
  },
  { 
    path: 'hospital', 
    component: LayoutComponent,
    children: [{
      path: '',
      component: HospitalComponent,
      outlet: "r2"
      }
    ]
  },
  {
    path: 'profile', 
    component: LayoutComponent,
     children:[
      {
        path: '',
        component: ProfileComponent,
        outlet: "r2",
      }
     ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
