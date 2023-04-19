import { AfterViewInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import * as L from 'leaflet';

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements AfterViewInit {

  private map: any

  constructor(private router: Router) {}

  ngAfterViewInit(): void {
    this.initMap();
  }


  private initMap(): void {
    this.map = L.map('map', {
      center: [ 1.292, 103.7767 ],
      zoom: 18
    
    })
    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 20,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    })
    tiles.addTo(this.map)


    L.circle([1.29215, 103.77647], {
      color: 'red',
      fillOpacity: 0,
      radius: 25
    }).addTo(this.map);
  }

  logout() {
    this.router.navigate(['/'])
  }

  getQuotes() {
    this.router.navigate(['/quotes'])
  }

  mainpage() {
    this.router.navigate(['/mainpage'])
  }

  contactus() {
    this.router.navigate(['/contactus'])
  }



}