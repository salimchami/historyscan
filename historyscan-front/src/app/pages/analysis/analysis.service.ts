import {HttpService} from "../../shared";
import {Observable} from "rxjs";
import {CodebaseHistory} from "./history/codebase-history.model";
import {Injectable} from "@angular/core";
import {CodebaseClocRevisions} from "./cloc-revisions/codebase-cloc-revisions.model";
import {CodebaseToAnalyze} from "../codebases/codebase-to-analyze.model";
import {LocalstorageService} from "../../shared/localstorage.service";
import {HttpClient} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class AnalysisService extends HttpService {

  constructor(http: HttpClient, private readonly localstorageService: LocalstorageService) {
    super(http);
  }

  history(): Observable<CodebaseHistory> {
    return this.analysis();
  }

  clocAndRevisions(): Observable<CodebaseClocRevisions> {
    return this.analysis();
  }

  private analysis() {
    return this.post(this.localstorageService.getItem('analysis-href')!, new CodebaseToAnalyze(
      this.localstorageService.getItem('analysis-name')!,
      this.localstorageService.getItem('analysis-type')!,
      this.localstorageService.getItem('analysis-root-folder')!,
    ));
  }
}
