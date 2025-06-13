import { Routes } from '@angular/router';
import { AccountCreateComponent } from './account/account-create/account-create.component';
import { AccountDepositComponent } from './account/account-deposit/account-deposit.component';
import { AccountWithdrawComponent } from './account/account-withdraw/account-withdraw.component';
import { AccountTransactionsComponent } from './account/account-transactions/account-transactions.component';
import { AccountStatementComponent } from './account/account-statement/account-statement.component';

export const routes: Routes = [
  { path: '', redirectTo: 'accounts/create', pathMatch: 'full' },
  { path: 'accounts/create', component: AccountCreateComponent },
  { path: 'accounts/:accountId/deposit', component: AccountDepositComponent },
  { path: 'accounts/:accountId/withdraw', component: AccountWithdrawComponent },
  { path: 'accounts/:accountId/transactions', component: AccountTransactionsComponent },
  { path: 'accounts/:accountId/statement', component: AccountStatementComponent },

];
