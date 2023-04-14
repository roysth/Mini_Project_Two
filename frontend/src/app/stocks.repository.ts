import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Day, Journal, Quotes } from "./stocks.model";
import { AuthenticationService } from "./services/authentication.service";

@Injectable()
export class StocksRepository {

    constructor(private httpClient: HttpClient, private authenticationService: AuthenticationService){}

    //This is required to get the jwt from local storage and setting in the headers in order to 
    //connect with the backend
    setHeaders(){
        const jwt = localStorage.getItem('jwt')
        const headers = new HttpHeaders().set('Authorization', `Bearer ${jwt}`)
        console.log('>>>>> JWT: ', jwt)
    
        return headers
    }

    getQuotes (ticker: string): Promise<Quotes> {

        console.log('>>Connecting to server')
        //Uses Path Variable
        return lastValueFrom(this.httpClient.get<Quotes>(`/api/quotes/${ticker}`, {headers: this.setHeaders()}))
    }

    postJournal (journal: Journal, image:Blob, day_id: string): Promise<any> {

        //Uses Response Body
        //payload is used in Controller
        const payload = {
            journal: journal
        }


        // **** For testing !!!!!!!!!!!!!!!!!!!
        // const queryParams =  new HttpParams()
        // .set('day_id', 'eae9d027')

        // **** For Real !!!!!!!!!!!!!!!!!!!
        const queryParams = new HttpParams()
            .set('day_id', day_id)
        
        const formData = new FormData()
        formData.set('image', image)


        return lastValueFrom(this.httpClient.post<any>('/api/uploadimage', formData, {headers: this.setHeaders()})).then(
            data => {
                console.log(data)
                journal.uuid = data['uuid']
                journal.imageUrl = data['imageUrl']
                console.log('>>> Sending journal over. Journal is: ', journal)
            }).then(()=> lastValueFrom(this.httpClient.post<any>('/api/uploadjournal', payload, {headers: this.setHeaders(), params: queryParams})))
        

    }
    //To get the list of Day object for the day_id
    getDays(startStr: string, endStr: string): Promise<Day[]> {

        const queryParams = new HttpParams()
        .set('startStr', startStr)
        .set('endStr', endStr)

        return lastValueFrom(this.httpClient.get<Day[]>('/api/getdays', {headers: this.setHeaders(), params: queryParams}))
    }

    //Search the day_id in SQL in the Days schema before submitting a journal entry. 
    //If the day_id is present, retrieve it. If no, submit journal without day_id and a day_id will be created
    searchDay(day:string): Promise<any> {

        const queryParams = new HttpParams()
        .set('date', day)

        return lastValueFrom(this.httpClient.get('/api/searchday', {headers: this.setHeaders(), params: queryParams}))

    }

    getJournalEntriesByDayId (day_id: string): Promise<any> {

        const queryParams = new HttpParams()
        .set('day_id', day_id)

        return lastValueFrom(this.httpClient.get<Journal[]>('/api/getjournal', {headers: this.setHeaders(), params: queryParams}))
    }
  

    //Things to do:
    //controller for getdays
    //Other methods in mainpage









}