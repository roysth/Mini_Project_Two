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
  day= new Date


  constructor(private stocksRepository: StocksRepository, private router: Router, 
    private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.day_id = this.activatedRoute.snapshot.params['id']
    this.day_string = this.activatedRoute.snapshot.params['day']
    this.getListOfJournal()

  }

  getListOfJournal() {
    this.stocksRepository.getJournalEntriesByDayId(this.day_id).then(results => {
      this.journalList = results as Journal[]
    }).then( () => {
      //This is needed because if the last Journal entry is deleted from Mongo,
      //the Day entry from SQL should also be deleted. 
      //This is for simplier entries in future. Neater also
      if (this.journalList.length == 0) {
        this.stocksRepository.deleteDay(this.day_id)
        this.router.navigate(['/mainpage'])
      }
    })
  }

  deleteJournal(j: Journal) {

    this.stocksRepository.deleteJournal(j.uuid, j.pnl, this.day_id)
      .then( () => this.getListOfJournal()
    ).catch(error => {
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

  contactus() {
    this.router.navigate(['/contactus'])
  }

  edited(date: Date) {
    
    //Date output:Wed Apr 19 00:00:00 SGT 2023
    //dateString: String
    //dateString = date.toLocaleString;
    const dateString = date.toString()

    // Split the input string by spaces to get an array of individual parts
    const parts: string[] = dateString.split(" ");

    // Extract the day, month, and year from the parts array
    const day: string = parts[2];
    const month: string = parts[1];
    const year: string = parts[5];

    // Concatenate the day, month, and year with a space between them
    const outputString: string = `${day} ${month} ${year}`;

    return outputString
 
  }
  

}
