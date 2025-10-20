import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-simple',
  templateUrl: './simple.component.html',
  styleUrls: ['./simple.component.scss']
})
export class RegisterSimpleComponent implements OnInit {

  public show: boolean = false;


  signupData = {
    email: '',
    password: '',
    username: '',
    address: '',
    phoneNumber: '',
    cin: '',
    datedenaissance: '',
    role: null,
  };

  ngOnInit() {
  }

  showPassword() {
    this.show = !this.show;
  }
  constructor(private authService: AuthService, private router: Router) {}

  onSignup() {
    this.authService.signup(this.signupData).subscribe({
      next: (response) => {
        console.log('Inscription réussie', response);
        this.router.navigate(['/authentication/login/image-two']);

      },
      error: (err) => {
        console.error('Erreur lors de l\'inscription', err);
        alert('Erreur lors de l\'inscription.');
      },
    });
  }
}
