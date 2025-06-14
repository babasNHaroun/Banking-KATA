import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../../services/account.service';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-account-statement',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule
  ],
  templateUrl: './account-statement.component.html',
  styleUrls: ['./account-statement.component.css']
})
export class AccountStatementComponent {
  accountId = '';
  statement = '';
  error = '';

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {
    this.route.paramMap.subscribe(params => {
      this.accountId = params.get('accountId') || '';
      if (this.accountId) {
        this.loadStatement();
      }
    });
  }

  loadStatement() {
    this.accountService.getStatement(this.accountId).subscribe({
      next: (data) => this.statement = data,
      error: (err) => this.error = err.error || 'Could not load statement.'
    });
  }
}
