import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GalleryModule } from '@ks89/angular-modal-gallery';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { NgxPrintModule } from 'ngx-print';
import { SharedModule } from "../../../shared/shared.module";
import { ECommerceRoutingModule } from './e-commerce-routing.module';
import { PaymentDetailsComponent } from './payment-details/payment-details.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import 'hammerjs';
import 'mousetrap';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { ProductListComponent } from './product-list/product-list.component';

@NgModule({
    declarations: [
        ProductDetailsComponent,
        PaymentDetailsComponent,
        ProductListComponent
    ],
    imports: [
        ECommerceRoutingModule,
        SharedModule,
        CommonModule,
        CarouselModule,
        NgbModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        NgxPrintModule,
        GalleryModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        BrowserAnimationsModule,
    ],
    providers: [NgbActiveModal]
})
export class ECommerceModule { }
