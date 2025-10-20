import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginImageTwoComponent } from './login/image-two/image-two.component';
import { ValidationComponent } from './login/validation/validation.component';
import { ToolTipComponent } from './login/tool-tip/tool-tip.component';
import { SweetalertComponent } from './login/sweetalert/sweetalert.component';
import { RegisterSimpleComponent } from './register/simple/simple.component';


const routes: Routes = [
  {
    path: 'login',
    children: [
      {
        path: 'image-two',
        component: LoginImageTwoComponent
      },
      {
        path: 'validation',
        component: ValidationComponent
      },
      {
        path: 'tooltip',
        component: ToolTipComponent
      },
      {
        path: 'sweetalert',
        component: SweetalertComponent
      }
    ]
  },
  {
    path: 'register',
    children: [
      {
        path: 'simple',
        component: RegisterSimpleComponent
      }
    ]
  },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthenticationRoutingModule { }
