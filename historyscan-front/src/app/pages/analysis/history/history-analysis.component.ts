import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AnalysisService} from "../analysis.service";
import {MatTableDataSource} from "@angular/material/table";
import {CodebaseHistoryFile} from "./codebase-history-file.model";
import {HistoryTableService} from "./history-table.service";

@Component({
  selector: 'app-history-analysis',
  templateUrl: './history-analysis.component.html',
  styleUrls: ['./history-analysis.component.scss'],
})
export class HistoryAnalysisComponent implements OnInit {
  dataSource: MatTableDataSource<CodebaseHistoryFile> = new MatTableDataSource<CodebaseHistoryFile>([]);
  displayedColumns = ['date', 'shortMessage', 'author', 'hash', 'fileName',
    'nbLinesAdded', 'nbLinesDeleted'
  ];

  constructor(private readonly activatedRoute: ActivatedRoute,
              private readonly analysisService: AnalysisService,
              private readonly historyTableService: HistoryTableService) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(() => {
      this.analysisService.history().subscribe({
        next: (codebaseHistory) => {
          this.dataSource = new MatTableDataSource<CodebaseHistoryFile>(codebaseHistory.files);
        }
      });
    });
  }

  rowspan(commit: CodebaseHistoryFile, currentIndex: any) {
    return this.historyTableService.rowspan(commit, currentIndex, this.dataSource);
  }
}
