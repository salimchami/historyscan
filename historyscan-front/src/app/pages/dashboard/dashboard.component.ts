import {Component, OnInit} from '@angular/core';
import {StartupService} from "./startup/startup.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CodebaseToAdd} from "./codebase-to-add.model";
import {
  ClocRevisionsTreeUploadDialogComponent
} from "../analysis/cloc-revisions/upload-dialog/cloc-revisions-tree-upload-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {LocalstorageService} from "../../shared/localstorage.service";

@Component({
  selector: 'app-dashboard',
  styleUrls: ['dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {
  repositoryUrl: string = '';
  secretKeyEnabled: boolean = false;
  addRepoForm: FormGroup = new FormGroup({
    url: new FormControl('', Validators.pattern('^(https|git|ssh)://[a-zA-Z0-9@:_./-]+\\.git$')),
    name: new FormControl('', Validators.required),
    secretKey: new FormControl(''),
    branch: new FormControl('main', Validators.required),
  });

  constructor(private readonly startupService: StartupService,
              private readonly dialog: MatDialog,
              private readonly router: Router,
              private readonly localStorageService: LocalstorageService) {
  }

  ngOnInit(): void {
    this.addRepoForm.get('url')?.valueChanges.subscribe(url => {
      if (url?.startsWith('ssh://') || url?.startsWith('git://')) {
        this.addRepoForm.controls['secretKey'].setValidators([Validators.required]);
      } else {
        this.addRepoForm.controls['secretKey'].clearValidators();
      }
      this.isSecretKeyEnabled(url);
      this.addRepoForm.controls['secretKey'].updateValueAndValidity();
    });
  }

  onSubmit() {
    if (this.addRepoForm.valid) {
      this.startupService.addCodeBase(CodebaseToAdd.from(this.addRepoForm.getRawValue()))
        .subscribe({
          next: () => {
            this.repositoryUrl = '';
            this.addRepoForm.reset();
          },
          error: (error) => {
            console.error(error);
          }
        });
    }
  }

  private isSecretKeyEnabled(url: string): boolean {
    return url?.startsWith('ssh://') || url?.startsWith('git://');
  }

  isSubmitButtonEnabled() {
    return this.addRepoForm.dirty && this.addRepoForm.valid;
  }

  uploadGraph() {
    this.dialog.open(ClocRevisionsTreeUploadDialogComponent, {
      width: '50%',
    }).afterClosed().subscribe((result) => {
      if (result) {
        this.localStorageService.addFilesTree(result);
        this.router.navigateByUrl('/analysis/cloc-revisions').then();
      }
    });
  }
}
