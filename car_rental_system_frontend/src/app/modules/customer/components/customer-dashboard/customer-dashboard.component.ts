import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { StorageService } from '../../../../auth/services/storage/storage.service';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.scss'
})
export class CustomerDashboardComponent {
  cars: any=[];

  constructor(private service: CustomerService){}
  ngOnInit(){
    if (StorageService.isCustomerLoggedIn()) {
      // Execute admin-specific logic
      this.getAllCars();
    }
  }
  
  getAllCars() {
    this.service.getAllCars().subscribe((res) => {
      this.cars = res.map((element: { processedImg: string; returnedImage: string; }) => {
        return {
          ...element,
          processedImg: 'data:image/jpg;base64,' + element.returnedImage
        };
      });
    });
  }
}
