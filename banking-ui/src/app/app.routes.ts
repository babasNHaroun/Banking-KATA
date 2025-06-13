import { Routes } from '@angular/router';
import { AccountCreateComponent } from './accounts/account-create/account-create.component';
import { AccountDetailComponent } from './accounts/account-detail/account-detail.component';
import { AccountDepositComponent } from './accounts/account-deposit/account-deposit.component';
import { AccountWithdrawComponent } from './accounts/account-withdraw/account-withdraw.component';
import { AccountTransactionsComponent } from './accounts/account-transactions/account-transactions.component';
import { AccountStatementComponent } from './accounts/account-statement/account-statement.component';

export const routes: Routes = [
  { path: '', redirectTo: 'accounts/create', pathMatch: 'full' },
  { path: 'accounts/create', component: AccountCreateComponent },
  { path: 'accounts/:accountId', component: AccountDetailComponent },
  { path: 'accounts/:accountId/deposit', component: AccountDepositComponent },
  { path: 'accounts/:accountId/withdraw', component: AccountWithdrawComponent },
  { path: 'accounts/:accountId/transactions', component: AccountTransactionsComponent },
  { path: 'accounts/:accountId/statement', component: AccountStatementComponent },

];
