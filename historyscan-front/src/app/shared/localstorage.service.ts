import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class LocalstorageService {
  addItems(items: Array<{ key: string, value: string }>) {
    items.forEach(item => this.addItem(item.key, item.value));
  }

  getItem(key: string): string | null {
    return localStorage.getItem(key);
  }

  removeItem(key: string) {
    localStorage.removeItem(key);
  }

  removeItems(keys: Array<string>) {
    keys.forEach(key => this.removeItem(key));
  }

  addItem(key: string, value: string): void {
    localStorage.setItem(key, value);
  }
}
