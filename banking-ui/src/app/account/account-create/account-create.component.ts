import { Component } from '@angular/core';
import { AccountService } from '../../services/account.service';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common'; // <-- Add this import

@Component({
  selector: 'app-account-create',
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
  templateUrl: './account-create.component.html',
  styleUrls: ['./account-create.component.css']
})
export class AccountCreateComponent {
  accountId = 'account-123'; // Default account ID for testing and demonstration
  message = '';
  error = '';

  constructor(private accountService: AccountService) {}

  onSubmit() {
    this.message = '';
    this.error = '';
    this.accountService.createAccount(this.accountId).subscribe({
      next: (res) => {
        this.message = 'Account created successfully!';
        this.accountId = '';
      },
      error: (err) => {
        this.error = err.error || 'Failed to create account.';
      }
    });
  }
}
