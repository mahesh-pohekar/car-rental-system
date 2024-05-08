import { Injectable } from '@angular/core';

const TOKEN = "token";
const USER = "user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static saveToken(token: string): void {
      window.localStorage.removeItem(TOKEN);
      window.localStorage.setItem(TOKEN, token);
  }

  static saveUser(user: any): void {
      window.localStorage.removeItem(USER);
      window.localStorage.setItem(USER, JSON.stringify(user));

  }

  static getUserId():string{
    const user=this.getUser();
    if(user === "user" || user==null){
      return '';
    }
    return user.id;
  }

  static getToken(): string | null {
    if (typeof window !== 'undefined' || TOKEN !== "token") {
      return window.localStorage.getItem(TOKEN);
    }else{
      return null;

    }
  }

    static getUser(): any {
        const userDataString = localStorage.getItem(USER);
        if ( userDataString == null) {
          return null; // or any default value or appropriate handling
        } else {
          return JSON.parse(userDataString);
        }
     
    }
    static getUserRole():string{
        const user = this.getUser();
        if(user == null) return "";
    return user.role;
    }

    static isAdminLoggedIn(): boolean {
      if (this.getToken() == null) return false;
      const role: string = this.getUserRole();
      return role == "ADMIN";
      }
      
      
      
      static isCustomerLoggedIn(): boolean {
      if (this.getToken() == null) return false;
      const role: string = this.getUserRole();
      return role == "CUSTOMER";
      
      }

      static logout(): void {
          window.localStorage.removeItem(TOKEN);
          window.localStorage.removeItem(USER);
        
      }

}
