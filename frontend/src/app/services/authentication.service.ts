import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from '../stocks.model';


@Injectable({
  providedIn: 'root'
})
//Used for logging in and registering users
export class AuthenticationService {

  jwt = ''

  constructor(private httpClient: HttpClient, private router : Router, private snackBar : MatSnackBar) {}

  //Register via the AuthenticationController
  //Will get back a jtw upon successful registration
  register(user: User): Promise<any> {
    return lastValueFrom(this.httpClient.post<any>('/api/auth/register', user)).then(
      token => {
        this.jwt = token['token']
        localStorage.setItem('jwt', this.jwt)
        console.log(this.jwt)
      })
  }

  //Get the jwt
  getJWTToken(email: string, password: string) {

    const credentials = {
      "email": email,
      "password": password
    }

    lastValueFrom(this.httpClient.post<any>('api/auth/login', credentials)).then(
      token => {
        this.jwt = token['token']
        localStorage.setItem('jwt', this.jwt)
      }).then(
        () => {
          this.router.navigate(['/mainpage'])
          this.snackBar.open(`Logged in as ${email}`, 'OK', {duration: 3000})
        }).catch(error => {
          console.log('>>>> ERROR: ', error)
        })
  }
}
