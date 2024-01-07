import {HttpService} from "../../shared";
import {Observable} from "rxjs";
import {CurrentCodebases} from "./current-codebases.model";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {LocalstorageService} from "../../shared/localstorage.service";

@Injectable({providedIn: 'root'})
export class CodebasesService extends HttpService {

  constructor(http: HttpClient, private localStorageService: LocalstorageService) {
    super(http);
  }
    getCodebases(): Observable<CurrentCodebases> {
        return this.get(this.localStorageService.getItem('codebases-url')!);
    }
}
