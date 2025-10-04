import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../wrapper/services/auth.service';
import {CommonModule} from '@angular/common';
import {ImageModule} from 'primeng/image';
import {FloatLabelModule} from 'primeng/floatlabel';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {IconFieldModule} from 'primeng/iconfield';
import {InputIconModule} from 'primeng/inputicon';
import {PasswordModule} from 'primeng/password';
import {Router} from '@angular/router';
import {LoginResponse} from '../../wrapper/models/login.response';
import {LoginRequest} from '../../wrapper/models/login.request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ImageModule,
    FloatLabelModule,
    InputTextModule,
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    ReactiveFormsModule,
    PasswordModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  formLogin: FormGroup;
  loading: boolean = false;
  private routing: Router = inject(Router);
  private authService: AuthService = inject(AuthService);


  constructor(private formBuilder: FormBuilder) {
    this.formLogin = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  public onSubmitForm(): void {
    if (this.formLogin.valid) {
      this.loading = true;
      const credentials: LoginRequest = this.formLogin.value;
      this.authService.login(credentials).subscribe({
        next: (result: LoginResponse) => {
          console.log("response", result);
          this.loading = false;
          this.routing.navigate([''])
        },
        error: error => {
          console.log("Error 123 => ", error);
          this.loading = false;
        }
      });
    }
  };
}
