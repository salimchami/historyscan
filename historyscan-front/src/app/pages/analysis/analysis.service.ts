import {HttpService} from "../../shared";
import {Observable} from "rxjs";
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

  clocAndRevisions(): Observable<CodebaseClocRevisions> {
    return this.post(this.localstorageService.getAnalysisHref()!, new CodebaseToAnalyze(
      this.localstorageService.getAnalysisName()!,
      this.localstorageService.getAnalysisType()!,
      this.localstorageService.getAnalysisRootFolder()!,
    ));
  }

  networkClocAndRevisions(): Observable<NetworkCodebaseClocRevisions> {
    return this.post(this.localstorageService.getAnalysisHref()!, new CodebaseToAnalyze(
      this.localstorageService.getAnalysisName()!,
      this.localstorageService.getAnalysisType()!,
      this.localstorageService.getAnalysisRootFolder()!,
    ));
  }
}
