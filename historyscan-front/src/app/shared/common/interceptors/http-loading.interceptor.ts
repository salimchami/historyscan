import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, finalize} from 'rxjs/operators';
import {Injectable, Injector} from '@angular/core';
import {LoadingService} from '../navigation';
import {HttpErrors} from './http-errors-constant';
import {TranslateService} from '@ngx-translate/core';
import {Router} from "@angular/router";

@Injectable()
export class HttpLoadingInterceptor implements HttpInterceptor {

  constructor(private loadingService: LoadingService,
              private readonly injector: Injector,
              private router: Router
  ) {
  }

  private static addToken(req: HttpRequest<any>) {
    const token = localStorage.getItem('id_token');
    return token ? req.clone({headers: req.headers.append('Authorization', `Bearer ${token}`)}) : req;
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.loadingService.show();
    return next.handle(HttpLoadingInterceptor.addToken(request)).pipe(
      catchError((err) => {
        this.showError(err);
        return throwError(() => err);
      }),
      finalize(() => {
        this.loadingService.hide();
      })
    );
  }

  showError(err: any) {
    const translateService = this.injector.get(TranslateService);
    translateService.get('global.errors.basic').subscribe({
      next: () => {
        switch (err.status) {
          case HttpErrors.INTERNAL_SERVER_ERROR:
            break;
          case HttpErrors.UNAUTHORIZED:
            this.router.navigate(['welcome']).then();
            break;
          case HttpErrors.SERVICE_UNAVAILABLE:
          case HttpErrors.BAD_GATEWAY:
          case HttpErrors.GATEWAY_TIMEOUT:
            this.router.navigate(['unavailable']).then();
            break;
        }
      }
    });
  }
}
