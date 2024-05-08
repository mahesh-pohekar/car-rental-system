import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  paymentForm: FormGroup;
  bookingId!: number;
  isSpinning = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: CustomerService,
    private message: NzMessageService
  ) {
    this.paymentForm = this.fb.group({
      cardNumber: ['', [Validators.required, Validators.pattern(/^\d{16}$/)]], // Assuming card number is 16 digits
      expiryDate: ['', [Validators.required, Validators.pattern(/^(0[1-9]|1[0-2])\/\d{2}$/)]], // Assuming MM/YY format
      cvv: ['', [Validators.required, Validators.pattern(/^\d{3,4}$/)]], // Assuming CVV is 3 or 4 digits
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.bookingId = Number(params.get('bookingId'));
    });
  }

  submitPayment(): void {
    if (this.paymentForm.valid) {
      this.isSpinning = true;
      const paymentData = {
        cardNumber: this.paymentForm.value.cardNumber,
        expiryDate: this.paymentForm.value.expiryDate,
        cvv: this.paymentForm.value.cvv
      };

      this.service.processPayment(this.bookingId, paymentData).subscribe(
        () => {
          this.message.success("Payment successful!", { nzDuration: 5000 });
          this.router.navigateByUrl("/customer/my_bookings");
        },
        error => {
          this.isSpinning = false;
          // Handle payment error
        }
      );
    }
  }

  // Get the validation status for the card number field
  getCardNumberValidateStatus(): string {
    const cardNumberFormControl = this.paymentForm.get('cardNumber');
    return cardNumberFormControl && cardNumberFormControl.invalid && (cardNumberFormControl.dirty || cardNumberFormControl.touched) ? 'error' : '';
  }

  // Get the validation status for the expiry date field
  getExpiryDateValidateStatus(): string {
    const expiryDateFormControl = this.paymentForm.get('expiryDate');
    return expiryDateFormControl && expiryDateFormControl.invalid && (expiryDateFormControl.dirty || expiryDateFormControl.touched) ? 'error' : '';
  }

  // Get the validation status for the CVV field
  getCvvValidateStatus(): string {
    const cvvFormControl = this.paymentForm.get('cvv');
    return cvvFormControl && cvvFormControl.invalid && (cvvFormControl.dirty || cvvFormControl.touched) ? 'error' : '';
  }
}
