import {Component, OnInit} from '@angular/core';
import {STATUS_NO_CONTENT, BOOKS_IMG_URL} from "../../util";

import {Resource} from '../../model/resource.model';

import {ResourceService} from '../../service/resource.service';
import {SessionService} from '../../service/session.service';

@Component({
  templateUrl: 'home.component.html',
  styles: [`
    .showBtnMoreBook {
      display: block
    }

    .showBtnMoreMagaz {
      display: block
    }`]
})

export class HomeComponent implements OnInit {

  books: Resource[];
  booksPage: number;
  img_url: string;
  isLogged: boolean;
  magazines: Resource[];
  magazinesPage: number;
  moreBooksActive: boolean;
  moreMagazActive: boolean;


  constructor(private resourceService: ResourceService, private sessionService: SessionService) {
    this.books = [];
    this.booksPage = 0;
    this.img_url = BOOKS_IMG_URL;
    this.isLogged = false;
    this.magazines = [];
    this.magazinesPage = 0;
    this.moreBooksActive = true;
    this.moreMagazActive = true;
    this.addBooks(true);
    this.addMagazines(true);
  }

  ngOnInit() {
    this.isLogged = this.sessionService.checkCredentials();
  }

  addBooks(userReq: boolean) {
    this.resourceService.getAllResources('Libro', this.booksPage).subscribe(
      books => {
        if (userReq) {
          this.booksPage++;
          this.books = this.books.concat(books);
          this.addBooks(false);
        }
      },
      error => {
        if (error.statusCode == STATUS_NO_CONTENT) {
          console.log(error + " - STATUS CODE: " + error.statusCode);
        }
        else {
          if (userReq)
            console.error("ERROR: " + error);
        }
        this.moreBooksActive = false;
      }
    );

  }

  addMagazines(userReq: boolean) {
    this.resourceService.getAllResources('Revista', this.magazinesPage).subscribe(
      magazines => {
        if (userReq) {
          this.magazinesPage++;
          this.magazines = this.magazines.concat(magazines);
          this.addMagazines(false);
        }
      },
      error => {
        if (error.statusCode == STATUS_NO_CONTENT) {
          console.log(error + " - STATUS CODE: " + error.statusCode);
        }
        else {
          if (userReq)
            console.error("ERROR: " + error);
        }
        this.moreMagazActive = false;
      }
    );
  }

  reserveResource(id: number) {
  }
}
