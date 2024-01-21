import {HttpService} from "../../shared";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {CodebaseClocRevisions} from "./cloc-revisions/codebase-cloc-revisions.model";
import {CodebaseToAnalyze} from "../codebases/codebase-to-analyze.model";
import {LocalstorageService} from "../../shared/localstorage.service";
import {HttpClient} from "@angular/common/http";
import {CodebaseNetworkRevisions} from "./network-cloc-revisions/codebase-network-revisions.model";

@Injectable({providedIn: 'root'})
export class AnalysisService extends HttpService {

  constructor(http: HttpClient, private readonly localstorageService: LocalstorageService) {
    super(http);
  }

  private analysis() {
    return this.post(this.localstorageService.getAnalysisHref()!, new CodebaseToAnalyze(
      this.localstorageService.getAnalysisName()!,
      this.localstorageService.getAnalysisType()!,
      this.localstorageService.getAnalysisRootFolder()!,
    ));
  }

  clocAndRevisions(): Observable<CodebaseClocRevisions> {
    return this.analysis();
  }

  networkClocAndRevisions(): Observable<CodebaseNetworkRevisions> {
    return this.analysis()
  }
}
