import { Routes } from "@angular/router";

export const content: Routes = [
  {
    path: "ecommerce",
    loadChildren: () => import("../../components/apps/e-commerce/e-commerce.module").then((m) => m.ECommerceModule),
  },
 
]