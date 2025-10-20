import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8084/auth';
  private userUrl = 'http://localhost:8084/user';
  private getToken(): string | null {
    return localStorage.getItem('jwt');
  }
  constructor(private http: HttpClient) {}

  signup(signupRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, signupRequest);
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, loginRequest);
  }

  verifyOtp(otpRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/verifyotp`, otpRequest);
  }

  getAllUsers(): Observable<any> {
    const url = `${this.userUrl}/all`;
    const token = this.getToken();
    const headers = token ? new HttpHeaders({ 
      'Content-Type': 'application/json', 
      'Authorization': `Bearer ${token}`
    }) : new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.get(url, { headers });
  }
  

  getUserById(id: number): Observable<any> {
    const url = `${this.userUrl}/${id}`;
    const token = this.getToken();
    const headers = token ? new HttpHeaders({ 
      'Content-Type': 'application/json', 
      'Authorization': `Bearer ${token}`
    }) : new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.get(url, { headers });
  }

  updateProfile(updateRequest: any): Observable<any> {
    const url = `${this.userUrl}/profile`;
    const token = this.getToken();
      const headers = {
      Authorization: `Bearer ${token}`
    };

    return this.http.put(url, updateRequest, { headers });
  }

  changePassword(changePasswordRequest: any): Observable<any> {
    const url = `${this.userUrl}/change-password`;
    const token = this.getToken();
    const headers = {
    Authorization: `Bearer ${token}`
  };

    return this.http.post(url, changePasswordRequest, { headers });
  }


  validateAccount(validateRequest: any): Observable<any> {
    const url = `${this.userUrl}/validate`;
    const token = this.getToken();
      const headers = {
      Authorization: `Bearer ${token}`
    };
    return this.http.post(url, validateRequest, { headers });
  }
  getCurrentUser(): Observable<any> {
    const url = `${this.userUrl}/current`;
    const token = this.getToken();
      const headers = {
      Authorization: `Bearer ${token}`
    };
    return this.http.get(url, { headers });
  }
  
  deleteUser(userId: number): Observable<any> {
    const url = `${this.userUrl}/${userId}`;
    const token = this.getToken();
      const headers = {
      Authorization: `Bearer ${token}`
    };
    return this.http.delete(url, { headers });
  }
}
