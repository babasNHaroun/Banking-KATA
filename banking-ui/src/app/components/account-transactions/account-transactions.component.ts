import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-account-transactions',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatTableModule,
    MatIconModule
  ],
  templateUrl: './account-transactions.component.html',
  styleUrls: ['./account-transactions.component.css']
})
export class AccountTransactionsComponent {
  accountId = '';
  transactions: any[] = [];
  displayedColumns: string[] = ['date', 'amount', 'balanceAfter'];

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {
    this.route.paramMap.subscribe(params => {
      this.accountId = params.get('accountId') || '';
      if (this.accountId) {
        this.loadTransactions();
      }
    });
  }

  loadTransactions() {
    this.accountService.getTransactions(this.accountId).subscribe({
      next: (data) => this.transactions = data,
      error: () => this.transactions = []
    });
  }
}
