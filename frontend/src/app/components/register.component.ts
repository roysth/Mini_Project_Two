import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../stocks.model';
import { AuthenticationService } from '../services/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form! : FormGroup

  constructor(private fb : FormBuilder, private router : Router, private authenticationService: AuthenticationService){}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  process() {
    const user: User = {
      "name": this.form.value['name'],
      "email": this.form.value['email'],
      "password": this.form.value['password']
    }
    //Register the user and store the token, which will be used upon logging in
    this.authenticationService.register(user).then(
      () => {
        this.router.navigate(['/mainpage'])
        //this.snackBar.open(`Logged in as ${user.username}`, 'OK', {duration: 3000})
      }
    ).catch(error => {
      console.log('>>>> ERROR: ', error)
    })


  }

  createForm():FormGroup {
    return this.fb.group({
      name : this.fb.control('',[Validators.required]),
      email : this.fb.control('',[Validators.required, Validators.email]),
      password: this.fb.control('',[Validators.required]),
      
    })
  }

}
