import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FullCalendarModule } from '@fullcalendar/angular';


import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/login.component';
import { MainpageComponent } from './components/mainpage.component';
import { StocksRepository } from './stocks.repository';
import { QuotesComponent } from './components/quotes.component';
import { SearchQuotesComponent } from './components/search-quotes.component';
import { JournalEntryComponent } from './components/journal-entry.component';
import { JournalEntrySuccessComponent } from './components/journal-entry-success.component';
import { RegisterComponent } from './components/register.component';
import { MaterialModule } from './material.module';
import { UploadedEntriesComponent } from './components/uploaded-entries.component';

const appRoutes: Routes = [
  {path:'', component: LoginComponent},
  {path:'register', component: RegisterComponent},
  {path:'entry/:day', component: JournalEntryComponent}, //To enter a journal entry
  {path:'uploadedentries/:day/:id', component: UploadedEntriesComponent}, //To display all the entries
  {path:'journal/success', component: JournalEntrySuccessComponent}, //not used
  {path:'mainpage', component: MainpageComponent},
  {path:'quotes', component: SearchQuotesComponent}, 
  {path:'quotes/:ticker', component: QuotesComponent},
  {path:'**', redirectTo: '/', pathMatch: 'full'}

]



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainpageComponent,
    QuotesComponent,
    SearchQuotesComponent,
    JournalEntryComponent,
    JournalEntrySuccessComponent,
    RegisterComponent,
    UploadedEntriesComponent
  ],

  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    FullCalendarModule,
    MaterialModule,
  ],
  providers: [StocksRepository],
  bootstrap: [AppComponent]
})
export class AppModule { }
