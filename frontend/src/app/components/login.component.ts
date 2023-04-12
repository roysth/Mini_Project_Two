import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form! : FormGroup

  constructor(private fb : FormBuilder, private router: Router, private authentcationService: AuthenticationService){}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  login() {
    this.authentcationService.getJWTToken(this.form.value['email'], this.form.value['password'])
  
  }



  createForm(){
    return this.fb.group({
      email : this.fb.control<string>('',[Validators.required]),
      password: this.fb.control<string>('',[Validators.required])
    })
  }

}
