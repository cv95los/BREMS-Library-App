import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  templateUrl: 'main.component.html'
})

export class MainComponent {

  constructor(private router: Router, activatedRoute: ActivatedRoute) {}

}