import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {inject} from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {

  const authService: AuthService = inject(AuthService);
  const routing: Router = inject(Router);

  if (authService.isAuthenticated()) {
    console.log('User Authenticated');
    return true;
  }else {
    console.log("User not Authenticated");
    return routing.createUrlTree(['/auth/login']);
  }

};
