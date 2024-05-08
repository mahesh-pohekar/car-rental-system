import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../../../auth/services/storage/storage.service';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrl: './get-bookings.component.scss'
})
export class GetBookingsComponent {
  bookings: any;
isSpinning = false;
constructor(private adminService: AdminService,
  private message: NzMessageService) {
    if (StorageService.isAdminLoggedIn()) {
      // Execute admin-specific logic
      this.getBookings();
    }
}
getBookings() {
this.isSpinning = true;
this.adminService.getCarBookings().subscribe( (res) => {
this.isSpinning = false;
this.bookings = res;
})
}


changeBookingStatus (bookingId: number, status: string){
this.isSpinning = true;
this.adminService.changeBookingStatus(bookingId,status).subscribe((res) => {
this.isSpinning = false;
this.getBookings();
this.message.success("Booking status changed successfuly!", { nzDuration: 5000 });
}, error => {
this.message.error("Something went wrong", { nzDuration: 5000 });
})
}
  

}
