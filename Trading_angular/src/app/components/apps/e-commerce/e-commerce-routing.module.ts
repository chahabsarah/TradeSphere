import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PaymentDetailsComponent } from "./payment-details/payment-details.component";
import { ProductDetailsComponent } from "./product-details/product-details.component";
import { ProductListComponent } from "./product-list/product-list.component";

const routes: Routes = [
  {
    path: "",
    children: [
     
      {
        path: "product-details",
        component: ProductDetailsComponent,
      },
      {
        path: "payment/detail",
        component: PaymentDetailsComponent,
      },
      {
        path: "product/list",
        component: ProductListComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ECommerceRoutingModule {}
