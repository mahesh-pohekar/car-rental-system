import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';



import { registerLocaleData } from '@angular/common';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NZ_I18N, en_US } from 'ng-zorro-antd/i18n';
import en from '@angular/common/locales/en';
import { SignupComponent } from './auth/components/signup/signup.component';
import { LoginComponent } from './auth/components/login/login.component';
import { NgZorroImportsModule } from './NgZorroImportsModule';



// Routing (replace with your actual routing configuration)

registerLocaleData(en);


@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    NgZorroImportsModule,
    
  ],
  providers: [
    provideClientHydration(),
    { provide: NZ_I18N, useValue: en_US }, // Example locale provider (optional)

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
