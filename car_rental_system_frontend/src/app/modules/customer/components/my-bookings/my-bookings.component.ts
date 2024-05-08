import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { Router } from '@angular/router';
import { StorageService } from '../../../../auth/services/storage/storage.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrl: './my-bookings.component.scss'
})
export class MyBookingsComponent {
  bookings: any
isSpinning = false;
constructor(private service: CustomerService,
  private router:Router) {
    if (StorageService.isCustomerLoggedIn()) {
      this.getMyBookings();
    }
}



getMyBookings() {
this.isSpinning = true;  
this.service.getBookingsByUserId().subscribe((res) => {
  this.isSpinning=false;
this.bookings=res;
});
}
navigateToPayment(bookingId: number) {
  // Navigate to the payment component with bookingId as a parameter
  this.router.navigateByUrl(`/customer/payment/${bookingId}`);
}
}
