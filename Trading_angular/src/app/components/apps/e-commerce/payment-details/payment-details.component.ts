import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';
import { PortfeuilleService } from '../../../../services/portfeuille.service';

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.scss']
})
export class PaymentDetailsComponent implements OnInit {

 transactionRequest = {
    encryptedWalletId: '',
    amount: 0,
    cryptoCurrency: ''
  }
  transactionResponse: string | null = null;

  constructor(
    private portfeuilleService: PortfeuilleService,
    private authService: AuthService,
    private dialog: MatDialog,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  envoyerDollar(): void {
    this.portfeuilleService.envoyerDollar(this.transactionRequest).subscribe(
      response => {
        this.transactionResponse = response;
        console.log('Transaction Request:', this.transactionRequest);
      },
      error => {
        console.error('Transaction failed:', error);
        this.transactionResponse = 'Une erreur est survenue lors de la transaction.';
      }
    );
  }
}
