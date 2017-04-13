import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import {FINE_URL} from "../util";

@Injectable()
export class FineService {

  authCreds: string;

  constructor(private http: Http) {
  }

  setAuthHeaders(authCreds: string) {
    this.authCreds = authCreds;
  }

  getAllFines(page: number) {
    console.log(this.authCreds);
    let headers: Headers = new Headers();
    headers.append('Authorization', 'Basic ' + this.authCreds);
    return this.http.get(FINE_URL + '?page=' + page, {headers: headers})
      .map(response => response.json().content)
      .catch(error => Observable.throw('Server error'));
  }
}
