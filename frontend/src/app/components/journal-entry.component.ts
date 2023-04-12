import { Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Journal } from '../stocks.model';
import { StocksRepository } from '../stocks.repository';

@Component({
  selector: 'app-journal-entry',
  templateUrl: './journal-entry.component.html',
  styleUrls: ['./journal-entry.component.css']
})

export class JournalEntryComponent implements OnInit {

  form!: FormGroup
  journal!: Journal
  blob!: Blob

  @ViewChild('image')
	image!: ElementRef

  constructor(private fb: FormBuilder, private router: Router, private stocksRepo: StocksRepository) {}

  ngOnInit(): void {
    this.form = this.createForm();
  }

  process() {

    //Get the Contact from the form
    const journal = this.form.value as Journal

    //Get the image from the form
    this.blob = this.image.nativeElement.files[0]
    
    console.log(this.blob)

    //console.log('>>>> JOURNAL ', journal)
    console.log(typeof journal.entryPrice)

    this.stocksRepo.postJournal(journal, this.blob)
    .then(results => {
      console.log('>>> POSTED: ', results)
    })
    .catch(error => {
      console.log('>>>> ERROR: ', error)
    })

    //this.router.navigate(['/journal/success'])
  }

  clear() {
    this.form = this.createForm()
  }

  createForm(): FormGroup {
    return this.fb.group({
      symbol: this.fb.control<string>('', Validators.required),
      quantity: this.fb.control<number>(0, Validators.required),
      position: this.fb.control<string>('', Validators.required),
      tradeType: this.fb.control<string>('', Validators.required),
      entryPrice: this.fb.control<number>(0, Validators.required),
      exitPrice: this.fb.control<number>(0),
      entryDate: this.fb.control<Date>(new Date, Validators.required),
      exitDate: this.fb.control<Date>(new Date),
      pnl: this.fb.control<number>(0, Validators.required),
      comments: this.fb.control<string>(''),
      image: this.fb.control('', Validators.required)
    })
  }


  

}


