import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import {BASE_URL} from "../util";

import {User} from '../model/user.model';

import {ActionService} from '../service/action.service';
import {FineService} from '../service/fine.service';
import {UserService} from '../service/user.service';

@Injectable()
export class SessionService {

  user: User;
  authCreds: string;
  isLogged = false;
  isAdmin = false;

  constructor(private http: Http, private userService: UserService, private actionService: ActionService,
              private fineService: FineService) {
  }

  logIn(username: string, password: string) {
    this.authCreds = btoa(username + ':' + password);
    let headers: Headers = new Headers();
    headers.append('Authorization', 'Basic ' + this.authCreds);
    return this.http.get(BASE_URL + 'logIn', {headers: headers})
      .map(response => {
          let id = response.json().id;
          this.userService.setAuthHeaders(this.authCreds);
          this.userService.getUser(id).subscribe(
            user => {
              this.user = user;
              if (this.user.roles.includes('ROLE_ADMIN', 0))
                this.isAdmin = true;
            },
            error => console.log(error)
          );
          localStorage.setItem("user", username);
          this.actionService.setAuthHeaders(this.authCreds);
          this.fineService.setAuthHeaders(this.authCreds);
          this.isLogged = true;
          return this.user;
      })
      .catch(error => Observable.throw('Server error'));
  }

  logOut() {
    let headers: Headers = new Headers();
    headers.append('Authorization', 'Basic ' + this.authCreds);
    return this.http.get(BASE_URL + 'logOut', {headers: headers})
      .map(response => {
        localStorage.clear();
        this.isLogged = false;
        this.user = null;
        return true;
      })
      .catch(error => Observable.throw('Server error'));
  }

  checkCredentials() {
    return (localStorage.getItem("user") !== null);
  }

  register(firstName: string, lastName1: string, lastName2: string, username: string, password: string, dni: string, email: string, phone: string){
      let newUser: User;
      newUser = {name: username, passwordHash: password, dni: dni, firstName: firstName, lastName1: lastName1, lastName2: lastName2, email: email, telephone: phone};
      return this.http.post(BASE_URL + 'register', newUser);
  }
}
