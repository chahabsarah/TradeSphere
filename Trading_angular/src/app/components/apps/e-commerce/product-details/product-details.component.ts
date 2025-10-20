import { AfterViewInit, Component, OnInit } from '@angular/core';
import { PortfeuilleService } from '../../../../services/portfeuille.service';
import { AuthService } from '../../../../services/auth.service';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

interface Portefeuille {
  idPortefeuille: number;
  totalValue: number;
  liquidity: number;
  encryptedWalletId:string;
  user: { id: number, username: string, email: string };
}

 
@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})

export class ProductDetailsComponent implements OnInit {
[x: string]: any;
  userId: number;
  portefeuilles: any[] = [];
  currentUser: any;
  userPortefeuille: any;
  portefeuilleId : number;
  portefeuille: Portefeuille | null = null;
  errorMessage: string | null = null;


  constructor(
    private portfeuilleService: PortfeuilleService,
    private authService: AuthService,
    private dialog: MatDialog,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe((user) => {
      this.currentUser = user;
      this.userId = user.id;
      console.log('userId',this.userId)
      console.log('Portfolio:', this.currentUser.portefeuille.idPortefeuille);
      localStorage.setItem('userId', this.userId.toString());
      const currentEmail=this.currentUser.email
      const currentUserName=this.currentUser.username

      this.loadPortefeuille(this.userId);

    });
  }
  loadPortefeuille(userId: number): void {
    this.portfeuilleService.getPortefeuilleByUserId(userId).subscribe(
      (data) => {
        this.portefeuille = data;
        console.log('User Portefeuille:', this.portefeuille);
      },
      (error) => {
        this.errorMessage = error.message;
        console.error('Error loading portefeuille:', error);
      }
    );
  }

  deletePortefeuille(id: number) {
    if (confirm('Are you sure you want to delete this portefeuille?')) {
      this.portfeuilleService.deletePortefeuille(id).subscribe(() => {
        this.loadPortefeuilles();
      });
    }
  }
  createPortefeuille(newPortefeuille: Partial<Portefeuille>) {
    const userId = Number(localStorage.getItem('userId'));

    if (userId) {
      newPortefeuille.user = { id: userId, username: this.currentUserName, email: this.currentEmail }; // Vous pouvez ajouter les détails de l'utilisateur si nécessaire

      this.portfeuilleService.createPortefeuille(newPortefeuille as Portefeuille).subscribe(
        (data) => {
          console.log('Portefeuille créé avec succès :', data);
          this.loadPortefeuilles();
        },
        (error) => {
          console.error('Erreur lors de la création du portefeuille :', error);
        }
      );
    } else {
      console.error('User ID is missing');
    }
  }

  updatePortefeuille(id: number, updatedPortefeuille: Partial<Portefeuille>) {
    this.portfeuilleService.updatePortefeuille(this.currentUser.portefeuille.idPortefeuille, updatedPortefeuille as Portefeuille).subscribe(
      (data) => {
        console.log('Portefeuille mis à jour avec succès :', data);
        this.loadPortefeuilles();
      },
      (error) => {
        console.error('Erreur lors de la mise à jour du portefeuille :', error);
      }
    );
  }

}
