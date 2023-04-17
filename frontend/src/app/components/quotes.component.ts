import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StocksRepository } from '../stocks.repository';
import { Quotes } from '../stocks.model';

@Component({
  selector: 'app-quotes',
  templateUrl: './quotes.component.html',
  styleUrls: ['./quotes.component.css']
})
export class QuotesComponent implements OnInit {

  //Params for the GET
  ticker!: string
  quotes!: Quotes

  constructor(private activatedRoute: ActivatedRoute, private stocksRepo: StocksRepository,
    private router: Router) {}


  ngOnInit(): void {
    //Get the param from url
    this.ticker = this.activatedRoute.snapshot.params['ticker']

    console.log('>>>> The ticker is: ', this.ticker)

    this.stocksRepo.getQuotes(this.ticker)
      .then(results => {
        this.quotes = results
        console.log('>>> Quotes: ', results)
      })
      .catch(error => {
        console.log('>>>> ERROR: ', error)
      })
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


}
