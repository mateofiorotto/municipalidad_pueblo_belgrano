import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { HealthService } from '../../../services/health/health.service';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { tap } from 'rxjs';
import { take } from 'rxjs';

export const maintenanceGuard: CanActivateFn = (route, state) => {
  
  const _healthService = inject(HealthService);
  const _router = inject(Router);

  if (state.url === '/mantenimiento') {
    return true;
  }

  return _healthService.getHealthStatus().pipe(
    take(1),
    tap(isUp => {
      if (!isUp) {
        _router.navigate(['/mantenimiento']);
      }
    }),
    map(isUp => isUp)
  );
};
