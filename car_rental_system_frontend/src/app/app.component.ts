import { Component } from '@angular/core';
import { StorageService } from './auth/services/storage/storage.service';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'car_rental_system_frontend';
  isCustomerLoggedIn: boolean = false;
  isAdminLoggedIn: boolean = false;

  constructor(private router: Router) {}

  ngOnInit(){
    // Subscribe to router events to detect navigation changes
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        // Update authentication status on navigation end
        this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
        this.isCustomerLoggedIn = StorageService.isCustomerLoggedIn();
      });
  }
logout(){
  StorageService.logout();
  this.router.navigateByUrl("/login");
}

}
