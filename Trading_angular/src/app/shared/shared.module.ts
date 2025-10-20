import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
// import { DragulaModule } from "ng2-dragula";
import { TranslateModule } from "@ngx-translate/core";
// Components
import { CustomizerComponent } from "./components/customizer/customizer.component";
import { FeatherIconsComponent } from "./components/feather-icons/feather-icons.component";
import { FooterComponent } from "./components/footer/footer.component";
import { ContentComponent } from "./components/layout/content/content.component";
import { FullComponent } from "./components/layout/full/full.component";
import { LoaderComponent } from "./components/loader/loader.component";
import { SidebarComponent } from "./components/sidebar/sidebar.component";
import { TapToTopComponent } from "./components/tap-to-top/tap-to-top.component";
import { LayoutService } from "./services/layout.service";
import { NavService } from "./services/nav.service";
import { DecimalPipe } from "@angular/common";
import { SvgIconComponent } from "./components/svg-icon/svg-icon.component";
import { CarouselModule } from "ngx-owl-carousel-o";
import { SwiperModule } from "swiper/angular";
import { MatDialogModule } from "@angular/material/dialog";

@NgModule({
  declarations: [
    FooterComponent,
    SidebarComponent,
    ContentComponent,
    CustomizerComponent,
    FeatherIconsComponent,
    FullComponent,
    LoaderComponent,
    TapToTopComponent,
  
    SvgIconComponent,
  ],
  imports: [
    CommonModule, 
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
      MatDialogModule,
    // DragulaModule.forRoot(),
    TranslateModule.forRoot(),
    CarouselModule,
    SwiperModule],
  providers: [NavService, LayoutService, DecimalPipe],
  exports: [NgbModule, FormsModule, ReactiveFormsModule,
    TranslateModule,
    LoaderComponent, FeatherIconsComponent, TapToTopComponent, SvgIconComponent, SwiperModule],
})
export class SharedModule {}
