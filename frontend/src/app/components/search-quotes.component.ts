import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-quotes',
  templateUrl: './search-quotes.component.html',
  styleUrls: ['./search-quotes.component.css']
})
export class SearchQuotesComponent implements OnInit{

  form!: FormGroup

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.form = this.createForm();
  }

  process() {
    const ticker = this.form.get('ticker')?.value

    this.router.navigate(['/quotes', ticker])
  }

  createForm(): FormGroup {
    return this.fb.group({
      ticker: this.fb.control<string>('', Validators.required)
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

}
