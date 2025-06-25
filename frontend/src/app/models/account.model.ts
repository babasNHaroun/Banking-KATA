export interface Account {
  accountId: string;
  balance: string;
}

export interface Transaction {
  date: string;
  amount: string;
  balanceAfter: string;
}