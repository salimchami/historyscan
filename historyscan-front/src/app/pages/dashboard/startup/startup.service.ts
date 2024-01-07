import {Injectable} from "@angular/core";
import {HttpService} from "../../../shared";
import {Observable} from "rxjs";
import {Startup} from "./startup.model";
import {environment} from "../../../../environments/environment";
import {CodebaseToAdd} from "../codebase-to-add.model";

@Injectable({providedIn: "root"})
export class StartupService extends HttpService {
  private addCodeBaseUrl: string = '';

  startup(): Observable<Startup> {
    return this.get(environment.startupUrl);
  }

  initAddCodeBaseUrl(url: string) {
    this.addCodeBaseUrl = url;
  }

  addCodeBase(codebase: CodebaseToAdd): Observable<any> {
    return this.post(this.addCodeBaseUrl, codebase);
  }
}
