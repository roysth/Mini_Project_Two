import { Component, OnInit } from '@angular/core';
import { StocksRepository } from '../stocks.repository';
import { ActivatedRoute, Router } from '@angular/router';
import { Journal } from '../stocks.model';

@Component({
  selector: 'app-uploaded-entries',
  templateUrl: './uploaded-entries.component.html',
  styleUrls: ['./uploaded-entries.component.css']
})
export class UploadedEntriesComponent implements OnInit {

  day_id!: string
  day_string!: string
  journalList!: Journal[]


  constructor(private stocksRepository: StocksRepository, private router: Router, 
    private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.day_id = this.activatedRoute.snapshot.params['id']
    this.day_string = this.activatedRoute.snapshot.params['day']
  }

  getListOfJournal() {
    this.stocksRepository.getJournalEntriesByDayId(this.day_id).then(results => {
      this.journalList = results as Journal[]
    }).then()

  }
  

}
