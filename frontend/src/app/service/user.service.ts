import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import {USER_URL} from "../util";

import {User} from '../model/user.model';

@Injectable()
export class UserService {

  user: User;
  authCreds: string;

  constructor(private http: Http) {
  }

  setAuthHeaders(authCreds: string) {
    this.authCreds = authCreds;
  }

  getUserCompleted() {
    return this.user;
  }

  getAllUsers() {
    return this.http.get(USER_URL + 'all')
      .map(response => response.json().content)
      .catch(error => Observable.throw('Server error'));
  }

  getUser(id: number) {
    console.log(this.authCreds);
    let headers: Headers = new Headers();
    headers.append('Authorization', 'Basic ' + this.authCreds);
    return this.http.get(USER_URL + '/' + id.toString(), {headers: headers})
      .map(response => {
        this.user = response.json();
        return this.user;
      })
      .catch(error => Observable.throw('Server error'));
  }
}
