import {Component, ViewChild} from '@angular/core';

import {User} from './model/user.model';

import {ModalLogin} from "./component/login/modal.component/modal-login";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor() {
    localStorage.clear();
  }

  @ViewChild(ModalLogin)
  private modalLogin: ModalLogin;

  openLogin() {
    this.modalLogin.open();
  }
}
