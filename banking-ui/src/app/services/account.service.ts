import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private apiUrl = 'http://localhost:8080/accounts';

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getAccount(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createAccount(accountId: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { accountId });
  }

  deposit(accountId: string, amount: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${accountId}/deposit`, { amount });
  }

  withdraw(accountId: string, amount: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${accountId}/withdraw`, { amount });
  }

  getTransactions(accountId: string) {
    return this.http.get<any[]>(`http://localhost:8080/accounts/${accountId}/transactions`);
  }

  getStatement(accountId: string) {
    return this.http.get(`http://localhost:8080/accounts/${accountId}/statement`, { responseType: 'text' });
  }
}