import {RouterModule} from '@angular/router';

import {HomeAdminComponent} from './component/admin/home-admin.component';

import {AboutComponent} from './component/home/about.component';
import {ContactComponent} from './component/home/contact.component';
import {HomeComponent} from './component/home/home.component';
import {SearchComponent} from './component/home/search/search.component';

import {LoginComponent} from './component/login/login.component';
import {RegisterComponent} from './component/login/register.component';

import {ProfileComponent} from './component/user/profile.component';

const appRoutes = [
  {path: '', component: HomeComponent},
  {path: 'admin', component: HomeAdminComponent},
  {path: 'about', component: AboutComponent},
  {path: 'contact', component: ContactComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'search', component: SearchComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent}
];

export const routing = RouterModule.forRoot(appRoutes);
