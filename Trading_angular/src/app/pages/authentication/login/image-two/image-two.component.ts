import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-image-two',
  templateUrl: './image-two.component.html',
  styleUrls: ['./image-two.component.scss']
})
export class LoginImageTwoComponent implements OnInit {
  loginRequest = {
    email: '',
    password: '',
  };

  otpRequest = {
    email: '',
    otp: '',
    password: '',
  };
  public show: boolean = false;

  constructor(
    private authService: AuthService, private router: Router){}

  ngOnInit() {
  }

  showPassword() {
    this.show = !this.show;
  }
  login(): void {
    this.authService.login(this.loginRequest).subscribe(
      (response) => {
        console.log('OTP sent:', response);
        this.router.navigate(['/authentication/unlock-user']);

      },
      (error) => {
        console.error('Login error:', error);
      }
    );
  }

  // Verify OTP Method
  verifyOtp(): void {
    this.authService.verifyOtp(this.otpRequest).subscribe(
      (response) => {
        console.log('JWT received:', response);
      },
      (error) => {
        console.error('OTP verification error:', error);
      }
    );
  }

}
