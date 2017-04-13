import { ResourceService } from './../../../service/resource.service';
import { Observable } from 'rxjs/Observable';
import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {BOOKS_IMG_URL} from '../../../util';

import {Genre} from '../../../model/genre.model';
import {Resource} from '../../../model/resource.model';

import {DoCheck } from '@angular/core';

import {GenreService} from '../../../service/genre.service';
import {SessionService} from '../../../service/session.service';

@Component({
  templateUrl: 'search.component.html'
})

export class SearchComponent implements OnInit, DoCheck{

  genres: Genre[];
  genresPage: number;
  search: string;
  search2: string;
  showMore: string;
  resourcesPage: number;
  img_url: string;
  isLogged: boolean;
  resources: Resource[];
  visible: boolean;
  activeSearch: boolean;

  constructor(private router: Router, private route: ActivatedRoute, private genreService: GenreService, private resourceService: ResourceService, private sessionService: SessionService) {
    this.genres = [];
    this.genresPage = 0;
    this.resourcesPage = 0;
    this.img_url  = BOOKS_IMG_URL;
    this.isLogged = false;
    this.resources = [];
    this.visible = true;
    this.showMore = 'No hay resultados para ';
    this.activeSearch = true;
  }

  ngOnInit() {
    this.genreService.getAllGenres(this.genresPage).subscribe(
      genres => this.genres = genres,
      error => console.log(error)
    );
    this.isLogged = this.sessionService.checkCredentials();
    this.route.queryParams.subscribe(
      params => {
        this.searchResourcesByName(params['name']);
        this.search = (params['name']);
        if (params['name']!=null) this.showMore = 'Ver más resultados para ';
      },
      error => console.log(error)
    )
    this.search2 = this.search;
  }

  ngDoCheck(){
    if(this.search != this.search2){
      this.search2 = this.search;
      this.searchResourcesByName(this.search);
    }
  }

  searchResourcesByGenre(id: number) {
    this.activeSearch = false;
    this.showMore = 'Ver más resultados';
    this.search = '';
    this.search2 = '';
    this.visible = true;
    this.genreService.getGenre(id).subscribe(
      genre => this.resources = genre,
      error => console.log(error)
    );
  }

  searchResourcesByName(name: string){
    this.activeSearch = true;
    this.visible = true;
    this.resourcesPage = 0;
    this.resourceService.searchResources(name,this.resourcesPage).subscribe(
      search => {
        this.resources = search;
      },
      error => {this.resources = [];
                this.showMore = 'No hay resultados para ';
      })
  }

  addSearch(){
    if (this.resources == null || this.activeSearch == false) return;
    this.resourcesPage ++;
    this.resourceService.searchResources(this.search, this.resourcesPage).subscribe(
      search =>{
        this.resources = this.resources.concat(search);
      },
      error => this.showMore = 'No hay más resultados para '
    )
  }
}
