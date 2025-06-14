import { Routes } from '@angular/router';
import { AccountCreateComponent } from './components/account-create/account-create.component';
import { AccountDepositComponent } from './components/account-deposit/account-deposit.component';
import { AccountWithdrawComponent } from './components/account-withdraw/account-withdraw.component';
import { AccountTransactionsComponent } from './components/account-transactions/account-transactions.component';
import { AccountStatementComponent } from './components/account-statement/account-statement.component';

export const routes: Routes = [
  { path: '', redirectTo: 'accounts/create', pathMatch: 'full' },
  { path: 'accounts/create', component: AccountCreateComponent },
  { path: 'accounts/:accountId/deposit', component: AccountDepositComponent },
  { path: 'accounts/:accountId/withdraw', component: AccountWithdrawComponent },
  { path: 'accounts/:accountId/transactions', component: AccountTransactionsComponent },
  { path: 'accounts/:accountId/statement', component: AccountStatementComponent },

];
