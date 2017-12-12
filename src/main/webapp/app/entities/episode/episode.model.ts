import { BaseEntity } from './../../shared';

export class Episode implements BaseEntity {
    constructor(
        public id?: number,
        public number?: number,
        public name?: string,
        public imgContentType?: string,
        public img?: any,
        public duration?: number,
        public releaseDate?: any,
        public season?: BaseEntity,
        public seens?: BaseEntity[],
    ) {
    }
}