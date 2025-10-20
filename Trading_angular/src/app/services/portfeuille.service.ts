import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

interface Portefeuille {
  idPortefeuille: number;
  totalValue: number;
  liquidity: number;
  encryptedWalletId:string;
  user: { id: number, username: string, email: string };
}

@Injectable({
  providedIn: 'root'
})
export class PortfeuilleService {
  private baseUrl = 'http://localhost:8084/portfeuilles';

  constructor(private http: HttpClient) {}
  
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Something went wrong, please try again later.'));
  }
  private getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  private createHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  getAllPortefeuilles(): Observable<Portefeuille[]> {
    const headers = this.createHeaders();
    return this.http.get<Portefeuille[]>(this.baseUrl, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  getPortefeuilleByUserId(userId: number): Observable<Portefeuille> {
    const headers = this.createHeaders();
    return this.http.get<Portefeuille>(`${this.baseUrl}/${userId}`, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  createPortefeuille(portefeuille: Portefeuille): Observable<Portefeuille> {
    const headers = this.createHeaders();
    return this.http.post<Portefeuille>(this.baseUrl, portefeuille, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  updatePortefeuille(id: number, portefeuille: Portefeuille): Observable<Portefeuille> {
    const headers = this.createHeaders();
    return this.http.put<Portefeuille>(`${this.baseUrl}/${id}`, portefeuille, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  deletePortefeuille(id: number): Observable<any> {
    const headers = this.createHeaders();
    return this.http.delete(`${this.baseUrl}/${id}`, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }
  
  envoyerDollar(transactionRequest: { encryptedWalletId: string; amount: number; cryptoCurrency: string }): Observable<any> {
    const headers = this.createHeaders();
    return this.http.post<any>(`${this.baseUrl}/envoyer`, transactionRequest, {
      headers,
      responseType: 'json'
    })
    .pipe(
      catchError(this.handleError)
    );
  }
  retirerDollar(transactionRequest: { encryptedWalletId: string; amount: number; cryptoCurrency: string }): Observable<any> {
    const headers = this.createHeaders();
    return this.http.post<any>(`${this.baseUrl}/retrait`, transactionRequest, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }
  }

