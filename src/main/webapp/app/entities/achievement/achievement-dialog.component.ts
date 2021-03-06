import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Achievement } from './achievement.model';
import { AchievementPopupService } from './achievement-popup.service';
import { AchievementService } from './achievement.service';

@Component({
    selector: 'jhi-achievement-dialog',
    templateUrl: './achievement-dialog.component.html'
})
export class AchievementDialogComponent implements OnInit {

    achievement: Achievement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private achievementService: AchievementService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.achievement, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.achievement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.achievementService.update(this.achievement));
        } else {
            this.subscribeToSaveResponse(
                this.achievementService.create(this.achievement));
        }
    }

    private subscribeToSaveResponse(result: Observable<Achievement>) {
        result.subscribe((res: Achievement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Achievement) {
        this.eventManager.broadcast({ name: 'achievementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-achievement-popup',
    template: ''
})
export class AchievementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private achievementPopupService: AchievementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.achievementPopupService
                    .open(AchievementDialogComponent as Component, params['id']);
            } else {
                this.achievementPopupService
                    .open(AchievementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
