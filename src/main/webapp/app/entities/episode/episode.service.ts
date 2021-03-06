import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Episode } from './episode.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EpisodeService {

    private resourceUrl = SERVER_API_URL + 'api/episodes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(episode: Episode): Observable<Episode> {
        const copy = this.convert(episode);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(episode: Episode): Observable<Episode> {
        const copy = this.convert(episode);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Episode> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Episode.
     */
    private convertItemFromServer(json: any): Episode {
        const entity: Episode = Object.assign(new Episode(), json);
        entity.releaseDate = this.dateUtils
            .convertLocalDateFromServer(json.releaseDate);
        return entity;
    }

    /**
     * Convert a Episode to a JSON which can be sent to the server.
     */
    private convert(episode: Episode): Episode {
        const copy: Episode = Object.assign({}, episode);
        copy.releaseDate = this.dateUtils
            .convertLocalDateToServer(episode.releaseDate);
        return copy;
    }
}
