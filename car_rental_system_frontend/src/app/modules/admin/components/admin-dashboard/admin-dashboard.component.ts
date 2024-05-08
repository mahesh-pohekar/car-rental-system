import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../../../auth/services/storage/storage.service';
@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {
  cars: any=[];
  
  constructor(
    private adminService: AdminService,
    private message: NzMessageService  ) { }
ngOnInit(){
  if (StorageService.isAdminLoggedIn()) {
    this.getAllCars();
  }
}


getAllCars() {
  this.adminService.getAllCars().subscribe((res) => {
    this.cars = res.map((element: { processedImg: string; returnedImage: string; }) => {
      return {
        ...element,
        processedImg: 'data:image/jpg;base64,' + element.returnedImage
      };
    });
  });
}

deleteCar(id: number) {
  this.adminService.deleteCar(id).subscribe((res) => {
    this.getAllCars();
    this.message.success("Car deleted successfully", {nzDuration:5000});
  })

}
}
