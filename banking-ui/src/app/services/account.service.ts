import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Account, Transaction } from '../models/account.model';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private apiUrl = 'http://localhost:8080/accounts';

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getAccount(id: string): Observable<Account> {
    return this.http.get<Account>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  createAccount(accountId: string): Observable<Account> {
    return this.http.post<Account>(this.apiUrl, { accountId }).pipe(
      catchError(this.handleError)
    );
  }

  deposit(accountId: string, amount: string): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}/${accountId}/deposit`, { amount }).pipe(
      catchError(this.handleError)
    );
  }

  withdraw(accountId: string, amount: string): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}/${accountId}/withdraw`, { amount }).pipe(
      catchError(this.handleError)
    );
  }

  getTransactions(accountId: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/${accountId}/transactions`).pipe(
      catchError(this.handleError)
    );
  }

  getStatement(accountId: string): Observable<string> {
    return this.http.get(`${this.apiUrl}/${accountId}/statement`, { responseType: 'text' as const }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    let errorMsg = 'An error occurred';
    if (error.error && error.error.message) {
      errorMsg = error.error;
    }
    return throwError(() => errorMsg);
  }
}