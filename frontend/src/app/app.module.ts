import {DropdownModule} from "ngx-dropdown";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule, JsonpModule} from '@angular/http';
import {NgModule} from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {AppComponent} from './app.component';
import {routing} from './app.routing';

import {HomeAdminComponent} from './component/admin/home-admin.component';
import {FooterComponent} from './component/footer/footer.component';
import {FooterAdminComponent} from './component/footer/footer-admin.component';
import {HeaderComponent} from './component/header/header.component';
import {HeaderAdminComponent} from './component/header/header-admin.component';
import {AboutComponent} from './component/home/about.component';
import {ContactComponent} from './component/home/contact.component';
import {HomeComponent} from './component/home/home.component';
import {SearchComponent} from './component/home/search/search.component';
import {LoginComponent} from './component/login/login.component';
import {ModalLogin} from "./component/login/modal.component/modal-login";
import {RegisterComponent} from './component/login/register.component';
import {ProfileComponent} from './component/user/profile.component';

import {ActionService} from './service/action.service';
import {FineService} from './service/fine.service';
import {GenreService} from './service/genre.service';
import {UserService} from './service/user.service';
import {ResourceService} from './service/resource.service';
import {SessionService} from './service/session.service';

@NgModule({
  declarations: [
    AboutComponent,
    AppComponent,
    ContactComponent,
    FooterComponent,
    FooterAdminComponent,
    HeaderComponent,
    HeaderAdminComponent,
    HomeComponent,
    HomeAdminComponent,
    LoginComponent,
    ModalLogin,
    ProfileComponent,
    RegisterComponent,
    SearchComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    DropdownModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    NgbModule.forRoot(),
    ReactiveFormsModule,
    routing
  ],
  providers: [
    ActionService,
    FineService,
    GenreService,
    UserService,
    ResourceService,
    SessionService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
