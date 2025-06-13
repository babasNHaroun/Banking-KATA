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
  selector: 'app-account-withdraw',
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
  templateUrl: './account-withdraw.component.html',
  styleUrls: ['./account-withdraw.component.css']
})
export class AccountWithdrawComponent {
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
    this.accountService.withdraw(this.accountId, this.amount).subscribe({
      next: () => {
        this.message = 'Withdrawal successful!';
        this.amount = '';
      },
      error: (err) => {
        this.error = err.error || 'Withdrawal failed.';
      }
    });
  }
}
