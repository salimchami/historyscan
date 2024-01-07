import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class LoadingService {
  private _loading = new BehaviorSubject<boolean>(false);
  public readonly loading$ = this._loading.asObservable();

  show() {
    if (!this._loading.value) {
      this._loading.next(true);
    }
  }

  hide() {
    if (this._loading.value) {
      this._loading.next(false);
    }
  }
}
