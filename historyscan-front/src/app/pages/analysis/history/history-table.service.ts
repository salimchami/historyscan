//  anguilar service
import {Injectable} from "@angular/core";
import {CodebaseHistoryFile} from "./codebase-history-file.model";

@Injectable({providedIn: 'root'})
export class HistoryTableService {
  rowspan(commit: CodebaseHistoryFile, currentIndex: any, dataSource: any) {
    if (currentIndex === 0) {
      return 1;
    }

    const previousCommit = dataSource.data[currentIndex - 1];
    if (commit.info.hash === previousCommit.info.hash) {
      return -1;
    } else {
      let rowspan = 1;
      for (let i = currentIndex - 1; i >= 0; i--) {
        if (dataSource.data[i].info.hash === commit.info.hash) {
          rowspan++;
        } else {
          break;
        }
      }
      return rowspan;
    }

  }
}
