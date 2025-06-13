import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-account-deposit',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule
  ],
  templateUrl: './account-deposit.component.html',
  styleUrls: ['./account-deposit.component.css']
})
export class AccountDepositComponent {
  amount = '';
  message = '';
  error = '';
  accountId = '';

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {
    this.route.paramMap.subscribe(params => {
      this.accountId = params.get('accountId') || '';
    });
  }

  onSubmit() {
    this.message = '';
    this.error = '';
    this.accountService.deposit(this.accountId, this.amount).subscribe({
      next: () => {
        this.message = 'Deposit successful!';
        this.amount = '';
      },
      error: (err) => {
        this.error = err.error || 'Deposit failed.';
      }
    });
  }
}
