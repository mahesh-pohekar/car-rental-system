import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

const BASE_URL = environment.API_URL;
@Injectable({
providedIn: 'root'
})

export class AuthService {
  constructor(private htpp: HttpClient) { }
register (signupRequest: any):Observable<any> {
   return this.htpp.post(BASE_URL+ "/api/auth/signup", signupRequest);
}

login(loginRequest:any):Observable<any>{
  return this.htpp.post(BASE_URL+"/api/auth/login",loginRequest);
}

}